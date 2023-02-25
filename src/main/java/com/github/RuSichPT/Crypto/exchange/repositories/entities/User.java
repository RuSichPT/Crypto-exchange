package com.github.RuSichPT.Crypto.exchange.repositories.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.util.DigestUtils;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
public class User {

    @Id
    @Column(name = "username")
    @JsonProperty("username")
    private String userName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "secret_key")
    private String secretKey;

    @OneToOne
    @JoinColumn(name = "wallet_id", referencedColumnName = "id")
    private Wallet wallet;

    public User(String userName, String email, Wallet wallet) {
        this.userName = userName;
        this.email = email;
        this.wallet = wallet;

        generateSecretKey();
    }

    public User() {
    }

    private void generateSecretKey() {
        String temp = userName + email;
        secretKey = DigestUtils.md5DigestAsHex(temp.getBytes());
    }
}
