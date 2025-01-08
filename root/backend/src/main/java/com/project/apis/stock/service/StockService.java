package com.project.apis.stock.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.apis.portfolio.service.PortfolioService;
import com.project.apis.stock.model.request.AddStockRequest;
import com.project.apis.stock.model.response.AddStockResponse;
import com.project.commons.model.User;
import com.project.commons.repository.UserRepository;
import io.reactivex.Maybe;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

@Singleton
public class StockService {

    @Inject
    PortfolioService portfolioService;

    @Inject
    UserRepository userRepository;

    public AddStockResponse addStock(String userName, AddStockRequest request) {
        if(userRepository.findByUsername(userName).isEmpty()){
            throw new RuntimeException();
        }
        else{
            User user = userRepository.findByUsername(userName).get();
            AddStockResponse response = new AddStockResponse();
            response.setQuantity(request.getQuantity());
            response.setTicker(request.getStock_ticker());
            response.setMessage("Successfully Added!");
            response.setStatus(portfolioService.addStockInPortfolio(
                    user.getId(),
                    request.getStock_ticker(),
                    request.getPriceBought(),
                    request.getQuantity())
            );
            return response;
        }
    }
}
