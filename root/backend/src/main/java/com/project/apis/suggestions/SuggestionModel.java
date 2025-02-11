package com.project.apis.suggestions;

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
public class SuggestionModel {
    @JsonProperty("text") // Ensures the field is serialized as "text"
    private String text;

    public SuggestionModel(String text) {
        this.text = text;
    }

}
