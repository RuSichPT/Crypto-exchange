# Crypto-exchange

## �����

�������� ������ **RESTfull API service** ������������ ����� ����� ��� ���������� ������ ��������������. ����������
������������� ���������� ���������� �����������.  
� ������� �������������� ��� ����: **������������** � **�������������**

### ������������ ����� �����������

- ������������������
- ��������� ������
- ������� ������
- ������/������� ������
- ���������� ���������� ���� �����

### ������������� ����� �����������

- �������� ���� ������
- ���������� ���������� �� ���� ��������� �������������
- ���������� �� ������ �� ������������ ���������� �������
- ���������� ���������� ���� �����

## ����������� �������������� �������

- ����������� ���� ������ PostgresSQL
- ����������� Swagger

## �������������� ����������

- Java 17
- Spring Boot
- Spring Web
- Spring Data
- Maven
- Lombok
- PostgresSQL
- Flyway
- Swagger
- DataJpaTest  

## ������
��������� ���� ������

    docker compose up -d

��������� �  application.properties ��������� ����: 

    spring.datasource.username=${DATA_BASE_USERNAME}  
    spring.datasource.password=${DATA_BASE_PASSWORD}  

��� ������� ���������� ����� DATA_BASE_USERNAME, DATA_BASE_PASSWORD

# �������� API ��������������� � ������� Swagger (Open API):

http://localhost:8080/swagger-ui.html

## ������� ��������:

���� ���� �������� �������� ��� postman � �����. ��� ����� �������������  
Crypto-exchange.postman_collection.json

### API ������������:

##### 1. ����������� ������ ������������:

    POST api/v1/crypto/registration  
    Body:  
    {  
        "username": "vasya_vezunchik",  
        "email": "vasyu_kolbasit@mail.ru"  
    }

##### 2. �������� ������� ������ ��������:

    GET api/v1/crypto/wallet
    Parameters:
        secretKey = "1ce21b405cf5dcaa8eb553f1ef9a03d5"

##### 3. ���������� ��������.

    POST api/v1/crypto/wallet/add
    Body:  
    {
        "secret_key": "1ce21b405cf5dcaa8eb553f1ef9a03d5",
        "wallet_name": "RUB_WALLET",
        "value": 1000
    }

##### 4. ����� ����� � �����.

    POST api/v1/crypto/wallet/add
    Body:  
    {
        "secret_key": "1ce21b405cf5dcaa8eb553f1ef9a03d5",
        "wallet_name": "RUB_WALLET",
        "value": 1000,
        "wallet": "AsS5A2SASd2as3q5sd2asd53a1s5"
    }

���

    POST api/v1/crypto/wallet/add
    Body:  
    {
        "secret_key": "1ce21b405cf5dcaa8eb553f1ef9a03d5",
        "wallet_name": "RUB_WALLET",
        "value": 1000,
        "credit_card": "1234 5678 9012 3456"
    }

##### 5. �������� ���������� ������ ����� (�������� � ��������������).

    GET api/v1/crypto/currency
    Parameters:
        secretKey = "1ce21b405cf5dcaa8eb553f1ef9a03d5"
        currencyName: "BTC:"

##### 6. ����� ����� �� �������������� �����.

    POST api/v1/crypto/wallet/exchange
    Body:
    {
        "secret_key": "1ce21b405cf5dcaa8eb553f1ef9a03d5",
        "currency_from": "RUB",
        "currency_to": "TON",
        "amount": "2000"
    }

### API ��������������:

##### 1. �������� ���� �����.

    POST api/v1/crypto/currency/exchange
    {
        "secret_key": "29e60d5ed9e91b1490501aa197c90515",
        "base_currency": "TON",
        "currencies":
         {
            "BTC": "0.000096",
            "RUB": "180"
        }
    }

##### 2. ���������� ����� ����� �� ���� ���������������� ������ ��� ��������� ������.

    GET api/v1/crypto/currency/stat/sum
    Parameters:
        secretKey: "29e60d5ed9e91b1490501aa197c90515",
        currencyName: "RUB"

##### 3. ���������� ���������� ��������, ������� ���� ��������� �� ��������� ������

    GET api/v1/crypto/currency/stat/transaction
    Parameters:
        secretKey: "29e60d5ed9e91b1490501aa197c90515",
        dateFrom: "26.02.2023",
        dateTo: "01.03.2023"

