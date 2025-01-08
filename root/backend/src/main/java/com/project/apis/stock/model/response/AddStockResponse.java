package com.project.apis.stock.model.response;

import jakarta.inject.Singleton;
import lombok.Data;

@Data
@Singleton
public class AddStockResponse {
    private String ticker;
    private Long quantity;
    private boolean status;
    private String message;
}
