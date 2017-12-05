// server.js

var express    = require('express');        
var app        = express();                 
var bodyParser = require('body-parser');

var Blockchain = require('./blockchain');
var Transaction = require('./transaction');

// configure app to use bodyParser()
// this will let us get the data from a POST
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

var port = process.env.PORT || 8080;        // set our port

var router = express.Router(); 

//(accessed at GET http://localhost:8080/api/blockain)
router.get('/blockchain', function(req, res) {
    res.json({ message: 'needs to be finished' });   
});

// more routes for our API will happen here
router.get('/transaction', function(req, res) {
    res.json({ message: 'needs to be finished' });   
});

router.route('/create')

    .post(function(req,res){
        var transaction = new Transaction()
        transaction.amount = req.body.amount;
        transaction.comment = req.body.comment;
        transaction.fromAddress = req.body.fromAddress;
        transaction.toAddress = req.body.toAddress;

        res.json({ message: 'Transaction created!' });
    })

// all of our routes will be prefixed with /api
app.use('/api', router);

// START THE SERVER
// =============================================================================
app.listen(port);
console.log('Magic happens on port ' + port);

