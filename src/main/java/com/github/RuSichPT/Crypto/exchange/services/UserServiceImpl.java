package com.github.RuSichPT.Crypto.exchange.services;

import com.github.RuSichPT.Crypto.exchange.exception.CryptoException;
import com.github.RuSichPT.Crypto.exchange.exception.errors.ErrorName;
import com.github.RuSichPT.Crypto.exchange.repositories.UserRepository;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.User;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.Wallet;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.WalletName;
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
        if (!hasUser(userName, email)) {
            User user = new User(userName, email, wallet);
            userRepository.save(user);

            return user;
        } else {
            throw new CryptoException(ErrorName.REFUSAL);
        }
    }

    @Override
    public boolean hasUser(String username, String email) {
        Optional<User> optionalUser = userRepository.findByUserNameOrEmail(username, email);

        return optionalUser.isPresent();
    }

    @Override
    public User findUserBySecretKey(String secretKey) {
        Optional<User> optUser = userRepository.findBySecretKey(secretKey);
        if (optUser.isPresent()) {
            return optUser.get();
        } else {
            throw new CryptoException(ErrorName.NOT_VALID_SECRET_KEY);
        }
    }

    @Override
    public double getAllMoney(WalletName walletName) {
        List<User> users = userRepository.findAll();

        double sum = 0;
        for (User user :
                users) {
            sum += user.getWallet().getValue(walletName);
        }

        return sum;
    }
}
