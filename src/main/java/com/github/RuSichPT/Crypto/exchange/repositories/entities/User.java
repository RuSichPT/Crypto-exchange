package com.github.RuSichPT.Crypto.exchange.repositories.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @Column(name = "username")
    @JsonProperty("username")
    private String userName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "secret_key")
    private String secretKey;
}
