package com.project.apis.suggestions.Controller;

import com.project.apis.suggestions.connector.SuggestionHttpClient;
import com.project.apis.suggestions.service.SuggestionService;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;

import java.util.List;

@Controller("app/v1/search")
@Secured(SecurityRule.IS_AUTHENTICATED)
@Introspected
@ExecuteOn(TaskExecutors.BLOCKING)
public class SuggestionsController{

    @Inject
    SuggestionService suggestionService;

    @Get("/suggestion/buy/{query}")
    public HttpResponse<?> buySuggestions(Authentication authentication, @PathVariable String query) {
        return HttpResponse.ok(suggestionService.buySuggestions(query));
    }

    @Get("/suggestion/sell/{query}")
    public HttpResponse<List<String>> sellSuggestions(Authentication authentication, @PathVariable String query) {
        return HttpResponse.ok(suggestionService.sellSuggestions(authentication.getName(),query));
    }
}