package com.forex.service;

import com.forex.domain.Amount;
import com.forex.domain.ConversionPair;
import com.forex.domain.ForexRate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TradeService {

    public Amount trade(String filePath) throws IOException {
        List<String> linesFromFile = removeComments(Files.readAllLines(Paths.get(filePath)));
        int indexRead = 0;
        int ratesQty = Integer.parseInt(linesFromFile.get(0));
        indexRead++;
        List<ForexRate> listForexRates = retrieveForexRates(linesFromFile, 1, ratesQty);
        indexRead = indexRead + ratesQty;
        Amount amount = readAmountFrom(linesFromFile.get(indexRead));
        indexRead++;
        int tradesQty = Integer.parseInt(linesFromFile.get(indexRead));
        indexRead++;
        List<String> operations = retrieveTradingOperations(linesFromFile, indexRead, tradesQty);
        return processTrading(amount, operations, listForexRates);
    }

    private Amount processTrading(Amount amount, List<String> operations, List<ForexRate> listForexRates) {
        Map<ConversionPair, ForexRate> mapForexRates = listForexRates.stream().collect(Collectors
                .toMap(forexRate -> new ConversionPair(forexRate.getFromCcy(), forexRate.getToCcy()), Function.identity()));

        double rate = 1;
        String currentCurrency = amount.getCurrency();

        for(String operation: operations) {
            ForexRate forexRate = mapForexRates.get(new ConversionPair(currentCurrency, operation));
            if (null == forexRate) {
                throw new RuntimeException("Forex Rate doesn´t exist: " + currentCurrency +  " --> " + operation);
            }
            rate = rate * forexRate.getRate();
            currentCurrency = operation;
        }

        String lastOperation = operations.get(operations.size() - 1);
        double value = rate * amount.getAmount();
        return new Amount(lastOperation, value);
    }

    private List<String> retrieveTradingOperations(List<String> linesFromFile, int indexRead, int tradesQty) {
        List<String> operations = new ArrayList<>();
        int index = indexRead;
        while (tradesQty > 0) {
            operations.add(linesFromFile.get(index));
            index++;
            tradesQty--;
        }
        return operations;
    }

    private Amount readAmountFrom(String line) {
        String [] values = line.split(" ");
        String currency = values[0];
        double amount = Double.parseDouble(values[1]);
        return new Amount(currency, amount);
    }

    private List<ForexRate> retrieveForexRates(List<String> linesFromFile, int startIndex, int ratesQty) {
        List<ForexRate> listForexRates = new ArrayList<>();
        while (ratesQty > 0) {
            String[] values = linesFromFile.get(startIndex).split(" ");
            String fromCcy = values[0];
            String toCcy = values[1];
            double rate = Double.parseDouble(values[2]);
            listForexRates.add(new ForexRate(fromCcy, toCcy, rate));
            ratesQty--;
            startIndex++;
        }
        return listForexRates;
    }

    private List<String> removeComments(List<String> linesFromFile) {
        return linesFromFile.stream().filter(line -> !line.trim().startsWith("#")).collect(Collectors.toList());
    }

}
