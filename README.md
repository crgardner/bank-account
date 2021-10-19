# bank-account

bank-account is a Java 17 Spring Boot application used as an example of the Clean Architecture. The application allows you to:

* Open accounts
* Adjust accounts with deposits and withdrawals
* Prepare statements

It uses the h2 database in-memory database for convenience.

## Installation

1. Install JDK version 17. [SDKMAN!](https://sdkman.io/) makes it quite easy.
1. Ensure a recent version of Maven is installed. SDKMAN! can help with that too.
1. Clone this repo.
1. In the root directory of the clone, run the following:

```bash
$ mvn clean install
```

## Usage
* After a successful installation, in the target directory of the root, execute the following:

```bash
$ java -jar bank-account-<version>.jar
```

* Alternatively, start the application in your IDE by creating a run configuration for the com.crg.learn.account.application.start.Main Java file. There are no start up options.

After the application is started, navigate to the script directory and run the following scripts:

```bash

$ ./open_account.sh

$ ./deposit_amount.sh <account number from open_account.sh> 

$ ./withdraw_amount.sh <account number from open_account.sh> 

$ ./prepare_statement.sh <account number from open_account.sh>

```
Note these scripts hardcode the currency code "EUR," as the application currently supports only Euros.

