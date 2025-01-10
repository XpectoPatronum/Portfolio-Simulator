package com.project.commons.mapper;

import com.project.commons.model.Transaction;
import com.project.commons.model.UserPortfolio;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

public class TransactionMapper implements RowMapper<Transaction> {

    @Override
    public Transaction map(ResultSet rs, StatementContext ctx) throws SQLException {
        Transaction _transaction = new Transaction();

        _transaction.setId(rs.getLong(1));
        _transaction.setUserId(rs.getLong(2));
        _transaction.setStockTicker(rs.getString(3));
        _transaction.setTradeType(rs.getString(4));
        _transaction.setQuantity(rs.getInt(5));
        _transaction.setPrice(rs.getBigDecimal(6));
        _transaction.setTradeDate(rs.getObject(7, OffsetDateTime.class));
        _transaction.setRemainingQuantity(rs.getInt(8));
        return _transaction;
    }
}