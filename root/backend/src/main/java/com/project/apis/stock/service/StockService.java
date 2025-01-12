package com.project.apis.stock.service;

import com.project.apis.portfolio.service.PortfolioService;
import com.project.apis.stock.model.request.StockTransactionRequest;
import com.project.apis.stock.model.response.StockTransactionResponse;
import com.project.commons.model.User;
import com.project.commons.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.AttributeNotFoundException;

@Singleton
public class StockService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockService.class);

    @Inject
    PortfolioService portfolioService;

    @Inject
    UserRepository userRepository;

    @Inject
    ValidatorService validatorService;

    public StockTransactionResponse buyStock(String userName, StockTransactionRequest request) {
        if(userRepository.findByUsername(userName).isEmpty()){
            LOGGER.error("No such user");
            throw new RuntimeException();
        }
        if(!validatorService.validate(request)){
            LOGGER.error("Invalid Buy Request");
            throw new RuntimeException("Invalid Buy Request");
        }
        User user = userRepository.findByUsername(userName).get();
        StockTransactionResponse response = new StockTransactionResponse();
        response.setQuantity(request.getQuantity());
        response.setTicker(request.getStock_ticker());
        response.setStatus(portfolioService.buyStockToPortfolio(
                user.getId(),
                request.getStock_ticker(),
                request.getPrice(),
                request.getQuantity())
        );
        return response;
    }

    public StockTransactionResponse sellStock(String userName, StockTransactionRequest request){
        if(userRepository.findByUsername(userName).isEmpty()){
            throw new RuntimeException();
        }
        if(!validatorService.validate(request)){
            LOGGER.error("Invalid Sell Request");
            throw new RuntimeException("Invalid Sell Request");
        }
        User user = userRepository.findByUsername(userName).get();
        StockTransactionResponse response = new StockTransactionResponse();
        response.setQuantity(request.getQuantity());
        response.setTicker(request.getStock_ticker());
        response.setStatus(portfolioService.sellStockFromPortfolio(
                user.getId(),
                request.getStock_ticker(),
                request.getPrice(),
                request.getQuantity())
        );
        return response;
    }
}
