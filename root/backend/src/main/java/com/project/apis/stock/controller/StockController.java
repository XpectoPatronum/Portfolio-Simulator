package com.project.apis.stock.controller;

import com.project.apis.stock.model.request.StockTransactionRequest;
import com.project.apis.stock.service.StockService;
import io.micronaut.data.connection.annotation.Connectable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;


@Controller("app/v1/stock")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class StockController {

    @Inject
    StockService stockService;

    @Post("/buy")
    @Connectable
    public HttpResponse buyStock(Authentication authentication, @Body StockTransactionRequest stockTransactionRequest) {
         return HttpResponse.created(stockService.buyStock(authentication.getName(), stockTransactionRequest));
    }

    @Post("/sell")
    @Connectable
    public HttpResponse sellStock(Authentication authentication, @Body StockTransactionRequest stockTransactionRequest) {
        return HttpResponse.created(stockService.sellStock(authentication.getName(), stockTransactionRequest));
    }
}





