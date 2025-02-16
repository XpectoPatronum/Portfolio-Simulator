package com.project.apis.pnl;

import com.project.commons.model.PnL;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.inject.Singleton;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Singleton
@Serdeable.Serializable
@Data
public class PnLResponse {
    private String name;
    private BigDecimal RealizedProfit;
    private List<PnL> pnL;
}
