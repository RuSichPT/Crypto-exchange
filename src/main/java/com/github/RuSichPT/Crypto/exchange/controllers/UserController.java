package com.github.RuSichPT.Crypto.exchange.controllers;

import com.github.RuSichPT.Crypto.exchange.models.*;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.*;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.enums.CurrencyName;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.enums.TransactionName;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.enums.WalletName;
import com.github.RuSichPT.Crypto.exchange.services.CurrencyService;
import com.github.RuSichPT.Crypto.exchange.services.TransactionService;
import com.github.RuSichPT.Crypto.exchange.services.UserService;
import com.github.RuSichPT.Crypto.exchange.services.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/crypto")
@AllArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final WalletService walletService;
    @Autowired
    private final CurrencyService currencyService;
    @Autowired
    private final TransactionService transactionService;

    @PostMapping(path = "registration")
    public SecretKey registerUser(@RequestBody User user) {
        Wallet wallet = walletService.createWallet();
        User newUser = userService.createUser(user.getUserName(), user.getEmail(), wallet);
        return new SecretKey(newUser.getSecretKey());
    }

    @GetMapping(path = "wallet")
    public Wallet getWallet(String secretKey) {
        User user = userService.findUserBySecretKey(secretKey);
        return user.getWallet();
    }

    @PostMapping(path = "wallet/add")
    public WalletResponse addToWallet(@RequestBody FillUpRequest request) {
        User user = userService.findUserBySecretKey(request.getSecretKey());
        Wallet wallet = user.getWallet();
        WalletName name = request.getWalletName();

        walletService.fillUpWallet(wallet, name, request.getValue());
        transactionService.saveTransaction(TransactionName.ADD, user.getUserName());

        return new WalletResponse(name, wallet.getValue(name));
    }

    @PostMapping(path = "wallet/subtract")
    public WalletResponse subtractFromWallet(@RequestBody WithdrawRequest request) {
        User user = userService.findUserBySecretKey(request.getSecretKey());
        Wallet wallet = user.getWallet();
        WalletName name = request.getWalletName();

        walletService.withdrawWallet(wallet, name, request.getValue());
        transactionService.saveTransaction(TransactionName.SUBTRACT, user.getUserName());

        return new WalletResponse(name, wallet.getValue(name));
    }


    @PostMapping(path = "wallet/exchange")
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
}
