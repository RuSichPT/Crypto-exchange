DROP TABLE IF EXISTS admins;

CREATE TABLE admins(
    username VARCHAR(40) PRIMARY KEY,
    email VARCHAR(40) UNIQUE,
    secret_key VARCHAR(40)
);