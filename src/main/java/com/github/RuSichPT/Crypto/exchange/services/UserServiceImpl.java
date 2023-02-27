package com.github.RuSichPT.Crypto.exchange.services;

import com.github.RuSichPT.Crypto.exchange.repositories.UserRepository;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.CurrencyName;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.User;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(String userName, String email, Wallet wallet) {
        User user = new User(userName, email, wallet);
        userRepository.save(user);

        return user;
    }

    @Override
    public Optional<User> findUserByNameOrEmail(String username, String email) {
        return userRepository.findByUserNameOrEmail(username, email);
    }

    @Override
    public Optional<User> findUserBySecretKey(String secretKey) {
        return userRepository.findBySecretKey(secretKey);
    }

    @Override
    public double getAllMoney(CurrencyName currencyName) {
        List<User> users = userRepository.findAll();

        double sum = 0;
        for (User user :
                users) {
            sum += user.getWallet().getValue(currencyName);
        }

        return sum;
    }
}
