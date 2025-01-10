package com.project.apis.portfolio;


import com.project.apis.portfolio.service.PortfolioService;
import com.project.apis.stock.model.request.StockTransactionRequest;
import com.project.apis.stock.service.StockService;
import io.micronaut.data.connection.annotation.Connectable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;

@Controller("app/v1/portfolio")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class PortfolioController {

    @Inject
    PortfolioService portfolioService;

    @Get("/show")
    @Connectable
    public HttpResponse showPortfolioForUser(Authentication authentication) {
        return HttpResponse.ok(portfolioService.showUserPortfolio(authentication.getName()));
    }
}