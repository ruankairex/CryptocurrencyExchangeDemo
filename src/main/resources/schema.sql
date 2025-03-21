-- 建立幣別資料表
DROP TABLE IF EXISTS bitcoin_exchange_rates;
CREATE TABLE bitcoin_exchange_rates (
    currency_num BIGINT AUTO_INCREMENT PRIMARY KEY,     -- 流水號
    code VARCHAR(10) ,                   -- 幣別代碼
    chinese_name NVARCHAR(50),                 -- 幣別中文名稱
    rate DECIMAL(15, 6),                       -- 匯率
    description NVARCHAR(100),                          -- 幣別描述
    symbol VARCHAR(20),                                 -- 幣別符號
    last_updated DATETIME                   -- 更新時間
);

DROP TABLE IF EXISTS crypto_currency_exchange_rates;
CREATE TABLE crypto_currency_exchange_rates (
    currency_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    chart_name VARCHAR(20) NOT NULL,
    currency_code VARCHAR(3) NOT NULL,  -- 如 USD, GBP, EUR
    currency_chinese_name NVARCHAR(50),                 -- 幣別中文名稱
    currency_symbol VARCHAR(10),        -- 如 $, £, €
    rate VARCHAR(50),                   -- 匯率的字串格式，例如 "57,756.298"
    rate_float DECIMAL(15, 4),          -- 匯率的數字格式，精確到小數點後四位
    description VARCHAR(100),           -- 貨幣的描述，例如 "United States Dollar"
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- 最後更新時間
);

