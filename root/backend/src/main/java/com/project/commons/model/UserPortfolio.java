package com.project.commons.model;

import jakarta.inject.Singleton;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Singleton
public class UserPortfolio {
    private Long id;
    private Long userId;
    private String stockTicker;
    private String stockName;
    private String stockSector;
    private BigDecimal averagePriceBought;
    private int totalQuantity;

    public UserPortfolio() {}

    public UserPortfolio(Long id, Long userId, String stockTicker, String stockName, String stockSector, BigDecimal averagePriceBought, int totalQuantity) {
        this.id = id;
        this.userId = userId;
        this.stockTicker = stockTicker;
        this.stockName = stockName;
        this.stockSector = stockSector;
        this.averagePriceBought = averagePriceBought;
        this.totalQuantity = totalQuantity;
    }
}