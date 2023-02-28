package com.github.RuSichPT.Crypto.exchange.controllers;

import com.github.RuSichPT.Crypto.exchange.models.*;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.*;
import com.github.RuSichPT.Crypto.exchange.services.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("crypto")
@AllArgsConstructor
public class CurrencyController {
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

    @GetMapping(path = "currency")
    public Map<CurrencyName, Double> getCurrency(@RequestBody CurrencyRequest request) {
        User user = userService.findUserBySecretKey(request.getSecretKey());
        Currency currency = currencyService.findCurrency(request.getCurrencyName());
        return currency.asMap();
    }

    @PostMapping(path = "currency/exchange")
    public ExchangeResponse exchangeCurrency(@RequestBody ExchangeRequest request) {
        User user = userService.findUserBySecretKey(request.getSecretKey());

        CurrencyName currencyFrom = request.getCurrencyFrom();
        CurrencyName currencyTo = request.getCurrencyTo();
        Double amountFrom = request.getAmountFrom();

        Wallet wallet = user.getWallet();

        walletService.withdrawWallet(wallet, WalletName.getWalletName(currencyFrom), amountFrom);
        Double amountTo = currencyService.exchangeCurrency(currencyFrom, currencyTo, amountFrom);
        walletService.fillUpWallet(wallet, WalletName.getWalletName(currencyTo), amountTo);
        transactionService.saveTransaction(TransactionName.EXCHANGE, user.getUserName());

        return new ExchangeResponse(currencyFrom, currencyTo, amountFrom, amountTo);
    }

    @PostMapping(path = "currency/change")
    public Map<CurrencyName, Double> changeCurrency(@RequestBody ChangeRequest request) {
        if (adminService.isAdmin(request.getSecretKey())) {
            Set<CurrencyName> names = request.getCurrencies().keySet();
            for (CurrencyName name :
                    names) {
                currencyService.changeCurrency(request.getBaseCurrency(), name, request.getCurrencies().get(name));
            }

            return request.getCurrencies();
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(path = "currency/stat/sum")
    public Map<CurrencyName, Double> getSum(@RequestBody SumRequest request) {
        if (adminService.isAdmin(request.getSecretKey())) {
            CurrencyName currency = request.getCurrency();
            double sum = userService.getAllMoney(WalletName.getWalletName(currency));

            HashMap<CurrencyName, Double> responseMap = new HashMap<>();
            responseMap.put(currency, sum);
            return responseMap;
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
