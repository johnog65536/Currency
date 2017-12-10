const crypto = require('crypto')
var fs = require('fs');
var Blockchain = require('./blockchain');
var Transaction = require('./transaction');

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
  console.log(transactions)
  var transactionsToConfirm = transactions['transactions'];
  console.log(transactionsToConfirm)
  console.log("len: " + transactionsToConfirm.length)
  for (var i = 0; i < transactionsToConfirm.length; i++) {
    var transaction = new Transaction("", "", "", "");
    transaction.create(transactionsToConfirm[i]);
    confirmedTransactions.push(transaction);
  }

  this.blockchain.addBlock(confirmedTransactions);
  this.blockchain.saveChain();
  fs.unlinkSync(pendingFilename);
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
  var confirmedTransactions = [];
  for (var i = 0; i < this.blockchain.blockList.length; i++) {
    var transactions = this.blockchain.blockList[i].tData;
    for (var i = 0; i < transactions.length; i++) {
      confirmedTransactions.push(transactions[i]);
      if(transactions[i].txId == id){
        return transactions[i];
      }
    }
  }
  return state + " " + id
  // if state empty, get all
  // if id empty get all of state

}



// export the class
module.exports = Miner;
