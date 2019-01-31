package de.reroba.pricecalculator.shared;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Currency;

@Data
public class Price {

    public static final Currency EUR = Currency.getInstance("EUR");

    public static final Price ZERO_EUR = Price.of(BigDecimal.ZERO, EUR);

    private final BigDecimal amount;

    private final Currency currency;

    private Price(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public static Price of(BigDecimal amount, Currency currency) {
        return new Price(amount, currency);
    }

    public static Price of(double amount, Currency currency) {
        return new Price(BigDecimal.valueOf(amount), currency);
    }
}
