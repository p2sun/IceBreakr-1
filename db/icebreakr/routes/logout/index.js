var express = require('express');
var app = module.exports = express();

var mongoose = require('mongoose');
var User = require('../user.model.js');

app.get('/', function(req, res){
	req.session.user = null;
	//req.logout(); not using this middleware
	res.redirect('/');
});