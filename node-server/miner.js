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
  if(pendingTransactions == "" || pendingTransactions == undefined){
    return "Pending transactions file empty";
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
  console.log("Adding new transaction to file " + JSON.stringify(transaction));
  pendingTransactions += JSON.stringify(transaction) + "]}";
  console.log(pendingTransactions);
  fs.writeFile(pendingFilename, pendingTransactions,'utf8', function callback(err, data){
    if(err){
      console.log(err);
    }
  });
  return transaction.txId;
}

Miner.prototype.getTransaction = function (state, id) {
  // add into pending
  if(state == "confirmed"){
    if(id == undefined){
      return "Error, id not provided for confirmed get tx";
    }
    console.log("getting confirmed transactions");
    for (var i = 0; i < this.blockchain.blockList.length; i++) {
      var blockTData = this.blockchain.blockList[i].tData;
      if(this.blockchain.blockList[i].tData == "Genesis") {
        continue;
      }

      //console.log(blockTData);
      for (var i = 0; i < blockTData.length; i++) {
        var transaction = blockTData[i];
        console.log("Checking tx: " + transaction.txId + " against " + id);
        if(transaction.txId == id){
          return transaction;
        }
      }

    }
  } else if(state == "pending"){
    if(fs.existsSync(pendingFilename)){
      return fs.readFileSync(pendingFilename, 'utf8');
    }
  }

  return state + " " + id
  // if state empty, get all
  // if id empty get all of state

}



// export the class
module.exports = Miner;
