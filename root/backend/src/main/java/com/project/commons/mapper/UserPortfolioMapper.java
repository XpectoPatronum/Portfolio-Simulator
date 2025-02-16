package com.project.commons.mapper;

import com.project.commons.model.UserPortfolio;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserPortfolioMapper implements RowMapper<UserPortfolio> {
        @Override
        public UserPortfolio map(ResultSet rs, StatementContext ctx) throws SQLException {
            UserPortfolio _userPortfolio = new UserPortfolio();

            _userPortfolio.setId(rs.getLong(1));
            _userPortfolio.setUserId(rs.getLong(2));
            _userPortfolio.setStockTicker(rs.getString(3));
            _userPortfolio.setStockName(rs.getString(4));
            _userPortfolio.setStockSector(rs.getString(5));
            _userPortfolio.setAveragePriceBought(rs.getBigDecimal(6));
            _userPortfolio.setTotalQuantity(rs.getInt(7));
            return _userPortfolio;
        }
}
