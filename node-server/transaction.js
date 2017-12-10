const crypto = require('crypto')

// Constructor
function Transaction(amount, fromAddress, toAddress, comment) {
  this.amount = amount; // Amount being sent
  this.fee = 0;
  this.confirmations = 0;
  this.time = + new Date();
  this.comment = comment;
  this.details = [
      {
        address: fromAddress,
        category: "send",
        amount: -amount,
        fee: 0
      },
      {
        address: toAddress,
        category: "recieve",
        amount: amount
      }
    ];
  this.txId = this.generateTxHash();


}

Transaction.prototype.create = function (transaction) {
  this.amount = transaction.amount; // Amount being sent
  this.fee = transaction.fee;
  this.confirmations = transaction.confirmations;
  this.time = transaction.time;
  this.comment = transaction.comment;
  this.details = [
      {
        address: transaction.details[0].address,
        category: "send",
        amount: transaction.details[0].amount,
        fee: transaction.details[0].fee
      },
      {
        address: transaction.details[1].address,
        category: "recieve",
        amount: transaction.details[1].amount
      }
    ];
  this.txId = transaction.txId;
  //console.log("CREATE TX: " + this);
};

Transaction.prototype.get = function () {
  return this;
};

Transaction.prototype.confirm = function(blockHash, blockIndex, blockTime) {
  this.blockHash = blockHash;
  this.blockIndex = blockIndex;
  this.timerecieved = + new Date();
  this.blockTime = blockTime;


};

Transaction.prototype.generateTxHash = function () {
  var hash = crypto.createHash('sha256')
  hash.update(this.amount + this.fee + this.time + this.comment + JSON.stringify(this.details));
  return hash.digest('hex');;
};



// export the class
module.exports = Transaction;
