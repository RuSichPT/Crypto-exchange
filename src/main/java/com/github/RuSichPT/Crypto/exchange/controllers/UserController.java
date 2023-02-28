package com.github.RuSichPT.Crypto.exchange.controllers;

import com.github.RuSichPT.Crypto.exchange.models.FillUpRequest;
import com.github.RuSichPT.Crypto.exchange.models.SecretKey;
import com.github.RuSichPT.Crypto.exchange.models.WalletResponse;
import com.github.RuSichPT.Crypto.exchange.models.WithdrawRequest;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.TransactionName;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.User;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.Wallet;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.WalletName;
import com.github.RuSichPT.Crypto.exchange.services.TransactionService;
import com.github.RuSichPT.Crypto.exchange.services.UserService;
import com.github.RuSichPT.Crypto.exchange.services.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("crypto")
@AllArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final WalletService walletService;
    @Autowired
    private final TransactionService transactionService;

    @PostMapping(path = "registration")
    public SecretKey registerUser(@RequestBody User user) {
        Wallet wallet = walletService.createWallet();
        User newUser = userService.createUser(user.getUserName(), user.getEmail(), wallet);
        return new SecretKey(newUser.getSecretKey());
    }

    @GetMapping(path = "wallet")
    public Wallet getWallet(@RequestBody SecretKey secretKey) {
        User user = userService.findUserBySecretKey(secretKey.getValue());
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
}
