package com.project.apis.suggestions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.apis.stock.service.StockService;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.*;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.jackson.annotation.JacksonFeatures;
import io.micronaut.rxjava2.http.client.RxHttpClient;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.reactivex.Maybe;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExecuteOn(TaskExecutors.BLOCKING)
@Singleton
public class Connector {
    private static final Logger LOGGER = LoggerFactory.getLogger(Connector.class);

    @Inject
    @Client("/")
    HttpClient httpClient;

    public List<String> getList(String query) {
        try {
            SuggestionModel requestBody = new SuggestionModel(query);
            HttpRequest<SuggestionModel> request = HttpRequest.POST(
                            "https://auto-complete-flrm.onrender.com/autocomplete", requestBody)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);

            HttpResponse<String> response = httpClient.toBlocking().exchange(request, String.class);

            String responseBody = response.getBody().orElse("");

            ObjectMapper objectMapper = new ObjectMapper();
            SuggestionResponse suggestionResponse = objectMapper.readValue(responseBody, SuggestionResponse.class);
            return suggestionResponse.getSuggestions() != null ? suggestionResponse.getSuggestions() : Collections.emptyList();

        } catch (HttpClientResponseException e) {
            System.err.println("API Error: " + e.getStatus() + " - " + e.getResponse().getBody(String.class).orElse("No response body") + e.reason());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

}
