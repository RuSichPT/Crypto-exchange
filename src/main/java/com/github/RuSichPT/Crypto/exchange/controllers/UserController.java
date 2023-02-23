package com.github.RuSichPT.Crypto.exchange.controllers;

import com.github.RuSichPT.Crypto.exchange.repositories.entities.User;
import com.github.RuSichPT.Crypto.exchange.services.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("crypto")
public class UserController {

    private static final String REFUSAL = "Такой пользователь уже существует. Отказ в регистрации!";

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "registration")
    public String saveUser(@RequestBody User user) {

        HashMap<String, String> responseMap = new HashMap<>();

        Optional<User> optionalUser = userService.findUserByName(user.getUserName());

        if (optionalUser.isEmpty()) {
            String secretKey = generateSecretKey(user);
            user.setSecretKey(secretKey);
            userService.saveUser(user);
            responseMap.put("secret_key", secretKey);
        } else {
            responseMap.put("text", REFUSAL);
        }

        return new JSONObject(responseMap).toString();
    }

    private String generateSecretKey(User user) {
        String temp = user.getUserName() + user.getEmail();
        return DigestUtils.md5DigestAsHex(temp.getBytes());
    }

}
