package com.project.apis.suggestions.service;

import com.project.apis.portfolio.model.PortfolioResponse;
import com.project.apis.portfolio.service.PortfolioService;
import com.project.apis.suggestions.connector.SuggestionHttpClient;
import com.project.commons.model.UserPortfolio;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

@Singleton
public class SuggestionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SuggestionService.class);

    @Inject
    PortfolioService portfolioService;

    @Inject
    SuggestionHttpClient suggestionHttpClient;

    public List<String> buySuggestions(String query) {
        return suggestionHttpClient.getBuyList(query);
    }

    public List<String> sellSuggestions(String username, String query){
        PortfolioResponse res = portfolioService.showUserPortfolio(username);
        if(res.getPortfolio().isEmpty()){
            List<String> stockSuggestions = Arrays.asList("");
            return suggestionHttpClient.getSellList(stockSuggestions,query);
        }
        List<String> portfolioStockTickers = res.getPortfolio().stream()
                .map(UserPortfolio::getStockTicker).toList();
        return suggestionHttpClient.getSellList(portfolioStockTickers,query);
    }
}
