package com.project.apis.stock.model.response;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.inject.Singleton;
import lombok.Data;

@Data
@Singleton
@Serdeable.Serializable
public class StockTransactionResponse {
    private String ticker;
    private int quantity;
    private boolean status;
}
