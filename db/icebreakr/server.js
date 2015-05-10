//Load the required packages
var express = require('express');
var mongoose = require('mongoose');
var bodyParser = require('body-parser');

var userController = require('./controller/userController');
var settings = require('./env.json')[process.env.NODE_ENV || 'development'];
//Load our models
var User = require('./models/user');

//connect to the mongo db

mongoose.connect("mongodb://admin:admin@ds061288.mongolab.com:61288/icebreakermongo");

var app = express();
app.use(bodyParser.urlencoded({
    extended:true
}));

app.use(bodyParser.json());

// create our router
var router = express.Router();

// middleware to use for all requests
router.use(function(req, res, next) {
	// do logging
	console.log('Something is happening.');
	next();
});

// test route to make sure everything is working (accessed at GET http://localhost:8080/api)
router.get('/', function(req, res) {
	res.json({ message: 'hooray! welcome to our api!' });
});

//handlers for /users

router.route('/users/')
    .get(userController.getUsers)
    .post(userController.postUsers);

//handlers for /users/:id

router.route('/users/:id')
    .get(userController.getUser)
    .put(userController.putUser)
    .delete(userController.deleteUser);

//register all routers with /api



app.use('/',router);

//start server


app.listen(process.env.PORT || 5000 );
console.log('Listen bitch on port ' );

module.exports = app;

