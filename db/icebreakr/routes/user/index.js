var express = require('express');
var app = module.exports = express();

var mongoose = require('mongoose');
var User = require('../user.model.js');

app.get('/', function(req, res) {
	ensureAuthenticated(req, res, function(user){
		var id = user._id;
		User.find({_id: id},function(err, results){
			for (var i = 0; i < results.length; i++) {
				res.json(results[i]);
			}
		});
	});
});

app.get('/interests', function(req, res) {
	ensureAuthenticated(req, res, function(user){
		var id = user._id;
		User.find({_id: id},function(err, result){
			for (var i = 0; i < results.interests.length; i++) {
				res.json(results.interests[i]);
			}
		});
	});
});

app.get('/description', function(req, res){
	ensureAuthenticated(req, res, function(user){
        var id = user._id;
		User.find({_id: id},function(err, result){
			res.json(results.description);
		});
	})
})
//to do
app.post('/', function(req, res){
	var userID = req.session.user._id;
	//var listId = req.body.listId;
	var desc = req.body.description;
	console.log(listId);
	
	var query = User.where({_id:userID});
	query.findOne(function(err, user){
		if(err) console.log(err);
		if(user){
			
				
			}
			res.send(0);
		}
	})
	
})

function ensureAuthenticated(req, res, next) {
  if (req.session.user) { 
	  console.log("user:" + req.session.user._id);
	  next(req.session.user); 
  } else {
	res.send('Fail');
  }
}