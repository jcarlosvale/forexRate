package com.forex;

import com.forex.service.TradeService;

import java.io.IOException;

public class TradeApp {

    public static void main( String[] args ) throws IOException {
        if (args.length != 1) {
            System.out.println("Missing parameters: <forex_file_path>");
            return;
        }

        String filePath = args[0];
        TradeService tradeService = new TradeService();
        System.out.println(tradeService.trade(filePath));
        System.out.println("Challenge Part 2 with 0.01 fee: ");
        System.out.println(tradeService.trade(filePath,0.01));
    }
}
