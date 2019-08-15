package com.forex.domain;

import java.util.Objects;

/**
 * Represents a Conversion and Rate value
 */
public class ForexRate {
    private final ConversionPair conversionPair;
    private final double rate;

    ForexRate(ConversionPair conversionPair, double rate) {
        this.conversionPair = conversionPair;
        this.rate = rate;
    }

    public ConversionPair getConversionPair() {
        return conversionPair;
    }

    public double getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return "ForexRate{" +
                "conversionPair='" + conversionPair + '\'' +
                ", rate=" + rate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForexRate forexRate = (ForexRate) o;
        return Double.compare(forexRate.rate, rate) == 0 &&
                conversionPair.equals(forexRate.conversionPair);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conversionPair, rate);
    }
}
