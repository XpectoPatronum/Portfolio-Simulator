package com.project.commons.mapper;

import com.project.commons.model.PnL;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PnLMapper implements RowMapper<PnL> {

    @Override
    public PnL map(ResultSet rs, StatementContext ctx) throws SQLException{
        PnL _pnL = new PnL();
        _pnL.setId(rs.getLong(1));
        _pnL.setUserId(rs.getLong(2));
        _pnL.setStockTicker(rs.getString(3));
        _pnL.setRealizedPnl(rs.getBigDecimal(4));
        _pnL.setUnrealizedPnl(rs.getBigDecimal(5));

        return _pnL;
    }


}
