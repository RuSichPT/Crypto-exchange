package com.github.RuSichPT.Crypto.exchange.controllers;

import com.github.RuSichPT.Crypto.exchange.controllers.json.ExchangeRequest;
import com.github.RuSichPT.Crypto.exchange.controllers.json.ExchangeResponse;
import com.github.RuSichPT.Crypto.exchange.controllers.json.SecretKey;
import com.github.RuSichPT.Crypto.exchange.controllers.json.WithdrawRequest;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.Currency;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.CurrencyName;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.User;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.Wallet;
import com.github.RuSichPT.Crypto.exchange.services.CurrencyService;
import com.github.RuSichPT.Crypto.exchange.services.UserService;
import com.github.RuSichPT.Crypto.exchange.services.WalletService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("crypto")
public class CryptoController {

    private static final String SECRET_KEY = "secret_key";
    private static final String REFUSAL = "Такой пользователь уже существует. Отказ в регистрации!";
    @Autowired
    private final UserService userService;
    @Autowired
    private final WalletService walletService;

    @Autowired
    private final CurrencyService currencyService;

    public CryptoController(UserService userService, WalletService walletService, CurrencyService currencyService) {
        this.userService = userService;
        this.walletService = walletService;
        this.currencyService = currencyService;
    }

    @PostMapping(path = "registration")
    public String registerUser(@RequestBody User user) {
        HashMap<String, String> responseMap = new HashMap<>();
        Optional<User> optionalUser = userService.findUserByNameOrEmail(user.getUserName(), user.getEmail());

        if (optionalUser.isEmpty()) {
            Wallet wallet = walletService.createWallet();
            User newUser = userService.createUser(user.getUserName(), user.getEmail(), wallet);

            responseMap.put(SECRET_KEY, newUser.getSecretKey());
        } else {
            responseMap.put("text", REFUSAL);
        }

        return new JSONObject(responseMap).toString();
    }

    @GetMapping(path = "wallet")
    public Wallet getWallet(@RequestBody SecretKey secretKey) {
        Optional<User> optionalUser = userService.findUserBySecretKey(secretKey.getValue());

        if (optionalUser.isPresent()) {
            return optionalUser.get().getWallet();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "wallet/add")
    public String addToWallet(@RequestBody String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        String secretKey = (String) jsonObject.get(SECRET_KEY);
        Optional<User> optionalUser = userService.findUserBySecretKey(secretKey);

        if (optionalUser.isPresent()) {
            Wallet wallet = optionalUser.get().getWallet();

            HashMap<String, String> responseMap = walletService.fillUpWallet(jsonObject, wallet);
            walletService.saveWallet(wallet);

            return new JSONObject(responseMap).toString();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "wallet/subtract")
    public String subtractFromWallet(@RequestBody WithdrawRequest request) {

        Optional<User> optionalUser = userService.findUserBySecretKey(request.getSecretKey());

        if (optionalUser.isPresent()) {
            Wallet wallet = optionalUser.get().getWallet();

            HashMap<String, String> responseMap = walletService.withdrawWallet(wallet, request.getCurrency(), request.getCount());

            return new JSONObject(responseMap).toString();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "currency")
    public String getCurrency(@RequestBody String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        String secretKey = (String) jsonObject.get(SECRET_KEY);
        Optional<User> optionalUser = userService.findUserBySecretKey(secretKey);

        if (optionalUser.isPresent()) {

            Optional<Currency> optCurrency = currencyService.findCurrency(jsonObject);

            if (optCurrency.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            Currency currency = optCurrency.get();

            return currency.asJson().toString();

        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "currency/exchange")
    public ExchangeResponse exchangeCurrency(@RequestBody ExchangeRequest request) {

        Optional<User> optionalUser = userService.findUserBySecretKey(request.getSecretKey());

        if (optionalUser.isPresent()) {
            CurrencyName currencyFrom = request.getCurrencyFrom();
            CurrencyName currencyTo = request.getCurrencyTo();
            Double amountFrom = request.getAmountFrom();

            Wallet wallet = optionalUser.get().getWallet();

            if (wallet.isEnoughMoney(currencyFrom, amountFrom)) {

                walletService.withdrawWallet(wallet, currencyFrom, amountFrom);
                Double amountTo = currencyService.exchangeCurrency(currencyFrom, currencyTo, amountFrom);
                walletService.fillUpWallet2(wallet, currencyTo, amountTo);

                return new ExchangeResponse(currencyFrom, currencyTo, amountFrom, amountTo);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                // TODO: 26.02.2023  отправить недостаточно средств
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
