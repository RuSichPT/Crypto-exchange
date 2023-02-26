package com.github.RuSichPT.Crypto.exchange.repositories.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.json.JSONObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import static com.github.RuSichPT.Crypto.exchange.repositories.entities.CurrencyName.*;

@Entity
@Table(name = "currencies")
@Data
public class Currency {

    @Id
    @JsonIgnore
    private String name;

    @Column
    @JsonProperty("BTC")
    private Double btc;

    @Column
    @JsonProperty("TON")
    private Double ton;

    @Column
    @JsonProperty("RUB")
    private Double rub;

    public Currency(String name, Double btc, Double ton, Double rub) {
        this.name = name;
        this.btc = btc;
        this.ton = ton;
        this.rub = rub;
    }

    public Currency() {
    }

    public JSONObject asJson() {
        JSONObject json = new JSONObject();

        for (CurrencyName currencyName :
                CurrencyName.values()) {
            String name = currencyName.getName();
            if (!name.equals(this.name)) {
                switch (name) {
                    case "BTC" -> json.put(name, btc);
                    case "TON" -> json.put(name, ton);
                    case "RUB" -> json.put(name, rub);
                }
            }
        }

        return json;
    }

    public Double getValue(CurrencyName name) {
        if (name.equals(BTC)) {
            return getBtc();
        } else if (name.equals(TON)) {
            return getTon();
        } else if (name.equals(RUB)) {
            return getRub();
        }

        throw new RuntimeException("Валюта с таким именем" + name.getName() + " отсутствует");
    }
}
