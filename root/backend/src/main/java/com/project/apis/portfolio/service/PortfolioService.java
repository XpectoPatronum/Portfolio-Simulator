package com.project.apis.portfolio.service;

import com.project.apis.pnl.PnLResponse;
import com.project.apis.portfolio.model.PortfolioResponse;
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
import java.util.List;
import java.util.Objects;

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
    public PortfolioResponse showUserPortfolio(String userName){
        LOGGER.info("Retrieving User Portfolio");
        if(userRepository.existsByUsername(userName)){
            List<UserPortfolio> portfolio = portfolioRepository.findUserPortfolio(userRepository.findByUsername(userName).get().getId());
            PortfolioResponse res = new PortfolioResponse();
            res.setPortfolio(portfolio);
            res.setName(userRepository.findByUsername(userName).get().getName());
            res.setTotalInvested(portfolio.stream()
                    .map(pf -> pf.getAveragePriceBought().multiply(BigDecimal.valueOf(pf.getTotalQuantity())))
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
            return res;
        }
        else
            throw new RuntimeException("No such user");
    }

    @Connectable
    public PnLResponse showUserPnL(String userName){
        LOGGER.info("Retrieving User PnL");
        if(userRepository.existsByUsername(userName)) {
            List<PnL> list = portfolioRepository.findUserPnL(userRepository.findByUsername(userName).get().getId());
            PnLResponse res = new PnLResponse();
            res.setPnL(list);
            res.setName(userRepository.findByUsername(userName).get().getName());
            res.setRealizedProfit(list.stream()
                    .map(PnL::getRealizedPnl)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
            return res;
        }
        else
            throw new RuntimeException("No such user");
    }
}