package com.forex.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Represents an amount and currency
 */
public class Amount {
    private final String currency;
    private final BigDecimal amount;

    public Amount(String currency, double amount) {
        this.currency = currency;
        this.amount = BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_UP);
    }

    public double getAmount() {
        return amount.doubleValue();
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "# Traded Amount\n" +
                currency + " " + amount.doubleValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amount amount1 = (Amount) o;
        return Objects.equals(currency, amount1.currency) &&
                Objects.equals(amount, amount1.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amount);
    }
}
