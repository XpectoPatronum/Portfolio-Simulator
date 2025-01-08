package com.project.commons.model;

import jakarta.inject.Singleton;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Singleton
@Data
public class Transaction {
    private Long id;
    private Long userId;
    private String stockTicker;
    private String tradeType; // "BUY" or "SELL"
    private int quantity;
    private BigDecimal price;
    private OffsetDateTime tradeDate;
    private int remainingQuantity;
}
