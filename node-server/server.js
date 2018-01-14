// server.js

var express    = require('express');
var app        = express();
var bodyParser = require('body-parser');

var Blockchain  = require('./blockchain');
var Transaction = require('./transaction');
var Miner       = require('./miner');

const miner = new Miner("test") //temp hardcoded name

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

var port = process.env.PORT || 8080;        // set our port

var router = express.Router();

router.get('/pending', function(req, res) {
    miner.getTransaction("pending")
    res.json({ transaction: miner.getTransaction("pending")});
});

router.get('/confirmed/:txId', function(req, res) {
  var confirmed = miner.getTransaction("confirmed", req.params.txId);
  //console.log(confirmed);
    res.status(200).json({ message: "found" });
});

router.route('/create')
  .post(function(req,res){
      var transaction = new Transaction(req.body.amount, req.body.fromAddress, req.body.toAddress, req.body.comment)
      console.log("Creating transaction: " + JSON.stringify(transaction));
      var id = miner.addTransaction(transaction)
      res.json({ message: 'Transaction created: ' + id });
})

router.route('/blockchain')
   .get(function(req, res) {
      res.json({ blockchain: miner.blockchain });
});

router.route('/block/:blockId')
    .get(function(req, res) {
    res.json({ blockchain: miner.blockchain.getBlock(req.params.blockId)});
});

router.route('/confirm-transactions')
  .post(function(req, res){
    res.json({ status: miner.confirm()});
});

// all of our routes will be prefixed with /api
app.use('/api', router);

// START THE SERVER
// =============================================================================
app.listen(port);
console.log('Application listening on port  ' + port);
