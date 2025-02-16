package com.project.apis.portfolio.repository;

import com.project.commons.mapper.PnLMapper;
import com.project.commons.mapper.TransactionMapper;
import com.project.commons.mapper.UserPortfolioMapper;
import com.project.commons.model.PnL;
import com.project.commons.model.Transaction;
import com.project.commons.model.UserPortfolio;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Inject;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.JdbiException;
import org.jdbi.v3.core.result.ResultBearing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class PortfolioRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(PortfolioRepository.class);

    @Inject
    DataSource dataSource;

    @Transactional
    public boolean processBuyOrder(Long userId, String stockTicker, BigDecimal price, int quantity) {
        Jdbi jdbi = Jdbi.create(dataSource);
        try (Handle handle = jdbi.open()) {
            List<UserPortfolio> portfolio = findUserPortfolio(handle, userId);
            Optional<UserPortfolio> existingStock = portfolio.stream()
                    .filter(entry -> entry.getStockTicker().equals(stockTicker))
                    .findFirst();

            if (existingStock.isPresent()) {
                UserPortfolio portfolioEntry = existingStock.get();
                int newTotalQuantity = portfolioEntry.getTotalQuantity() + quantity;
                BigDecimal currentTotalValue = portfolioEntry.getAveragePriceBought().multiply(BigDecimal.valueOf(portfolioEntry.getTotalQuantity()));
                BigDecimal newTotalValue = currentTotalValue.add(price.multiply(BigDecimal.valueOf(quantity)));
                BigDecimal newAvgPrice = newTotalValue.divide(BigDecimal.valueOf(newTotalQuantity), 2, java.math.RoundingMode.HALF_UP);

                handle.execute("UPDATE user_portfolios SET average_price_bought = ?, total_quantity = ? WHERE user_id = ? AND stock_ticker = ?",
                        newAvgPrice, newTotalQuantity, userId, stockTicker);
            } else {
                String stockName = getStockName(handle, stockTicker);
                String stockSector = getStockSector(handle, stockTicker);

                if (stockName != null && stockSector != null) {
                    handle.execute("INSERT INTO user_portfolios (user_id, stock_ticker, stock_name, stock_sector, average_price_bought, total_quantity) VALUES (?, ?, ?, ?, ?, ?)",
                            userId, stockTicker, stockName, stockSector, price, quantity);
                } else {
                    LOGGER.error("Stock with ticker {} not found", stockTicker);
                    return false;
                }
            }
            return true;
        } catch (JdbiException e) {
            LOGGER.error("Error updating portfolio", e);
            return false;
        }
    }

    public void addBuyTransaction(Long userId, String stockTicker, int quantity, BigDecimal price) {
        Jdbi jdbi = Jdbi.create(dataSource);
        try (Handle handle = jdbi.open()) {
            String insertSQL = "INSERT INTO transactions (user_id, stock_ticker, transaction_type, quantity, price, remaining_quantity) VALUES (?, ?, ?, ?, ?, ?)";
            handle.createUpdate(insertSQL)
                .bind(0, userId)
                .bind(1, stockTicker)
                .bind(2, "BUY") // Constant for buy transaction
                .bind(3, quantity)
                .bind(4, price)
                .bind(5, quantity) // Remaining quantity is the same as purchased quantity for buy
                .execute();
        } catch (JdbiException e) {
            LOGGER.error("Error adding buy transaction", e);
        }
    }

    @Transactional
    public boolean processSellOrder(Long userId, String stockTicker, int quantityToSell, BigDecimal sellPrice) {
        Jdbi jdbi = Jdbi.create(dataSource);
        try (Handle handle = jdbi.open()) {

            int remainingQuantityToSell = quantityToSell;
            BigDecimal totalCostOfSoldShares = BigDecimal.ZERO;

            // 1. Get existing portfolio entry
            List<UserPortfolio> portfolioEntryList = findUserPortfolio(handle, userId);
            Optional<UserPortfolio> portfolioStock = portfolioEntryList.stream().filter(entry -> {
                return entry.getStockTicker().equals(stockTicker);
            }).findFirst();

            if (portfolioStock.isEmpty() || portfolioStock.get().getTotalQuantity() < quantityToSell) {
                // Not enough shares to sell
                return false;
            }

            UserPortfolio portfolio = portfolioStock.get();

            // 2. Process Transactions in FIFO order
            List<Transaction> buyTransactions = getBuyTransactionsWithRemainingQuantity(handle, userId, stockTicker);

            for (Transaction buyTransaction : buyTransactions) {
                if (remainingQuantityToSell == 0) break;

                int quantityFromThisTransaction = Math.min(remainingQuantityToSell, buyTransaction.getRemainingQuantity());

                // 3. Record the sell Transaction


                remainingQuantityToSell -= quantityFromThisTransaction;

                // 4. Update the remaining quantity in the buy Transaction
                int newRemainingQuantity = buyTransaction.getRemainingQuantity() - quantityFromThisTransaction;
                updateBuyTransactionRemainingQuantity(handle, buyTransaction.getId(), newRemainingQuantity);

                totalCostOfSoldShares = totalCostOfSoldShares.add(buyTransaction.getPrice().multiply(BigDecimal.valueOf(quantityFromThisTransaction)));
            }
            recordSellTransaction(handle, userId, stockTicker,quantityToSell, sellPrice);

            // Calculate Realized PnL
            BigDecimal realizedPnlForThisSale = sellPrice.multiply(BigDecimal.valueOf(quantityToSell)).subtract(totalCostOfSoldShares);

            // Update PnL table
            updatePnl(handle, userId, stockTicker, realizedPnlForThisSale);

            // 5. Update the portfolio
            int newTotalQuantity = portfolio.getTotalQuantity() - quantityToSell;
            if(newTotalQuantity == 0) {
                removePortfolioEntry(handle, userId, stockTicker);
            } else {
                BigDecimal newAveragePrice;
                if (newTotalQuantity > 0) {
                    BigDecimal remainingTotalValue = portfolio.getAveragePriceBought().multiply(BigDecimal.valueOf(portfolio.getTotalQuantity())).subtract(totalCostOfSoldShares);
                    newAveragePrice = remainingTotalValue.divide(BigDecimal.valueOf(newTotalQuantity), 2, java.math.RoundingMode.HALF_UP);
                } else {
                    newAveragePrice = BigDecimal.ZERO;
                }
                updatePortfolioAfterSell(handle, userId, stockTicker, newTotalQuantity, newAveragePrice);
            }

            return true;
        } catch (JdbiException e) {
            LOGGER.error("Error processing sell order", e);
            return false;
        }
    }

    private void updatePnl(Handle handle, Long userId, String stockTicker, BigDecimal realizedPnlForThisSale) {
        // Check if PnL entry exists
        String stockName=getStockName(handle,stockTicker);
        Optional<PnL> pnlOptional = findPnl(handle, userId, stockTicker);

        if (pnlOptional.isPresent()) {
            // Update existing PnL entry
            handle.execute("UPDATE pnl SET realized_pnl = realized_pnl + ? WHERE user_id = ? AND stock_ticker = ?",
                    realizedPnlForThisSale, userId, stockTicker);
        } else {
            // Create new PnL entry
            handle.execute("INSERT INTO pnl (user_id, stock_ticker, stock_name, realized_pnl) VALUES (?, ?, ?, ?)",
                    userId, stockTicker, stockName, realizedPnlForThisSale);
        }
    }

    private Optional<PnL> findPnl(Handle handle, Long userId, String stockTicker) {
        return handle.createQuery("SELECT * FROM pnl WHERE user_id = ? AND stock_ticker = ?")
                .bind(0, userId)
                .bind(1, stockTicker)
                .map(new PnLMapper())
                .findFirst();
    }

    private List<Transaction> getBuyTransactionsWithRemainingQuantity(Handle handle, Long userId, String stockTicker) {
        return handle.createQuery("SELECT * FROM transactions WHERE user_id = ? AND stock_ticker = ? AND Transaction_type = 'BUY' AND remaining_quantity > 0 ORDER BY Transaction_date ASC")
                .bind(0, userId)
                .bind(1, stockTicker)
                .map(new TransactionMapper())
                .list();
    }



    private void updateBuyTransactionRemainingQuantity(Handle handle, Long TransactionId, int remainingQuantity) {
        handle.execute("UPDATE transactions SET remaining_quantity = ? WHERE id = ?", remainingQuantity, TransactionId);
    }

    private void recordSellTransaction(Handle handle, Long userId, String stockTicker, int quantity, BigDecimal price) {
        handle.execute("INSERT INTO transactions (user_id, stock_ticker, transaction_type, quantity, price, remaining_quantity) VALUES (?, ?, ?, ?, ?, ?)",
                userId, stockTicker, "SELL",quantity, price, 0);
    }


    private void removePortfolioEntry(Handle handle, Long userId, String stockTicker) {
        handle.execute("DELETE FROM user_portfolios WHERE user_id = ? AND stock_ticker = ?", userId, stockTicker);
    }

    private void updatePortfolioAfterSell(Handle handle, Long userId, String stockTicker, int newTotalQuantity, BigDecimal newAveragePrice){
        handle.execute("UPDATE user_portfolios SET total_quantity = ?, average_price_bought = ? WHERE user_id = ? AND stock_ticker = ?", newTotalQuantity, newAveragePrice, userId, stockTicker);
    }

    private List<UserPortfolio> findUserPortfolio(Handle handle, Long userId) {
        return handle.createQuery("SELECT * FROM user_portfolios WHERE user_id = ?")
                .bind(0, userId)
                .map(new UserPortfolioMapper())
                .list();
    }

    private String getStockName(Handle handle, String stockTicker) {
        return handle.createQuery("SELECT stock_name FROM stocks WHERE stock_ticker = ?")
                .bind(0, stockTicker)
                .mapTo(String.class)
                .findFirst()
                .orElse(null);
    }

    private String getStockSector(Handle handle, String stockTicker) {
        return handle.createQuery("SELECT stock_sector FROM stocks WHERE stock_ticker = ?")
                .bind(0, stockTicker)
                .mapTo(String.class)
                .findFirst()
                .orElse(null);
    }

    public List<UserPortfolio> findUserPortfolio(Long userId) {
        Jdbi jdbi = Jdbi.create(dataSource);
        try(Handle handle = jdbi.open()){
            return handle.createQuery("SELECT * FROM user_portfolios WHERE user_id = ?")
                    .bind(0, userId)
                    .map(new UserPortfolioMapper())
                    .list();
        }
        catch (JdbiException e){
            LOGGER.error("JDBI exception at find user portfolio");
            throw new RuntimeException("Jdbi exception at find user portfolio");
        }
    }

    public List<PnL> findUserPnL(Long userId) {
        Jdbi jdbi = Jdbi.create(dataSource);
        try(Handle handle = jdbi.open()){
            return handle.createQuery("SELECT * FROM pnl WHERE user_id = ?")
                    .bind(0, userId)
                    .map(new PnLMapper())
                    .list();
        }
        catch (JdbiException e){
            LOGGER.error("JDBI exception at find user pnl");
            throw new RuntimeException("Jdbi exception at find user pnl");
        }
    }
}
