# 加密貨幣與其對應實質貨幣匯率 - DEMO
This is a practice of retrieving the cryptocurrency and fiat currency exchange rate API and performing CRUD operations.

**WARNING**: This is a practice project, and the data included is simulated. Any issues arising from its use are at your own risk.

## CoinDesk API 專案環境

- 專案使用

    - Build Tool : Maven
    - JDK : 8
    - Spring Boot : 2.7.6

- 後端使用框架: Spring Boot

- 資料庫: H2 (於伺服器啟動時會自動建立資空資料表)
    - 設定於application.properties
        - spring.sql.init.mode=always


## API spec

URL前綴:localhost:8080


| description                 | Http Method | URL                                  |
| --------------------------- | ----------- | ------------------------------------ |
| 取得H2資料庫資訊            | GET         | /coindesk/{chartName}/{currencyCode} |
| 新增H2資料庫資訊            | POST        | /coindesk |
|        更新H2資料庫資訊  |      PUT       |  /coindesk      |
|        刪除H2資料庫資訊  |    DELETE         |     /coindesk                                  |
| call外部API取得價密貨幣匯率 | GET         | /getCoinDeskInfo       |


- 取得H2資料庫資訊
    - URL 使用 PathVariable
        - 範例 : localhost:8080/coindesk/Bitcoin/USD
    - 資料回傳形式JSON
- 新增H2資料庫資訊
    - request body(範例)
        - {
          "chartName":"Bitcoin",
          "currencyCode": "USD",
          "rate": "57,756.298",
          "description": "United States Dollar",
          "symbol": "$",
          "rate_float": 57756.2982
          }
    - 注意:
        1. 若資料庫中已存在chartName與currencyCode的組合，則回傳失敗
        2. 若chartName與currencyCode不在程式內白名單(com.ruan.coinDesk.util.CurrencyMap)，則回傳失敗

- 更新H2資料庫資訊
    - request body(範例)
        - {
          "chartName":"Bitcoin",
          "currencyCode": "USD",
          "currencyChineseName":"測試123",
          "rate": "57,756.298",
          "description": "United States Dollar",
          "symbol": "$",
          "rate_float": 57756.2982
          }
        - 注意:
          1.會使用chartName與currencyCode搜尋資料庫是否有這筆資料，若無則回傳失敗

- 刪除H2資料庫資訊
    - request body(範例)
        - {
          "chartName":"Bitcoin",
          "currencyCode": "USD"
          }
    - 注意:
      1.會使用chartName與currencyCode搜尋資料庫是否有這筆資料，若無則回傳失敗


- call外部API取得價密貨幣匯率
    - 外部 API 設定於 application.properties 的 coindesk.api.url
    - 呼叫外部API取得加密貨幣的匯率表，進行資料處理(data process)後返回
    - 回傳範例:
        - {"updatedTime":"2024/09/02 07:07:20","updatedTimeISO":"2024/09/02 07:07:20","updatedTimeUK":"2024/09/02 08:07:00","cryptocurrency":"Bitcoin","cryptocurrencyChineseName":"比特幣","currency":[{"currencyCode":"USD","currencyChineseName":"美元","rate":"57756.2984"},{"currencyCode":"GBP","currencyChineseName":"英鎊","rate":"43984.0203"},{"currencyCode":"EUR","currencyChineseName":"歐元","rate":"52243.2865"}]}

## 單元測試
路徑 : src/test/java/com/ruan/coinDesk

- 數據轉換邏輯
    - DataTransferTest
- 幣別對應表 CRUD API Call Test
    - CallCoinDeskCRUDApiTest
- CoinDesk API Call Test
    - CallCoinDeskApiTest
- 資料轉換 API Call Test
    - CallCoinDeskDataTransferTest

## API 快速測試
可於專案內找到Postman檔案，進入後有一個檔案，demo.postman_collection.json，可直接匯入Postman
- 可以快速測試:
  1.當沒有資料時，使用查詢、更新、刪除的error message
  2.新增一筆資料後，更新再查詢，確認資料已被更新
  3.新增資料漏給資訊或資訊不合理所給的error message
    - 資訊不合理: 如chartName給Bitcoin123

## 備註
- 可以使用h2-console來操作H2 DB
    - 方法:啟動伺服器後，進入 localhost:8080/h2-console，\
        - 帳號:sa
        - 密碼:password
        - JDBC_URL : jdbc:h2:mem:coindesk
    - 使用者帳號密碼設置於application.properties
        - spring.datasource.username=sa
        - spring.datasource.password=password

- SQL建立語法
```
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
```
