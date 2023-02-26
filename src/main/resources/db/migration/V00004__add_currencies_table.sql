DROP TABLE IF EXISTS currencies;

CREATE TABLE currencies(
    name VARCHAR(40) PRIMARY KEY,
    btc NUMERIC,
    ton NUMERIC,
    rub NUMERIC
);