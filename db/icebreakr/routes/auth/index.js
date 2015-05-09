var express = require('express');
var app = module.exports = express();
var config = require('../../env.json')[process.env.NODE_ENV || 'development'];

var mongoose = require('mongoose');
var User = require('../user.model.js');

 var passport = require('passport'),
    TwitterStrategy = require('passport-twitter').Strategy;


app.use(passport.initialize());
app.use(passport.session()); 
	
app.get('/', function(req, res) {
	if(req.session.user ){
		res.send('valid');
	} else {
		res.send('invalid');
	}
});
passport.serializeUser(function(user, done) {
    done(null, user);
});

passport.deserializeUser(function(obj, done) {
    done(null, obj);
});

// Use the TwitterStrategy within Passport.
//   Strategies in Passport require a `verify` function, which accept
//   credentials and invoke a callback with a user object.
passport.use(new TwitterStrategy({
        consumerKey: config.TWITTER_CLIENT_KEY,
        consumerSecret: config.TWITTER_CLIENT_SECRET,
        callbackURL: config.TWITTER_CALLBACK_URL,
    },
    function(token, tokenSecret, profile, done) {
		User.findOrCreate(
		{_id: profile.id},
		{
			name: profile.displayName
		}, function(err, user, created) {
			console.log(user);
			console.log('user auth');
			console.log(profile.id);
			request.session.user = user;
			return done(err, user);
		});
    }
));


// Redirect the user to Twitter for authentication.  When complete, Twitter
// will redirect the user back to the application at
//   /auth/twitter/callback
app.get('/auth/twitter', passport.authenticate('twitter'));

// Twitter will redirect the user to this URL after approval.  Finish the
// authentication process by attempting to obtain an access token.  If
// access was granted, the user will be logged in.  Otherwise,
// authentication has failed.
app.get('/auth/twitter/callback', 
  passport.authenticate('twitter', { session: true }),
  function(req, res) {
    res.json({ id: req.user.id, username: req.user.username });
  });
