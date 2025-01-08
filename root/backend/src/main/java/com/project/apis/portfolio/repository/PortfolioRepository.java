package com.project.apis.portfolio.repository;

import com.project.commons.model.Transaction;
import com.project.commons.model.UserPortfolio;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Inject;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.JdbiException;
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
    public boolean updatePortfolio(Long userId, String stockTicker, BigDecimal price, long quantity) {
        Jdbi jdbi = Jdbi.create(dataSource);
        try (Handle handle = jdbi.open()) {
            List<UserPortfolio> portfolio = findUserPortfolio(handle, userId);
            Optional<UserPortfolio> existingStock = portfolio.stream()
                    .filter(entry -> entry.getStockTicker().equals(stockTicker))
                    .findFirst();

            if (existingStock.isPresent()) {
                UserPortfolio portfolioEntry = existingStock.get();
                long newTotalQuantity = portfolioEntry.getTotalQuantity() + quantity;
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

            UserPortfolio portfolioEntry = portfolioStock.get();

            // 2. Process Transactions in FIFO order
            List<Transaction> buyTransactions = getBuyTransactionsWithRemainingQuantity(handle, userId, stockTicker);

            for (Transaction buyTransaction : buyTransactions) {
                if (remainingQuantityToSell == 0) break;

                int quantityFromThisTransaction = Math.min(remainingQuantityToSell, buyTransaction.getRemainingQuantity());

                // 3. Record the sell Transaction
                recordSellTransaction(handle, userId, stockTicker, quantityFromThisTransaction, sellPrice);

                remainingQuantityToSell -= quantityFromThisTransaction;

                // 4. Update the remaining quantity in the buy Transaction
                int newRemainingQuantity = buyTransaction.getRemainingQuantity() - quantityFromThisTransaction;
                updateBuyTransactionRemainingQuantity(handle, buyTransaction.getId(), newRemainingQuantity);

                totalCostOfSoldShares = totalCostOfSoldShares.add(buyTransaction.getPrice().multiply(BigDecimal.valueOf(quantityFromThisTransaction)));
            }

            // 5. Update the portfolio
            int newTotalQuantity = portfolioEntry.getTotalQuantity() - quantityToSell;
            if(newTotalQuantity == 0) {
                removePortfolioEntry(handle, userId, stockTicker);
            } else {
                BigDecimal newAveragePrice;
                if (newTotalQuantity > 0) {
                    BigDecimal remainingTotalValue = portfolioEntry.getAveragePriceBought().multiply(BigDecimal.valueOf(portfolioEntry.getTotalQuantity())).subtract(totalCostOfSoldShares);
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

    private List<Transaction> getBuyTransactionsWithRemainingQuantity(Handle handle, Long userId, String stockTicker) {
        return handle.createQuery("SELECT * FROM Transactions WHERE user_id = ? AND stock_ticker = ? AND Transaction_type = 'BUY' AND remaining_quantity > 0 ORDER BY Transaction_date ASC")
                .bind(0, userId)
                .bind(1, stockTicker)
                .mapToBean(Transaction.class)
                .list();
    }

    private void updateBuyTransactionRemainingQuantity(Handle handle, Long TransactionId, int remainingQuantity) {
        handle.execute("UPDATE Transactions SET remaining_quantity = ? WHERE id = ?", remainingQuantity, TransactionId);
    }

    private void recordSellTransaction(Handle handle, Long userId, String stockTicker, int quantity, BigDecimal price){
        handle.execute("INSERT INTO Transactions (user_id, stock_ticker, Transaction_type, quantity, price) VALUES (?, ?, 'SELL', ?, ?)",
                userId, stockTicker, quantity, price);
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
                .mapToBean(UserPortfolio.class) // Use mapToBean for simpler mapping
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
}
