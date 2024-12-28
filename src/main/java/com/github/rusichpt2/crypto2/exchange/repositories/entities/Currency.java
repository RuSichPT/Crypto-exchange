package com.github.rusichpt2.crypto2.exchange.repositories.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.rusichpt2.crypto2.exchange.exception.CryptoException;
import com.github.rusichpt2.crypto2.exchange.exception.errors.ErrorName;
import com.github.rusichpt2.crypto2.exchange.repositories.entities.enums.CurrencyName;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;

import static com.github.rusichpt2.crypto2.exchange.repositories.entities.enums.CurrencyName.*;

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

    public Map<CurrencyName, Double> asMap() {
        HashMap<CurrencyName, Double> map = new HashMap<>();

        for (CurrencyName name :
                CurrencyName.values()) {
            if (!name.getName().equals(this.name)) {
                switch (name) {
                    case BTC -> map.put(name, btc);
                    case TON -> map.put(name, ton);
                    case RUB -> map.put(name, rub);
                }
            }
        }
        return map;
    }

    public void setValue(CurrencyName name, double value) {
        if (name.equals(BTC)) {
            setBtc(value);
        } else if (name.equals(TON)) {
            setTon(value);
        } else if (name.equals(RUB)) {
            setRub(value);
        }
    }

    public Double getValue(CurrencyName name) throws CryptoException {
        if (name.equals(BTC)) {
            return getBtc();
        } else if (name.equals(TON)) {
            return getTon();
        } else if (name.equals(RUB)) {
            return getRub();
        }

        throw new CryptoException(ErrorName.NOT_FOUND_CURRENCY);
    }
}
