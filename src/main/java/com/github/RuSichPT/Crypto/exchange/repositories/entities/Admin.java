package com.github.RuSichPT.Crypto.exchange.repositories.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.util.DigestUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "admins")
@Getter
public class Admin {

    @Id
    @Column(name = "username")
    @JsonProperty("username")
    private String userName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "secret_key")
    private String secretKey;

    public Admin(String userName, String email) {
        this.userName = userName;
        this.email = email;
        generateSecretKey();
    }

    public Admin() {
    }

    private void generateSecretKey() {
        String temp = userName + email;
        secretKey = DigestUtils.md5DigestAsHex(temp.getBytes());
    }


}
