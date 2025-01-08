package com.project.apis.stock.controller;

import com.project.apis.stock.model.request.AddStockRequest;
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
import reactor.core.publisher.Mono;


@Controller("app/v1/stock")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class StockController {

    @Inject
    StockService stockService;

    @Post("/add")
    @Connectable
    public HttpResponse index(Authentication authentication, @Body AddStockRequest addStockRequest) {
         return HttpResponse.created(stockService.addStock(authentication.getName(), addStockRequest));
    }
}





