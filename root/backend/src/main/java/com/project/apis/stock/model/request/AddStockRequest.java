package com.project.apis.stock.model.request;

import com.project.commons.model.Stock;
import jakarta.inject.Singleton;

import java.time.LocalDate;

@Singleton
public class AddStockRequest {
    private Stock stock;
    private long quantity;
    private double priceBought;
    private LocalDate date;
}
