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


Miner.prototype.check = function () {

};






// export the class
module.exports = Miner;
