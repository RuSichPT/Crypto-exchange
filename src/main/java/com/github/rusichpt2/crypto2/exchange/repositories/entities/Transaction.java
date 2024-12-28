package com.github.rusichpt2.crypto2.exchange.repositories.entities;

import lombok.Getter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "transactions")
@Getter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private Date date;

    @Column(name = "username")
    private String userName;

    public Transaction(String name, String username) {
        this.name = name;
        this.userName = username;
        long now = System.currentTimeMillis();
        this.date = new Date(now);
    }

    public Transaction() {
    }
}
