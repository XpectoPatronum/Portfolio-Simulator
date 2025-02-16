package com.project.apis.portfolio.model;

import com.project.commons.model.UserPortfolio;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.inject.Singleton;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Serdeable.Serializable
@Data
@Singleton
public class PortfolioResponse {
    private String name;
    private BigDecimal totalInvested;
    private List<UserPortfolio> portfolio;
}
