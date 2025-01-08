package com.project.apis.portfolio.repository;

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
