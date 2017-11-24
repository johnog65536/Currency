// imports
var Blockchain = require('./blockchain');

// create blockchain
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
// get blockchain

// add block
