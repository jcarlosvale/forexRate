package com.forex.domain;

/**
 * Represents a Conversion and Rate value
 */
public class ForexRate {
    private final String fromCcy;
    private final String toCcy;
    private final double rate;

    public ForexRate(String fromCcy, String toCcy, double rate) {
        this.fromCcy = fromCcy;
        this.toCcy = toCcy;
        this.rate = rate;
    }

    public String getFromCcy() {
        return fromCcy;
    }

    public String getToCcy() {
        return toCcy;
    }

    public double getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return "ForexRate{" +
                "fromCcy='" + fromCcy + '\'' +
                ", toCcy='" + toCcy + '\'' +
                ", rate=" + rate +
                '}';
    }

}
