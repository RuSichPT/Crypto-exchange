package com.github.RuSichPT.Crypto.exchange.repositories.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

import static com.github.RuSichPT.Crypto.exchange.repositories.entities.CurrencyName.*;

@Entity
@Data
@Table(name = "wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @Column
    @JsonProperty("BTC_wallet")
    private Double btc = 0.0;

    @Column
    @JsonProperty("TON_wallet")
    private Double ton = 0.0;

    @Column
    @JsonProperty("RUB_wallet")
    private Double rub = 0.0;

    public void setValue(CurrencyName name, double value) {
        if (name.equals(BTC)) {
            setBtc(value);
        } else if (name.equals(TON)) {
            setTon(value);
        } else if (name.equals(RUB)) {
            setRub(value);
        }
    }

    public Double getValue(CurrencyName name) {
        if (name.equals(BTC)) {
            return getBtc();
        } else if (name.equals(TON)) {
            return getTon();
        } else if (name.equals(RUB)) {
            return getRub();
        }

        throw new RuntimeException("Кошелек с таким именем" + name.getName() + " отсутствует");
    }

    public boolean isEnoughMoney(CurrencyName currencyName, Double money) {
        double difference = getValue(currencyName) - money;

        return difference >= 0;
    }
}
