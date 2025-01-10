package com.project.apis.portfolio.service;

import com.project.apis.portfolio.repository.PortfolioRepository;
import io.micronaut.data.connection.annotation.Connectable;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

@Singleton
public class PortfolioService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PortfolioService.class);

    @Inject
    PortfolioRepository portfolioRepository;

    @Connectable
    public boolean buyStockToPortfolio(Long userId, String stockTicker, BigDecimal price, int quantity) {
        LOGGER.info("Buying {} stock", stockTicker);
        portfolioRepository.addBuyTransaction(userId, stockTicker, quantity, price);
        return portfolioRepository.processBuyOrder(userId, stockTicker, price, quantity);

    }

    @Connectable
    public boolean sellStockFromPortfolio(Long userId, String stockTicker, BigDecimal price, int quantity){
        LOGGER.info("Selling {} stock", stockTicker);
        return portfolioRepository.processSellOrder(userId,stockTicker,quantity,price);
    }
}