package com.project.apis.portfolio.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.apis.portfolio.repository.PortfolioRepository;
import com.project.commons.model.PnL;
import com.project.commons.model.UserPortfolio;
import com.project.commons.repository.UserRepository;
import io.micronaut.data.connection.annotation.Connectable;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Singleton
public class PortfolioService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PortfolioService.class);

    @Inject
    PortfolioRepository portfolioRepository;

    @Inject
    UserRepository userRepository;

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

    @Connectable
    public List<UserPortfolio> showUserPortfolio(String userName){
        LOGGER.info("Retrieving User Portfolio");
        if(userRepository.existsByUsername(userName))
            return portfolioRepository.findUserPortfolio(userRepository.findByUsername(userName).get().getId());
        else
            return Collections.emptyList();
    }

    @Connectable
    public List<PnL> showUserPnL(String userName){
        LOGGER.info("Retrieving User PnL");
        if(userRepository.existsByUsername(userName))
            return portfolioRepository.findUserPnL(userRepository.findByUsername(userName).get().getId());
        else
            return Collections.emptyList();
    }
}