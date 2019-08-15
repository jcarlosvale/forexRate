package com.forex.domain;

import com.sun.tools.javac.util.List;
import org.junit.Test;

import java.io.IOException;

import static com.forex.UtilTest.getPathFromFile;
import static org.junit.Assert.*;

public class ForexFileInformationTest {

    @Test
    public void testFileInformation() throws IOException {
        String path = getPathFromFile("tradeFile.txt");
        ForexFileInformation actualForexFileInformation = ForexFileInformation.parse(path);
        assertEquals(
                List.of(new ForexRate(new ConversionPair("EUR", "GBP"), 0.8),
                        new ForexRate(new ConversionPair("GBP", "EUR"), 1.2),
                        new ForexRate(new ConversionPair("GBP", "USD"), 1.4),
                        new ForexRate(new ConversionPair("USD", "HKD"), 7.8)
                ),actualForexFileInformation.getForexRates());
        assertEquals(new Amount("EUR", 1000.00), actualForexFileInformation.getInitialAmount());
        assertEquals(List.of("GBP", "USD", "HKD"), actualForexFileInformation.getOperations());
    }
}