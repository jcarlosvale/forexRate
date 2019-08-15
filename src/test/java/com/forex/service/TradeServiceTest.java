package com.forex.service;

import com.forex.domain.Amount;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static com.forex.UtilTest.getPathFromFile;
import static org.junit.Assert.*;

public class TradeServiceTest {

    private TradeService service = new TradeService();

    @Test
    public void testTradeWithoutFee() throws IOException {
        Amount expectedAmount = new Amount("HKD", 8736.00);
        String path = getPathFromFile("tradeFile.txt");
        Amount actualAmount = service.trade(path);
        assertEquals(expectedAmount, actualAmount);
    }

    @Test
    public void testTradeWithFee() throws IOException {
        Amount expectedAmount = new Amount("HKD", 7862.40);
        String path = getPathFromFile("tradeFile.txt");
        Amount actualAmount = service.trade(path, 0.1);
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
        String path = getPathFromFile("invalidTradeFile.txt");
        service.trade(path);
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidFeeHighValue() throws IOException {
        String path = getPathFromFile("tradeFile.txt");
        service.trade(path,1.01);
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidFeeLowValue() throws IOException {
        String path = getPathFromFile("tradeFile.txt");
        service.trade(path,-0.01);
    }
}