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
  var confirmedTransactions = [];
  var transactions = getPendingTransactions();
  console.log(transactions);
  if(transactions && typeof transactions != "string"){
    var transactionsToConfirm = transactions['transactions'];
  } else {
    return "No transactions to confirm";
  }

  console.log(transactionsToConfirm)
  console.log("len: " + transactionsToConfirm.length)
  for (var i = 0; i < transactionsToConfirm.length; i++) {
    var transaction = new Transaction("", "", "", "");
    transaction.create(transactionsToConfirm[i]);
    transaction.confirmations += 1;
    confirmedTransactions.push(transaction);
  }

  this.blockchain.addBlock(confirmedTransactions);
  this.blockchain.saveChain();
  fs.unlinkSync(pendingFilename);
  return "Successfully confirmed " + confirmedTransactions.length + " details: " + JSON.stringify(confirmedTransactions);
};

Miner.prototype.addTransaction = function (transaction) {
  console.log("MINER : ADD : " + JSON.stringify(transaction));
  // add into pending
  //return JSON.stringify(transaction);
  // Check if transaction is not valid
  //var pendingTx = getPendingTransactions();
  var pending = [];
  //console.log(pendingTx);
  var curPending = getPendingTransactions();

  if(curPending && typeof curPending != "string"){
    curPending['transactions'].forEach(function(tx){
      var newTX = new Transaction();
      newTX.create(tx);
      pending.push(newTX);

    });
  }

  if(!this.blockchain.validateTx(transaction, pending)){
    console.log("Transaction is invalid: " + JSON.stringify(transaction))
    return "Transaction is invalid: either wallet does not exist or insufficient funds";
  }
  var pendingTransactions = "{\"transactions\":[";
  if(fs.existsSync(pendingFilename)){
    pendingTransactions = fs.readFileSync(pendingFilename, 'utf8');
    //pendingTransactions = pendingTransactions.replace("{\"transactions\":[", "")
    pendingTransactions = pendingTransactions.replace("]}", "")
    pendingTransactions += ","
  }

  console.log("Adding new transaction to file " + JSON.stringify(transaction));
  pendingTransactions += JSON.stringify(transaction) + "]}";
  //console.log(pendingTransactions);
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
      return "Error: id not provided for confirmed get tx";
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
        //console.log("Checking tx: " + transaction.txId + " against " + id);
        if(transaction.txId == id){
          //console.log("confirmed match");
          return JSON.stringify(transaction);
        }
      }


    }
    return "No transactions found";
  } else if(state == "pending"){
    if(fs.existsSync(pendingFilename)){
      var pendingTxs = fs.readFileSync(pendingFilename, 'utf8');
      //console.log(pendingTxs);
      return pendingTxs
    } else {
      return "There are pending transactions"
    }
  }

  //return state + " " + id
  // if state empty, get all
  // if id empty get all of state
};

Miner.prototype.getWalletBalance = function(walletName){
  console.log(this.blockchain.getWalletTotals());
}

function getPendingTransactions(){
  if(fs.existsSync(pendingFilename)){
    pendingTransactions = fs.readFileSync(pendingFilename, 'utf8');
  } else {
    return "No available pending transactions";
  }
  if(pendingTransactions == "" || pendingTransactions == undefined){
    return "Pending transactions file empty";
  }
  var transactions = JSON.parse(pendingTransactions);
  return transactions;
}



// export the class
module.exports = Miner;
