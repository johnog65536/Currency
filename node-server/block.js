const crypto = require('crypto')


// Constructor
function Block(blockInstance, prevHash, tData) {
    if(blockInstance==undefined){
      return;
    }
    this.blockID = blockInstance;
    this.prevHash = prevHash;
    this.timestamp = + new Date();
    this.tData = tData;
    this.nonce = Math.random()*101|0;
    this.hashString = this.getCompleteBlockHash();
}

Block.prototype.getCompleteBlockHash = function () {
  this.hash = crypto.createHash('sha256')
  this.hash.update(this.prevHash + this.timestamp + this.nonce + this.tData);
  var hashStr = this.hash.digest('hex');
  return hashStr;
};

Block.prototype.getBlockHash = function () {
  return this.hashString;
};

Block.prototype.export = function () {
  return JSON.stringify(this);
}

// export the class
module.exports = Block;
