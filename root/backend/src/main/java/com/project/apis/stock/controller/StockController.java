package com.project.apis.stock.controller;

import com.project.apis.stock.model.request.AddStockRequest;
import com.project.apis.stock.service.StockService;
import io.micronaut.data.connection.annotation.Connectable;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpHeaders;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.reactivestreams.Publisher;


@Controller("app/v1/stock")
@Secured(SecurityRule.IS_AUTHENTICATED)
@Transactional
@Connectable
public class StockController {

    @Inject
    StockService stockService;

    @Post("/add")
    public Publisher<? extends HttpResponse> index(Authentication authentication, @Body AddStockRequest addStockRequest) {
        return Flowable.fromCallable(()->{
            return HttpResponse.ok();
//            return HttpResponse.created(stockService.addStock(authentication.getName(),addStockRequest));
        }).subscribeOn(Schedulers.io());
    }
}





