// imports
var Blockchain = require('./blockchain');
var Miner = require('./miner');
var Transaction = require('./transaction'); // TEST PURPOSES PLEASE REMOVE

// Create blockchain and add base blocks to it
var bc = new Blockchain("tester");
// bc.addBlock("I am a transaction");
// bc.addBlock("I also am");
// bc.addBlock("Me too");

var hashList = bc.exportChain("hashlist");

for (var i = 0; i < hashList.length; i++) {
  console.log(hashList[i]);
}




// Create transaction
var tx = new Transaction(10, "todoFrom", "todoTo", "Test please ignore");
//tx.confirm(bc.blockList[1].hashString, bc.blockList[1].blockID, bc.blockList[1].timestamp);
var trans = [tx.get(), tx.get()];
bc.addBlock(tx.get());
bc.addBlock(trans);
console.log(tx);

// Save blockchain object to file
console.log(bc.exportFile());
bc.saveChain();

//bc.validateFull();

// // Create miner from local tester blockchain
// var mn = new Miner("tester");
// console.log(mn.blockchain);

// add block
