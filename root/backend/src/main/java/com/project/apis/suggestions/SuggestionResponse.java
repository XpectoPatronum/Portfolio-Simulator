package com.project.apis.suggestions;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class SuggestionResponse {
    @JsonProperty("suggestions")
    private List<String> suggestions;

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }
}