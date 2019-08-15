package com.forex.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Represents an amount and currency
 */
public class Amount {
    private final String currency;
    private final BigDecimal amountValue;

    public Amount(String currency, double amountValue) {
        this.currency = currency;
        this.amountValue = BigDecimal.valueOf(amountValue).setScale(2, RoundingMode.HALF_UP);
    }

    public double getAmountValue() {
        return amountValue.doubleValue();
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "# Traded Amount\n" +
                currency + " " + amountValue.doubleValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amount amount1 = (Amount) o;
        return Objects.equals(currency, amount1.currency) &&
                Objects.equals(amountValue, amount1.amountValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amountValue);
    }
}
