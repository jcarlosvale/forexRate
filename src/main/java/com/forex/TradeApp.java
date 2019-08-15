package com.forex;

import com.forex.service.TradeService;

import java.io.IOException;

public class TradeApp {

    public static void main( String[] args ) throws IOException {
        String filePath = args[0];
        TradeService tradeService = new TradeService();
        System.out.println(tradeService.trade(filePath));
        System.out.println(tradeService.trade(filePath,0.01));
    }
}
