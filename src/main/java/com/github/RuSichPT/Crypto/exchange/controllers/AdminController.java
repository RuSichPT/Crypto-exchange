package com.github.RuSichPT.Crypto.exchange.controllers;

import com.github.RuSichPT.Crypto.exchange.models.ChangeRequest;
import com.github.RuSichPT.Crypto.exchange.models.TransactionResponse;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.Currency;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.Transaction;
import com.github.RuSichPT.Crypto.exchange.repositories.entities.enums.CurrencyName;
import com.github.RuSichPT.Crypto.exchange.services.AdminService;
import com.github.RuSichPT.Crypto.exchange.services.CurrencyService;
import com.github.RuSichPT.Crypto.exchange.services.TransactionService;
import com.github.RuSichPT.Crypto.exchange.services.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("api/v1/crypto")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final CurrencyService currencyService;
    private final AdminService adminService;
    private final TransactionService transactionService;

    @GetMapping(path = "currency")

    public Map<CurrencyName, Double> getCurrency(
            @RequestParam String secretKey,
            @RequestParam CurrencyName currencyName) throws ResponseStatusException {

        if (adminService.isAdmin(secretKey) || userService.hasUser(secretKey)) {
            Currency currency = currencyService.findCurrency(currencyName);
            return currency.asMap();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "currency/change")
    public Map<CurrencyName, Double> changeCurrency(@RequestBody ChangeRequest request) {
        adminService.findAdminBySecretKey(request.getSecretKey());

        Set<CurrencyName> names = request.getCurrencies().keySet();
        for (CurrencyName name :
                names) {
            currencyService.changeCurrency(request.getBaseCurrency(), name, request.getCurrencies().get(name));
        }

        return request.getCurrencies();

    }

    @GetMapping(path = "currency/stat/sum")
    public Map<CurrencyName, Double> getSum(@RequestParam String secretKey, @RequestParam CurrencyName currencyName) {
        adminService.findAdminBySecretKey(secretKey);
        return userService.getAllMoney(currencyName);
    }

    @GetMapping(path = "currency/stat/transaction")
    public TransactionResponse getTransaction(
            @RequestParam String secretKey,
            @Parameter(description = "Pattern dd.MM.yyyy", schema = @Schema(implementation = String.class))
            @DateTimeFormat(pattern = "dd.MM.yyyy") java.util.Date dateFrom,
            @Parameter(description = "Pattern dd.MM.yyyy", schema = @Schema(implementation = String.class))
            @DateTimeFormat(pattern = "dd.MM.yyyy") java.util.Date dateTo) {

        adminService.findAdminBySecretKey(secretKey);
        List<Transaction> transactions = transactionService
                .getTransactions(new Date(dateFrom.getTime()), new Date(dateTo.getTime()));

        return new TransactionResponse(transactions.size());
    }
}
