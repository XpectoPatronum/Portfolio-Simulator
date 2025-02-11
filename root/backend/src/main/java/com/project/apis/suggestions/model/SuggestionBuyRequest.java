package com.project.apis.suggestions.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Serdeable
@Introspected
@Serdeable.Serializable
public class SuggestionBuyRequest {
    @JsonProperty("text") // Ensures the field is serialized as "text"
    private String text;

    public SuggestionBuyRequest(String text) {
        this.text = text;
    }

}
