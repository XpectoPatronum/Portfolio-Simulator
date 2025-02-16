select * from stocks;

select * from transactions;

select * from user_portfolios up;

select * from pnl p ;

CREATE TABLE if not exists pnl (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    stock_ticker VARCHAR(255) NOT NULL,
    realized_pnl DECIMAL(19, 4) NOT NULL DEFAULT 0.00, -- Total realized PnL for this stock
    unrealized_pnl DECIMAL(19, 4) NOT NULL DEFAULT 0.00, -- Unrealized PnL based on current price
    UNIQUE (user_id, stock_ticker)
);

drop table transactions;

drop table user_portfolios ;

delete from pnl ;
delete from user_portfolios ;
delete from transactions ;

CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    stock_ticker VARCHAR(255) NOT NULL,
    transaction_type VARCHAR(4) NOT NULL CHECK (transaction_type IN ('BUY', 'SELL')),
    quantity INTEGER NOT NULL CHECK (quantity > 0), -- CHECK constraint to ensure quantity > 0
    price DECIMAL(19, 4) NOT NULL,
    transaction_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    remaining_quantity INTEGER NOT NULL CHECK (remaining_quantity >= 0) -- Remaining quantity cannot be negative
);

CREATE TABLE user_portfolios (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,  -- Foreign key referencing the users table
    stock_ticker VARCHAR(255) NOT NULL,
    stock_name VARCHAR(255),  -- Optional: Store stock name for convenience
    stock_sector VARCHAR(255),-- Optional: Store stock sector for convenience
    average_price_bought DECIMAL NOT NULL,
    total_quantity INTEGER NOT NULL,
    UNIQUE (user_id, stock_ticker), -- Prevents duplicate entries for the same stock for a user
    FOREIGN KEY (user_id) REFERENCES users(id) -- Ensure referential integrity
);