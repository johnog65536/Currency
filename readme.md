# Currency
This is a basic example of a CryptoCoin for understanding of the tech only

## Overview

### node-server
Contains the NodeJS scripts for the server side blockchain transactions
* main.js - Currently the test script interacting with the others
* miner.js -  The server miner client which handles transactions and interacts with the blockchainName
* blockchain.js - Blockchain class allowing to be read and interacts
* block.js - Block class used by blockchain
* transaction.js - Transaction object which will be stored in a block and created/validated in the miner

To run main.js
```
node main.js
```

### Prerequisites
npm, Maven, JUnit

This is a basic example of a CryptoCoin created as an input for Capgemini Tech Challenge 2017.

The goal, over a number of challenges, is to develop a fully working, albeit pretty simple block chain instance, with a payments engine, wallet, miners, script engine and browser.
