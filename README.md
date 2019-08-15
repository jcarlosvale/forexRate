# Forex Transactions
by João Carlos (https://www.linkedin.com/in/joaocarlosvale/)

Forex transactions involve exchanging money from one currency to another based on a set of rates provided by 
forex exchanges.
This project consists of an application to find the final amount after parse a file containing forex rates, 
an initial monetary amount to exchange, and a list of forex transactions to execute.

## Technologies used:
* Java
* Maven 

## Example:

File: 

  Some examples in the _resources_ folder.
  
Run:

    java -jar target/tradeApp-0.0.1.jar tradeFile.txt

Input file:

    # Rates
    4
    EUR GBP 0.80
    GBP EUR 1.20
    GBP USD 1.40
    USD HKD 7.80
    # Initial Amount
    EUR 1000
    # TRADES
    3
    GBP
    USD
    HKD

Output:

    # Traded Amount
    HKD 8736.00

## Commands:

To generate JAR:

    mvn clean package

To run:

    java -jar target/tradeApp-0.0.1.jar [file_path]
    
To run tests:

    mvn test
