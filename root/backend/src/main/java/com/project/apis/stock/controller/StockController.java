package com.project.apis.stock.controller;

import com.project.apis.stock.model.request.AddStockRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
//import io.micronaut.security.annotation.Secured;
//import io.micronaut.security.rules.SecurityRule;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Publisher;

import java.security.Principal;

@Controller("app/v1/stock")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class StockController {

    @Post("/get")
    public String index() {
        return "Hi";
    }
}





