# Crypto-exchange#
## Обзор
Тестовый проект **RESTfull API service** представляет собой биржу для проведения торгов криптовалютами.
Приложение реализовывает упрощенный функционал криптобиржи.  
В системе предполагается две роли: **пользователь** и **администратор**

### Пользователь имеет возможность
- зарегистрироваться
- пополнить кошелёк
- вывести деньги
- купить/продать крипту

### Администартор имеет возможность
- изменить курс крипты
- посмотреть статистику по всем кошелькам пользователей
- статистику по торгам за определенный промежуток времени

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

# API:
http://localhost:8080/swagger-ui.html

### Примеры:

### API пользователя:

##### 1. Регистрация нового пользователя:

    POST /crypto/registration  
    Body:  
    {  
        "username": "vasya_vezunchik",  
        "email": "vasyu_kolbasit@mail.ru"  
    }

##### 2. Просмотр баланса своего кошелька:

    GET /crypto/wallet
    Body:  
    {
        "secret_key": "1ce21b405cf5dcaa8eb553f1ef9a03d5"
    }

##### 3. Пополнение кошелька.

    POST /crypto/wallet/add
    Body:  
    {
        "secret_key": "1ce21b405cf5dcaa8eb553f1ef9a03d5",
        "wallet_name": "RUB_WALLET",
        "value": 1000
    }

##### 5. Вывод денег с биржи.

    POST /crypto/wallet/add
    Body:  
    {
        "secret_key": "1ce21b405cf5dcaa8eb553f1ef9a03d5",
        "wallet_name": "RUB_WALLET",
        "value": 1000,
        "wallet": "AsS5A2SASd2as3q5sd2asd53a1s5"
    }
или  

    POST /crypto/wallet/add
    Body:  
    {
        "secret_key": "1ce21b405cf5dcaa8eb553f1ef9a03d5",
        "wallet_name": "RUB_WALLET",
        "value": 1000,
        "credit_card": "1234 5678 9012 3456"
    }
##### 5. Просмотр актуальных курсов валют (доступен и администратору).

    GET /crypto/currency
    Body:
    {
        "secret_key": "1ce21b405cf5dcaa8eb553f1ef9a03d5",
        "currency": "BTC"
    }
##### 6. Обмен валют по установленному курсу.

    POST /crypto/exchange
    Body:
    {
        "secret_key": "1ce21b405cf5dcaa8eb553f1ef9a03d5",
        "currency_from": "RUB",
        "currency_to": "TON",
        "amount": "1000"
    }