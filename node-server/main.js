// imports
var Blockchain = require('./blockchain');
var Miner = require('./miner');
var Transaction = require('./transaction'); // TEST PURPOSES PLEASE REMOVE


var bc = new Blockchain("tester");
bc.addBlock("I am a transaction");
bc.addBlock("I also am");
bc.addBlock("Me too");

var hashList = bc.exportChain("hashlist");

for (var i = 0; i < hashList.length; i++) {
  console.log(hashList[i]);
}

console.log(bc.exportFile());
bc.saveChain();



var tx = new Transaction(10, "todoFrom", "todoTo", "Test please ignore");
tx.confirm(bc.blockList[1].hashString, bc.blockList[1].blockID, bc.blockList[1].timestamp);
console.log(tx);

var mn = new Miner("tester");

console.log(mn.blockchain);

// add block
