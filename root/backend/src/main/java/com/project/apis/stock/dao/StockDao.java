package com.project.apis.stock.dao;

import com.project.commons.model.User;
import com.project.commons.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.JdbiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

@Singleton
public class StockDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockDao.class);

    @Inject
    DataSource dataSource;

    public boolean stockPresent(String stockTicker){
        Jdbi jdbi = Jdbi.create(dataSource);
        try (Handle handle = jdbi.open()) {
            String query = "SELECT EXISTS(SELECT 1 FROM stocks WHERE stock_ticker = ?)";
            return handle.createQuery(query)
                    .bind(0, stockTicker)
                    .mapTo(Boolean.class)
                    .one();
        }
        catch (JdbiException e){
            LOGGER.error("JDBI exception at stockPresent");
            throw new RuntimeException("JDBI exception at stockPresent");
        }
    }

    public String getstockName(String stockTicker){
        Jdbi jdbi = Jdbi.create(dataSource);
        try (Handle handle = jdbi.open()) {
            String query = "SELECT stock_name from stocks where stock_ticker=?";
            return handle.createQuery(query)
                    .bind(0, stockTicker)
                    .mapTo(String.class)
                    .one();
        }
        catch (JdbiException e){
            LOGGER.error("JDBI exception at getStockName");
            throw new RuntimeException("JDBI exception at getStockName");
        }
    }
}
