package com.project.apis.stock.service;

import com.project.apis.stock.dao.StockDao;
import com.project.apis.stock.model.request.StockTransactionRequest;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

@Singleton
public class ValidatorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidatorService.class);

    @Inject
    StockDao stockDao;

    public boolean validate(StockTransactionRequest request){
        LOGGER.info("Validating the request");
        BigDecimal zero = new BigDecimal(0);
        boolean isValid = (stockDao.stockPresent(request.getStock_ticker())
                && request.getQuantity()>0
                && request.getPrice().compareTo(zero)>0
        );
        LOGGER.info(isValid?"Valid Request, Proceeding...":"Invalid Request, Abort!!");
        return isValid;
    }
}
