package com.forex.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main class to parse a file and fill the properties: initial amount, list of Forex Rates and operations to execute.
 */
public class ForexFileInformation {

    private final Amount initialAmount;
    private final List<ForexRate> listForexRates;
    private final List<String> operations;

    private ForexFileInformation(Amount initialAmount, List<String> operations, List<ForexRate> listForexRates) {
        this.initialAmount = initialAmount;
        this.operations = operations;
        this.listForexRates = listForexRates;
    }

    /**
     * Method to read the file, generate a list of lines, and parse in groups: Forex Rates section, the amount and the
     * list os Operations.
     * @param filePath file path
     * @return the object representing the values present in the file
     * @throws IOException in case of error reading the file
     */
    public static ForexFileInformation parse(String filePath) throws IOException {
        List<String> linesFromFile = Files.readAllLines(Paths.get(filePath));
        linesFromFile = removeComments(linesFromFile);
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
        return new ForexFileInformation(amount, operations, listForexRates);
    }

    /**
     * Remove the comments of file, Strings starting with #
     * @param linesFromFile the lines from file
     * @return list of lines without comments.
     */
    private static List<String> removeComments(List<String> linesFromFile) {
        return linesFromFile.stream().filter(line -> !line.trim().startsWith("#")).collect(Collectors.toList());
    }

    /**
     * Retrieve a list of forex rates starting in the startLine and following the rates qty. lines.
     * @param linesFromFile lines from file
     * @param startLine initial index
     * @param qtyOfLines quantity of rates
     * @return list of rates
     */
    private static List<ForexRate> retrieveForexRates(List<String> linesFromFile, int startLine, int qtyOfLines) {
        List<ForexRate> listForexRates = new ArrayList<>();
        while (qtyOfLines > 0) {
            String[] values = linesFromFile.get(startLine).split(" ");
            String fromCcy = values[0];
            String toCcy = values[1];
            double rate = Double.parseDouble(values[2]);
            listForexRates.add(new ForexRate(new ConversionPair(fromCcy, toCcy), rate));
            qtyOfLines--;
            startLine++;
        }
        return listForexRates;
    }

    /**
     * read the initial amount from file.
     * @param line line containing the amount
     * @return a Amount object having the currency and value.
     */
    private static Amount readAmountFrom(String line) {
        String [] values = line.split(" ");
        String currency = values[0];
        double amount = Double.parseDouble(values[1]);
        return new Amount(currency, amount);
    }

    /**
     * Retrieve a list of operations from file, starting in the startLine and following the qty. of lines.
     * @param linesFromFile lines from file
     * @param startLine start line
     * @param qtyOfLines quantity of lines to read
     * @return list of operations in String format
     */
    private static List<String> retrieveTradingOperations(List<String> linesFromFile, int startLine, int qtyOfLines) {
        List<String> operations = new ArrayList<>();
        int index = startLine;
        while (qtyOfLines > 0) {
            operations.add(linesFromFile.get(index));
            index++;
            qtyOfLines--;
        }
        return operations;
    }

    public List<ForexRate> getForexRates() {
        return this.listForexRates;
    }

    public Amount getInitialAmount() {
        return this.initialAmount;
    }

    public List<String> getOperations() {
        return this.operations;
    }
}
