const crypto = require('crypto')


// Constructor
function Block(blockInstance, prevHash, tData) {
    this.blockID = blockInstance;
    this.prevHash = prevHash;
    this.timestamp = + new Date();
    this.tData = tData;
    this.nonce = Math.random()*101|0;

    this.hash = crypto.createHash('sha256')
    this.hash.update(this.getCompleteBlock());
    this.hashString = this.hash.digest('hex');

}

Block.prototype.getCompleteBlock = function () {
  return this.prevHash + this.timestamp + this.nonce + this.tData;
};

Block.prototype.getBlockHash = function () {
  return this.hashString;
};

Block.prototype.export = function () {
  var data = {
    block_id: this.blockID,
    nonce: this.nonce,
    prev_hash: this.prevHash,
    timestamp: this.timestamp,
    transaction: this.tData,
    hash: this.getBlockHash()
  }
  return data;
}

// export the class
module.exports = Block;
