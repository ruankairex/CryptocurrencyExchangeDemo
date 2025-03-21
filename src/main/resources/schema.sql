DROP TABLE IF EXISTS crypto_currency_exchange_rates;
CREATE TABLE crypto_currency_exchange_rates (
    currency_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    chart_name VARCHAR(20) NOT NULL,
    currency_code VARCHAR(3) NOT NULL,
    currency_chinese_name NVARCHAR(50),
    currency_symbol VARCHAR(10),
    rate VARCHAR(50),
    rate_float DECIMAL(15, 4),
    description VARCHAR(100),
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

