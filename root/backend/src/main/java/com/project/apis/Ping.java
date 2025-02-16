package com.project.apis;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("app/v1")
@Secured(SecurityRule.IS_ANONYMOUS)
public class Ping {
    private static final Logger LOGGER = LoggerFactory.getLogger(Ping.class);

    @Get("/ping")
    public HttpResponse<?> showPortfolioForUser() {
        LOGGER.info("Ping request intercepted!");
        return HttpResponse.ok("pong");
    }
}
