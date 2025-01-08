package com.project.apis.stock.model.response;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.inject.Singleton;
import lombok.Data;

@Data
@Singleton
@Serdeable.Serializable
public class AddStockResponse {
    private String ticker;
    private Long quantity;
    private boolean status;
    private String message;
}
