package com.project.apis.stock.model.request;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.inject.Singleton;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Singleton
@Data
@Introspected
@Serdeable.Deserializable
public class StockTransactionRequest {
    private String stock_ticker;
    private int quantity;
    private BigDecimal price;
    private LocalDate date;
}
