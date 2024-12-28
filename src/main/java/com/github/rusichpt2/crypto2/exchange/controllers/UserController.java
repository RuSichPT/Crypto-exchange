package com.github.rusichpt2.crypto2.exchange.controllers;

import com.github.rusichpt2.crypto2.exchange.models.*;
import com.github.rusichpt2.crypto2.exchange.repositories.entities.User;
import com.github.rusichpt2.crypto2.exchange.repositories.entities.Wallet;
import com.github.rusichpt2.crypto2.exchange.repositories.entities.enums.CurrencyName;
import com.github.rusichpt2.crypto2.exchange.repositories.entities.enums.TransactionName;
import com.github.rusichpt2.crypto2.exchange.repositories.entities.enums.WalletName;
import com.github.rusichpt2.crypto2.exchange.services.CurrencyService;
import com.github.rusichpt2.crypto2.exchange.services.TransactionService;
import com.github.rusichpt2.crypto2.exchange.services.UserService;
import com.github.rusichpt2.crypto2.exchange.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/crypto")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final WalletService walletService;
    private final CurrencyService currencyService;
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
