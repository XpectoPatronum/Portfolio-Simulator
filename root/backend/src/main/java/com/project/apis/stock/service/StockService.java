package com.project.apis.stock.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.apis.portfolio.service.PortfolioService;
import com.project.apis.stock.model.request.AddStockRequest;
import com.project.apis.stock.model.response.AddStockResponse;
import com.project.commons.repository.UserRepository;
import io.reactivex.Maybe;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class StockService {

    @Inject
    PortfolioService portfolioService;

    @Inject
    UserRepository userRepository;

    public AddStockResponse addStock(String userName, AddStockRequest request){
        AddStockResponse response = new AddStockResponse();
        Long userId= userRepository.findByUsername(userName).get().getId();
//        response.setStatus(portfolioService.addStockInPortfolio(userId,request.getStock_ticker(),request.getPriceBought(),request.getQuantity()));
        response.setQuantity(request.getQuantity());
        response.setTicker(request.getStock_ticker());
        response.setMessage("Successfully Added!");
        return response;
    }
}
