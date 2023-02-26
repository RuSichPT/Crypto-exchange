ALTER TABLE users
ADD wallet_id INTEGER;

DROP TABLE IF EXISTS wallets;

CREATE TABLE wallets(
    id SERIAL PRIMARY KEY,
    btc NUMERIC,
    ton NUMERIC,
    rub NUMERIC
);

ALTER TABLE users
ADD CONSTRAINT wallets_fk FOREIGN KEY (wallet_id) REFERENCES "wallets" (id);