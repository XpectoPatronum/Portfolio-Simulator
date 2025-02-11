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
@AllArgsConstructor
@NoArgsConstructor
public class SuggestionSellRequest {
    @NonNull
    @JsonProperty("query")
    private String query;

    @NonNull
    @JsonProperty("texts")
    private List<String> texts;
}
