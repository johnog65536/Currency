# Currency
This is a basic example of a CryptoCoin for understanding of the tech only

## Overview

### Wallet Command line
Fetch the code from src, and build with 
```
mvn compile
```
Run tests
 
* Majority contained within TestHttpCallOnMiner, which integrates with the node miner
* Remaining elements assume the miner is local, implemented in Java

### Wallet GUI
[TODO] John Manley updating

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

#### API overview
Run the api SERVER
```
node server.js
```
All these calls if done locally are on: http://localhost:8080/api

```
GET /pending
```
Get all pending transactions
```
GET /confirmed
```
Get all confirmed transactions
```
POST /create
```
Create a transaction
```
GET /blockchain
```
Get the whole blockchain - for init
```
GET /block/:blockId
```
Get a single block using block id
```
POST /confirm-transactions
```
Signal the server to confirm transactions TEST PURPOSES ONLY

### Prerequisites
npm, Maven, JUnit

This is a basic example of a CryptoCoin created as an input for Capgemini Tech Challenge 2017.

The goal, over a number of challenges, is to develop a fully working, albeit pretty simple block chain instance, with a payments engine, wallet, miners, script engine and browser.
