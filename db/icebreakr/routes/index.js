var express = require('express');
var app = module.exports = express();

var auth = require('./auth/');
app.use('/auth', auth);

var user = require('./user/');
app.use('/user', user);

//var users = require('./users/');
//app.use('/userlists', users);

var logout = require('./logout/');
app.use('/logout', logout);


/* GET list of api routes */
app.get('/', function(req, res) {
	ensureAuthenticated(req, res, function(){
		//res.redirect('/quests.html');
	});
});

function ensureAuthenticated(req, res, next) {
  if (req.session.user) { 
	  console.log("user:" + req.session.user.googleId);
	  next(); 
  } else {
	//res.redirect('/login.html');
  }
}

