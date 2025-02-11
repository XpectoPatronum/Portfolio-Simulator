package com.project.apis.suggestions.connector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.apis.suggestions.model.SuggestionBuyRequest;
import com.project.apis.suggestions.model.SuggestionResponse;
import com.project.apis.suggestions.model.SuggestionSellRequest;
import io.micronaut.http.*;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

@ExecuteOn(TaskExecutors.BLOCKING)
@Singleton
public class SuggestionHttpClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(SuggestionHttpClient.class);

    @Inject
    @Client("/")
    HttpClient httpClient;

    public List<String> getBuyList(String query) {
        try {
            SuggestionBuyRequest requestBody = new SuggestionBuyRequest(query);
            HttpRequest<SuggestionBuyRequest> request = HttpRequest.POST(
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

    public List<String> getSellList(List<String> portfolioTickerList, String query) {
        try {
            SuggestionSellRequest requestBody = new SuggestionSellRequest(query,portfolioTickerList);
            HttpRequest<SuggestionSellRequest> request = HttpRequest.POST(
                            "https://auto-complete-sell.onrender.com/autocomplete", requestBody)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);

            HttpResponse<String> response = httpClient.toBlocking().exchange(request, String.class);

            String responseBody = response.getBody().orElse("");
            System.out.println(responseBody);
            if (responseBody.equals("{\"suggestions\":[]}")) {
                responseBody = "{\"suggestions\":[\"No similar stocks found in your portfolio\"]}";
            }

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
