# Crypto-exchange

## Обзор

Тестовый проект **RESTfull API service** представляет собой биржу для проведения торгов криптовалютами. Приложение
реализовывает упрощенный функционал криптобиржи.  
В системе предполагается две роли: **пользователь** и **администратор**

### Пользователь имеет возможность

- зарегистрироваться
- пополнить кошелёк
- вывести деньги
- купить/продать крипту
- посмотреть актуальный курс валют

### Администартор имеет возможность

- изменить курс крипты
- посмотреть статистику по всем кошелькам пользователей
- статистику по торгам за определенный промежуток времени
- посмотреть актуальный курс валют

## Выполненные дополнительные задания

- подключение базы данных PostgresSQL
- подключение Swagger

## Использованные технологии

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

## Запуск
Запустить базу данных

    docker compose up -d

Заполнить в  application.properties следующие поля: 

    spring.datasource.username=${DATA_BASE_USERNAME}  
    spring.datasource.password=${DATA_BASE_PASSWORD}  

Или создать переменные среды DATA_BASE_USERNAME, DATA_BASE_PASSWORD

# Описание API предоставляется в формате Swagger (Open API):

http://localhost:8080/swagger-ui.html

## Примеры запросов:

Есть файл коллеции запросов для postman в корне. Его можно имопртировать  
Crypto-exchange.postman_collection.json

### API пользователя:

##### 1. Регистрация нового пользователя:

    POST api/v1/crypto/registration  
    Body:  
    {  
        "username": "vasya_vezunchik",  
        "email": "vasyu_kolbasit@mail.ru"  
    }

##### 2. Просмотр баланса своего кошелька:

    GET api/v1/crypto/wallet
    Parameters:
        secretKey = "1ce21b405cf5dcaa8eb553f1ef9a03d5"

##### 3. Пополнение кошелька.

    POST api/v1/crypto/wallet/add
    Body:  
    {
        "secret_key": "1ce21b405cf5dcaa8eb553f1ef9a03d5",
        "wallet_name": "RUB_WALLET",
        "value": 1000
    }

##### 4. Вывод денег с биржи.

    POST api/v1/crypto/wallet/add
    Body:  
    {
        "secret_key": "1ce21b405cf5dcaa8eb553f1ef9a03d5",
        "wallet_name": "RUB_WALLET",
        "value": 1000,
        "wallet": "AsS5A2SASd2as3q5sd2asd53a1s5"
    }

или

    POST api/v1/crypto/wallet/add
    Body:  
    {
        "secret_key": "1ce21b405cf5dcaa8eb553f1ef9a03d5",
        "wallet_name": "RUB_WALLET",
        "value": 1000,
        "credit_card": "1234 5678 9012 3456"
    }

##### 5. Просмотр актуальных курсов валют (доступен и администратору).

    GET api/v1/crypto/currency
    Parameters:
        secretKey = "1ce21b405cf5dcaa8eb553f1ef9a03d5"
        currencyName: "BTC:"

##### 6. Обмен валют по установленному курсу.

    POST api/v1/crypto/wallet/exchange
    Body:
    {
        "secret_key": "1ce21b405cf5dcaa8eb553f1ef9a03d5",
        "currency_from": "RUB",
        "currency_to": "TON",
        "amount": "2000"
    }

### API администратора:

##### 1. Изменить курс валют.

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

##### 2. Посмотреть общую сумму на всех пользовательских счетах для указанной валюты.

    GET api/v1/crypto/currency/stat/sum
    Parameters:
        secretKey: "29e60d5ed9e91b1490501aa197c90515",
        currencyName: "RUB"

##### 3. Посмотреть количество операций, которые были проведены за указанный период

    GET api/v1/crypto/currency/stat/transaction
    Parameters:
        secretKey: "29e60d5ed9e91b1490501aa197c90515",
        dateFrom: "26.02.2023",
        dateTo: "01.03.2023"

