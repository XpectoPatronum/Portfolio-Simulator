package com.project.apis.suggestions.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.serde.annotation.Serdeable;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Serdeable
@Introspected
@Serdeable.Serializable
@NoArgsConstructor
public class SuggestionSellRequest {
    @JsonProperty("query")
    private String query;

    @JsonProperty("texts")
    private List<String> texts;

    public SuggestionSellRequest(String query, List<String> texts) {
        this.query = query;
        this.texts = texts;
    }

    @Override
    public String toString() {
        return "SuggestionSellRequest{" +
                "query='" + query + '\'' +
                ", texts=" + texts +
                '}';
    }
}
