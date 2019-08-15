package com.forex.service;

import com.forex.domain.Amount;
import com.forex.domain.ConversionPair;
import com.forex.domain.ForexRate;
import com.forex.domain.ForexFileInformation;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Main class to process the operations and conversions from Forex File
 */
public class TradeService {

    /**
     * Part 1 of Challenge: without fee to calculate
     * @param filePath file path
     * @return the Amount after calculation, applying the operations over the initial amount and using the Forex rates
     * @throws IOException in case of File read error operations
     */
    public Amount trade(String filePath) throws IOException {
        ForexFileInformation forexFileInformation = ForexFileInformation.parse(filePath);
        return processTrading(forexFileInformation, 0);
    }

    /**
     * Part 2 of Challenge: with fee to calculate
     * @param filePath file path
     * @param fee fee to be applied in the conversions
     * @return the Amount after calculation, applying the operations over the initial amount and using the Forex rates
     * @throws IOException in case of File read error operations
     */
    public Amount trade(String filePath, double fee) throws IOException {
        ForexFileInformation forexFileInformation = ForexFileInformation.parse(filePath);
        return processTrading(forexFileInformation, fee);
    }

    /**
     * Main calculation method.
     * First is created a Map of Conversion Pair and the Forex Rates to be used in locate the conversion to be applied
     * Second for each operation, it's located your conversion rate and updated the rate to be applied in the end
     * Third, using the accumulated rate, it's done the calculation over the initial amount and applied the fee
     * @param forexFileInformation the file informations
     * @param fee fee to be applied
     * @return the final amount after the calculations or operations
     */
    private Amount processTrading(ForexFileInformation forexFileInformation, double fee) {
        if (fee < 0 || fee > 1) {
            throw new RuntimeException("Forex fee invalid: " + fee);
        }

        Map<ConversionPair, ForexRate> mapForexRates = forexFileInformation.getForexRates().stream().collect(Collectors
                .toMap(ForexRate::getConversionPair, Function.identity()));

        double rate = 1;
        String lastOperation = forexFileInformation.getInitialAmount().getCurrency();

        for(String operation: forexFileInformation.getOperations()) {
            ForexRate forexRate = mapForexRates.get(new ConversionPair(lastOperation, operation));
            if (null == forexRate) {
                throw new RuntimeException("Forex Rate doesn't exist: " + lastOperation +  " --> " + operation);
            }
            rate = rate * forexRate.getRate();
            lastOperation = operation;
        }

        double value = rate * forexFileInformation.getInitialAmount().getAmountValue() * (1-fee);
        return new Amount(lastOperation, value);
    }
}
