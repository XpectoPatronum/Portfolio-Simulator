package com.project.apis.suggestions;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;

@Controller("app/v1/search")
@Secured(SecurityRule.IS_ANONYMOUS)
@Introspected
@ExecuteOn(TaskExecutors.BLOCKING)
public class SuggestionsController{

    @Inject
    Connector connector;
    @Get("/suggestion/buy/{query}")
    public HttpResponse<?> buySuggestions(@PathVariable String query) {
        return HttpResponse.ok(connector.getList(query));
    }

    @Get("/suggestion/sell/{query}")
    public HttpResponse<List<String>> sellSuggestions(@PathVariable String query) {
        return HttpResponse.ok(List.of(query, query + "b"));
    }
}