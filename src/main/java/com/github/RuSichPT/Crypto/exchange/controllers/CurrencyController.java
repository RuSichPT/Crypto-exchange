package com.github.RuSichPT.Crypto.exchange.controllers;

import com.github.RuSichPT.Crypto.exchange.models.*;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.*;
import com.github.RuSichPT.Crypto.exchange.services.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("crypto")
public class CurrencyController {

    private static final String SECRET_KEY = "secret_key";

    @Autowired
    private final UserService userService;
    @Autowired
    private final WalletService walletService;
    @Autowired
    private final CurrencyService currencyService;
    @Autowired
    private final AdminService adminService;
    @Autowired
    private final TransactionService transactionService;

    public CurrencyController(UserService userService, WalletService walletService, CurrencyService currencyService, AdminService adminService, TransactionService transactionService) {
        this.userService = userService;
        this.walletService = walletService;
        this.currencyService = currencyService;
        this.adminService = adminService;
        this.transactionService = transactionService;
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

            User user = optionalUser.get();
            Wallet wallet = user.getWallet();

            if (wallet.isEnoughMoney(currencyFrom, amountFrom)) {

                walletService.withdrawWallet(wallet, currencyFrom, amountFrom);
                Double amountTo = currencyService.exchangeCurrency(currencyFrom, currencyTo, amountFrom);
                walletService.fillUpWallet2(wallet, currencyTo, amountTo);
                transactionService.saveTransaction(TransactionName.EXCHANGE, user.getUserName());

                return new ExchangeResponse(currencyFrom, currencyTo, amountFrom, amountTo);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                // TODO: 26.02.2023  отправить недостаточно средств
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "currency/change")
    public String changeCurrency(@RequestBody String jsonString) {
        HashMap<String, String> responseMap = new HashMap<>();
        JSONObject jsonObject = new JSONObject(jsonString);
        String secretKey = (String) jsonObject.get(SECRET_KEY);

        if (adminService.isAdmin(secretKey)) {
            String baseCurrency = (String) jsonObject.get("base_currency");
            Set<String> keys = jsonObject.keySet();

            for (CurrencyName name :
                    CurrencyName.values()) {
                if (keys.contains(name.getName())) {
                    String currency = name.getName();
                    double value = jsonObject.getDouble(currency);
                    currencyService.changeCurrency(CurrencyName.valueOf(baseCurrency), name, value);
                    responseMap.put(currency, String.valueOf(value));
                }
            }

            return new JSONObject(responseMap).toString();
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(path = "currency/stat/sum")
    public String getSum(@RequestBody SumRequest request) {
        if (adminService.isAdmin(request.getSecretKey())) {
            CurrencyName currency = request.getCurrency();
            double sum = userService.getAllMoney(currency);

            HashMap<String, String> responseMap = new HashMap<>();
            responseMap.put(currency.getName(), String.valueOf(sum));

            return new JSONObject(responseMap).toString();
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(path = "currency/stat/transaction")
    public TransactionResponse getTransaction(@RequestBody TransactionRequest request) {
        if (adminService.isAdmin(request.getSecretKey())) {
            List<Transaction> transactions = transactionService.getTransactions(request.getDateFrom(), request.getDateTo());

            return new TransactionResponse(transactions.size());
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}
