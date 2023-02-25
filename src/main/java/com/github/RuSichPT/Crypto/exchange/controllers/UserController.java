package com.github.RuSichPT.Crypto.exchange.controllers;

import com.github.RuSichPT.Crypto.exchange.repositories.entities.User;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.Wallet;
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

    public UserController(UserService userService, WalletService walletService) {
        this.userService = userService;
        this.walletService = walletService;
    }

    @PostMapping(path = "registration")
    public String saveUser(@RequestBody User user) {
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
    public Wallet getWallet(@RequestBody String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        String secretKey = (String) jsonObject.get(SECRET_KEY);
        Optional<User> optionalUser = userService.findUserBySecretKey(secretKey);

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
    public String subtractFromWallet(@RequestBody String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        String secretKey = (String) jsonObject.get(SECRET_KEY);
        Optional<User> optionalUser = userService.findUserBySecretKey(secretKey);

        if (optionalUser.isPresent()) {
            Wallet wallet = optionalUser.get().getWallet();

            HashMap<String, String> responseMap = walletService.withdrawWallet(jsonObject, wallet);

            return new JSONObject(responseMap).toString();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
