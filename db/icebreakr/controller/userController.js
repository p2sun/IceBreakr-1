var User = require('../models/user');

exports.postUsers = function(req,res){
   //var input = JSON.parse(req.body);
   var newUser = {
       _id:req.body.twitterID,
       estimote_id:req.body.estimoteID,
       name: req.body.name,
       description: req.body.description,
       interests: req.body.interests
   };

    console.log(newUser);
    User.create(newUser, function (err) {
        if (err)  {
            console.log ('Error on save!\n'+err);
            return res.status(500).send('Shit happened');
        } else {
            return res.status(201).send('Saved to db');
        }
    });
};

// Create endpoint /users for GET
exports.getUsers = function(req, res) {
  // Use the User model to find all user
  User.find({}, function(err, users) {
        var userMap = {};
        users.forEach(function(user) {
            userMap[user._id] = user;
        });

        res.send(userMap);
    });
};

// Create endpoint /api/users/:id for GET
exports.getUser = function(req, res) {
  // Use the User model to find a specific user
  User.find({_id:req.params.twitterID}, function(err, user) {
    if (err) res.send(err);
    res.json(user);
  });
};

// Create endpoint /api/users/:id for PUT
exports.putUser = function(req,res){
              User.find({_id:req.body.twitterID}, function(err, user){
    if(err) res.send(err);

    user.estimote_id = req.body.estimoteID;
    user.description = req.body.description;
    user.interests = req.body.interests;

    user.save(function(err){
        if(err) res.send(err);
        res.send(user);
    });
});
};

// Create endpoint /api/users/:id for DELETE
exports.deleteUser = function(req, res) {
  // Use the User model to find a specific user and remove it
  User.remove({_id:req.body.twitterID}, function(err) {
    if (err)
      res.send(err);

    res.json({ message: 'User is deleted!' });
  });
};
