package com.github.RuSichPT.Crypto.exchange.repositories.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.RuSichPT.Crypto.exchange.exception.CryptoException;
import com.github.RuSichPT.Crypto.exchange.exception.errors.ErrorName;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.enums.WalletName;
import lombok.Data;

import javax.persistence.*;

import static com.github.RuSichPT.Crypto.exchange.repositories.entities.enums.WalletName.*;

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

    public void setValue(WalletName name, double value) {
        if (name.equals(BTC_WALLET)) {
            setBtc(value);
        } else if (name.equals(TON_WALLET)) {
            setTon(value);
        } else if (name.equals(RUB_WALLET)) {
            setRub(value);
        }
    }

    public Double getValue(WalletName name) throws CryptoException {
        if (name.equals(BTC_WALLET)) {
            return getBtc();
        } else if (name.equals(TON_WALLET)) {
            return getTon();
        } else if (name.equals(RUB_WALLET)) {
            return getRub();
        }

        throw new CryptoException(ErrorName.NOT_FOUND_WALLET);
    }

    public boolean isEnoughMoney(WalletName name, Double value) {
        double difference = getValue(name) - value;

        return difference >= 0;
    }
}
