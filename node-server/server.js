// server.js

var express    = require('express');
var app        = express();
var bodyParser = require('body-parser');

var Blockchain = require('./blockchain');
var Transaction = require('./transaction');
var Miner = require('./miner');

const miner = new Miner("test") //temp hardcoded name

// configure app to use bodyParser()
// this will let us get the data from a POST
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

var port = process.env.PORT || 8080;        // set our port

var router = express.Router();

router.get('/pending', function(req, res) {
    miner.getTransaction("pending")
    res.json({ transaction: json.stringify(miner.getTransaction("pending"))});
});

router.get('/confirmed', function(req, res) {
    //TODO url id parameter
    miner.getTransaction("confirmed")
    res.json({ message: 'needs to be finished' });
});

router.route('/create')
    .post(function(req,res){
        var transaction = new Transaction(req.body.amount, req.body.fromAddress, req.body.toAddress, req.body.comment)
        miner.addTransaction(transaction)
        res.json({ message: 'Transaction created!' });
})

router.get('/blockchain', function(req, res) {
    //TODO url id parameter
    res.json({ blockchain: miner.blockchain });
});

router.get('/block', function(req, res) {
    //TODO url blockId parameter
    res.json({ blockchain: miner.blockchain.getBlock("blockId") });
});

router.get('/cofirm-transactions', function(req, res) {
    //TODO url blockId parameter
    res.json({ status: miner.confirm() });
});

// all of our routes will be prefixed with /api
app.use('/api', router);

// START THE SERVER
// =============================================================================
app.listen(port);
console.log('Magic happens on port ' + port);
