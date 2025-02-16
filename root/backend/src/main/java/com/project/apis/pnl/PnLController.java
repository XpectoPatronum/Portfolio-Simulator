package com.project.apis.pnl;

import com.project.apis.portfolio.service.PortfolioService;
import io.micronaut.data.connection.annotation.Connectable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;

@Controller("app/v1/pnl")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class PnLController {

    @Inject
    PortfolioService portfolioService;

    @Get("/show")
    @Connectable
    public HttpResponse showPnLForUser(Authentication authentication) {
        return HttpResponse.ok(portfolioService.showUserPnL(authentication.getName()));
    }
}