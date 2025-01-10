package com.project.commons.model;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.inject.Singleton;
import lombok.Data;

import java.math.BigDecimal;

@Singleton
@Data
@Serdeable.Serializable
public class PnL {
    private Long id;
    private Long userId;
    private String stockTicker;
    private BigDecimal realizedPnl;
    private BigDecimal unrealizedPnl;
}