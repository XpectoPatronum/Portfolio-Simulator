package com.project.commons.model;

import jakarta.inject.Singleton;
import lombok.Data;

import java.util.List;

@Singleton
public class WatchList {
    List<Stock> stockList;

}
