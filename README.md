# Crypto-exchange#
## �����
�������� ������ **RESTfull API service** ������������ ����� ����� ��� ���������� ������ ��������������.
���������� ������������� ���������� ���������� �����������.  
� ������� �������������� ��� ����: **������������** � **�������������**

### ������������ ����� �����������
- ������������������
- ��������� ������
- ������� ������
- ������/������� ������

### ������������� ����� �����������
- �������� ���� ������
- ���������� ���������� �� ���� ��������� �������������
- ���������� �� ������ �� ������������ ���������� �������

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

# API:
http://localhost:8080/swagger-ui.html

### �������:

### API ������������:

##### 1. ����������� ������ ������������:

    POST /crypto/registration  
    Body:  
    {  
        "username": "vasya_vezunchik",  
        "email": "vasyu_kolbasit@mail.ru"  
    }

##### 2. �������� ������� ������ ��������:

    GET /crypto/wallet
    Body:  
    {
        "secret_key": "1ce21b405cf5dcaa8eb553f1ef9a03d5"
    }

##### 3. ���������� ��������.

    POST /crypto/wallet/add
    Body:  
    {
        "secret_key": "1ce21b405cf5dcaa8eb553f1ef9a03d5",
        "wallet_name": "RUB_WALLET",
        "value": 1000
    }

##### 5. ����� ����� � �����.

    POST /crypto/wallet/add
    Body:  
    {
        "secret_key": "1ce21b405cf5dcaa8eb553f1ef9a03d5",
        "wallet_name": "RUB_WALLET",
        "value": 1000,
        "wallet": "AsS5A2SASd2as3q5sd2asd53a1s5"
    }
���  

    POST /crypto/wallet/add
    Body:  
    {
        "secret_key": "1ce21b405cf5dcaa8eb553f1ef9a03d5",
        "wallet_name": "RUB_WALLET",
        "value": 1000,
        "credit_card": "1234 5678 9012 3456"
    }
##### 5. �������� ���������� ������ ����� (�������� � ��������������).

    GET /crypto/currency
    Body:
    {
        "secret_key": "1ce21b405cf5dcaa8eb553f1ef9a03d5",
        "currency": "BTC"
    }
##### 6. ����� ����� �� �������������� �����.

    POST /crypto/exchange
    Body:
    {
        "secret_key": "1ce21b405cf5dcaa8eb553f1ef9a03d5",
        "currency_from": "RUB",
        "currency_to": "TON",
        "amount": "1000"
    }