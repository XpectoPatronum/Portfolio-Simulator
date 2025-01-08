package com.project.apis.portfolio.service;

import com.project.apis.portfolio.repository.PortfolioRepository;
import com.project.apis.stock.service.StockService;
import com.project.commons.model.UserPortfolio;
import com.project.commons.repository.UserRepository;
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

    public boolean addStockInPortfolio(Long userId, String stockTicker, BigDecimal price, long quantity){
        LOGGER.info("Adding stock in portfolio");
        return portfolioRepository.updatePortfolio(userId, stockTicker, price, quantity);
    }
}
