package com.forex.service;

import com.forex.domain.Amount;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class TradeServiceTest {

    private TradeService service = new TradeService();

    @Test
    public void testTrade() throws IOException {
        Amount expectedAmount = new Amount("HKD", 8736.00);
        String path = getPathFromFile("tradeFile.txt");
        Amount actualAmount = service.trade(path);
        assertEquals(expectedAmount, actualAmount);
    }

    @Test(expected = IOException.class)
    public void testInvalidPath() throws IOException {
        Amount expectedAmount = new Amount("HKD", 8736.00);
        String path = "some path";
        service.trade(path);
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidOperation() throws IOException {
        Amount expectedAmount = new Amount("BRL", 1.00);
        String path = getPathFromFile("invalidTradeFile.txt");
        service.trade(path);
    }

    private String getPathFromFile(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        return file.getPath();
    }
}