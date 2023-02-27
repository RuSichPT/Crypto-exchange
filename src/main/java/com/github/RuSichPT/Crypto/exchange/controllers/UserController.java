package com.github.RuSichPT.Crypto.exchange.controllers;

import com.github.RuSichPT.Crypto.exchange.models.SecretKey;
import com.github.RuSichPT.Crypto.exchange.models.WithdrawRequest;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.TransactionName;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.User;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.Wallet;
import com.github.RuSichPT.Crypto.exchange.services.TransactionService;
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
public class UserController {

    private static final String SECRET_KEY = "secret_key";
    private static final String REFUSAL = "Такой пользователь уже существует. Отказ в регистрации!";

    @Autowired
    private final UserService userService;
    @Autowired
    private final WalletService walletService;
    @Autowired
    private final TransactionService transactionService;

    public UserController(UserService userService, WalletService walletService, TransactionService transactionService) {
        this.userService = userService;
        this.walletService = walletService;
        this.transactionService = transactionService;
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
            User user = optionalUser.get();
            Wallet wallet = user.getWallet();

            HashMap<String, String> responseMap = walletService.fillUpWallet(jsonObject, wallet);
            walletService.saveWallet(wallet);
            transactionService.saveTransaction(TransactionName.ADD, user.getUserName());

            return new JSONObject(responseMap).toString();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "wallet/subtract")
    public String subtractFromWallet(@RequestBody WithdrawRequest request) {
        Optional<User> optionalUser = userService.findUserBySecretKey(request.getSecretKey());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Wallet wallet = user.getWallet();

            HashMap<String, String> responseMap = walletService.withdrawWallet(wallet, request.getCurrency(), request.getCount());
            transactionService.saveTransaction(TransactionName.SUBTRACT, user.getUserName());

            return new JSONObject(responseMap).toString();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
