package com.project.commons.model;

import jakarta.inject.Singleton;

@Singleton
public class Stock {
    private String stockISIN;
    private String ticker;
    private double currentPrice;
    private Sector sector;
}
