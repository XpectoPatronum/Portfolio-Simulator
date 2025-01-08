package com.project.apis.portfolio.service;

import com.project.apis.portfolio.repository.PortfolioRepository;
import io.micronaut.data.connection.annotation.Connectable;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Singleton
public class PortfolioService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PortfolioService.class);

    @Inject
    PortfolioRepository portfolioRepository;

    @Connectable
    public boolean addStockInPortfolio(Long userId, String stockTicker, BigDecimal price, long quantity) {
        LOGGER.info("Adding stock in portfolio");
        return portfolioRepository.updatePortfolio(userId, stockTicker, price, quantity);
    }
}