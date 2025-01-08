package com.project.commons.model;

import jakarta.inject.Singleton;
import lombok.Data;


@Singleton
@Data
public class Stock {
    private String stockISIN;
    private String stockTicker;
    private String stockName;
    private double currentPrice;
    private double volumePreviousDay;
    private String sector;
}
