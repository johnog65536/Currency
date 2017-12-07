const crypto = require('crypto')
var fs = require('fs');
var Blockchain = require('./blockchain');

// Constructor
function Miner(blockchainName) {

  if(fs.existsSync(blockchainName+'.json')){
    var inputChain = fs.readFileSync(blockchainName + '.json', 'utf8');
    this.blockchain = new Blockchain(blockchainName, inputChain);
  }else{
    console.log("Blockchain does not exist, creating...");
    this.blockchain = new Blockchain(blockchainName);
    this.blockchain.saveChain();
  }
  console.log("Miner successfully loaded blockchain: " + blockchainName );
}


Miner.prototype.confirm = function () {
  // confirm pending into block into chain
  return "confirm"
};

Miner.prototype.addTransaction = function (transaction) {
  // add into pending
  return JSON.stringify(transaction)
}

Miner.prototype.getTransaction = function (state, id) {
  // add into pending
  return state + " " + id
}



// export the class
module.exports = Miner;
