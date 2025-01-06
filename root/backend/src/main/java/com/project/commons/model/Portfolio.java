package com.project.commons.model;

import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class Portfolio {
    private long portfolio_id;
    private double investedAmount;
    private double currentAmount;
    private List<Stock> listOfShares;
}
