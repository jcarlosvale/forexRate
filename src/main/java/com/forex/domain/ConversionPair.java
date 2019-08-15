package com.forex.domain;

import java.util.Objects;

/**
 * Represents an Conversion Rate Representation from --> to
 */
public class ConversionPair {
    private final String fromCcy;
    private final String toCcy;

    public ConversionPair(String fromCcy, String toCcy) {
        this.fromCcy = fromCcy;
        this.toCcy = toCcy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConversionPair that = (ConversionPair) o;
        return Objects.equals(fromCcy, that.fromCcy) &&
                Objects.equals(toCcy, that.toCcy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromCcy, toCcy);
    }
}
