DROP TABLE IF EXISTS transactions;

CREATE TABLE transactions(
    id SERIAL PRIMARY KEY,
    name VARCHAR(40),
    date date,
    username VARCHAR(40)
);