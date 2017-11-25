//
var Block = require('./block');
var fs = require('fs');

// Constructor
function Blockchain(chainName, data) {
  this.name = chainName;
  this.blockList = [];
  if(data==undefined){
    console.log("Creating new blockchain: " + chainName);
    this.creation = + new Date();
    this.lastUpdated = this.creation;
    // Create genesis block
    this.blockList.push(new Block(0, 0, "In the beginning, there was the block"));
    // There will only every be one at this stage
    this.prevHash = this.blockList[0].getBlockHash();
    this.blockID = 0;
  } else {
    console.log("Loading blockchain from data");
    var inputObj = JSON.parse(data);
    this.creation = inputObj.creation_timestamp;
    this.lastUpdated = inputObj.last_updated;
    this.blockList = mapBlockData(inputObj.blocks);
  }
}

function mapBlockData(blocks){
  var blockArr = [];
  var blocksToMap = JSON.parse(JSON.stringify(blocks));
  for (var i = 0; i < blocksToMap.length; i++) {
    var block = new Block();
    var blockToMap = JSON.parse(blocksToMap[i]);
    block.blockID = blockToMap.blockID;
    block.prevHash = blockToMap.prevHash;
    block.timestamp = blockToMap.timestamp;
    block.tData = blockToMap.tData;
    block.nonce = blockToMap.nonce;
    block.hashString = blockToMap.hashString;
    blockArr.push(block);
  }
  return blockArr;
}


Blockchain.prototype.method = function () {
  var newBlock = new Block(0, "test");
  console.log(newBlock.getBlockHash());
};

Blockchain.prototype.addBlock = function (tData) {
  this.lastUpdated = + new Date();
  this.prevBlockID = this.blockList.length
  this.blockList.push(new Block(this.prevBlockID, this.prevHash, tData));
  this.prevHash = this.blockList[this.blockList.length - 1].getBlockHash();
}


Blockchain.prototype.exportChain = function(format) {
  var returnList = [];
  for (var i = 0; i < this.blockList.length; i++) {
    var curBlock = this.blockList[i];
    switch (format) {
      case "hashlist":
        // Return list of block hashes
        returnList.push(curBlock.getBlockHash());
        break;
      case "blocklist":
        // Return all block data
        return this.blockList;
        break;
      default:
        break;
    }
  }
  return returnList;
}

Blockchain.prototype.saveChain = function() {
  var json = JSON.stringify(this.exportFile());
  fs.writeFile(this.name + ".json", json,'utf8', function callback(err, data){
    if(err){
      console.log(err);
    }
  });
}

Blockchain.prototype.exportFile = function(blockList){
  var blockList = [];
  for (var i = 0; i < this.blockList.length; i++) {
    blockList.push(this.blockList[i].export());
  }
  var data = {
    name: this.name,
    creation_timestamp: this.creation,
    last_updated: this.lastUpdated,
    chain_length: this.blockList.length,
    blocks: blockList
  }
  return data;
}



// export the class
module.exports = Blockchain;
