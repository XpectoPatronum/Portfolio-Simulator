package com.project.apis.stock.service;

import com.project.apis.portfolio.service.PortfolioService;
import com.project.apis.stock.model.request.StockTransactionRequest;
import com.project.apis.stock.model.response.StockTransactionResponse;
import com.project.commons.model.User;
import com.project.commons.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class StockService {

    @Inject
    PortfolioService portfolioService;

    @Inject
    UserRepository userRepository;

    public StockTransactionResponse buyStock(String userName, StockTransactionRequest request) {
        if(userRepository.findByUsername(userName).isEmpty()){
            throw new RuntimeException();
        }
        else{
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
    }

    public StockTransactionResponse sellStock(String userName, StockTransactionRequest request){
        if(userRepository.findByUsername(userName).isEmpty()){
            throw new RuntimeException();
        }
        else{
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
}
