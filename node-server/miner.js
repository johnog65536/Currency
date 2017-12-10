const crypto = require('crypto')
var fs = require('fs');
var Blockchain = require('./blockchain');

const pendingFilename = 'pending_transactions.json';


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
  var pendingTransactions = "";
  var confirmedTransactions = [];
  if(fs.existsSync(pendingFilename)){
    pendingTransactions = fs.readFileSync(pendingFilename, 'utf8');
  } else {
    return "No available pending transactions";
  }
  var transactions = JSON.parse(pendingTransactions);
  for (var i = 0; i < transactions.length; i++) {
    var transaction = new Transaction();
    transaction.create(transactions[i]);
    transaction.confirm();
    transactionsToConfirm.push(transaction);
  }
  return "Successfully confirmed " + confirmedTransactions.length + " details: " + JSON.stringify(confirmedTransactions);
};

Miner.prototype.addTransaction = function (transaction) {
  // add into pending
  //return JSON.stringify(transaction);
  var pendingTransactions = "{\"transactions\":[";
  if(fs.existsSync(pendingFilename)){
    pendingTransactions = fs.readFileSync(pendingFilename, 'utf8');
    //pendingTransactions = pendingTransactions.replace("{\"transactions\":[", "")
    pendingTransactions = pendingTransactions.replace("]}", "")
    pendingTransactions += ","
  }
  pendingTransactions += JSON.stringify(transaction) + "]}";
  fs.writeFile(pendingFilename, pendingTransactions,'utf8', function callback(err, data){
    if(err){
      console.log(err);
    }
  });
  return transaction.txId;
}

Miner.prototype.getTransaction = function (state, id) {
  // add into pending
  return state + " " + id
  // if state empty, get all
  // if id empty get all of state

}



// export the class
module.exports = Miner;
