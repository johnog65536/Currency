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
  console.log("Miner successfully loaded blockchain: " + this.blockchain);
}

Miner.prototype.check = function () {

};

Miner.prototype.confirm = function(blockHash, blockIndex, blockTime) {


};




// export the class
module.exports = Miner;
