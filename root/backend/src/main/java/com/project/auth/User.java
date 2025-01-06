package com.project.auth;

import com.project.commons.model.PnL;
import com.project.commons.model.Portfolio;
import com.project.commons.model.WatchList;
import jakarta.inject.Singleton;
import lombok.Data;

@Data
@Singleton
public class User {
    private Long id;
    private String username;
    private String hashedPassword;
    private WatchList watchlist;
    private Portfolio portfolio;
    private PnL pnL;
}
