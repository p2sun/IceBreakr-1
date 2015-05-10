//load the mongoose package
var mongoose = require('mongoose');

//define our user schema
var UserSchema = new mongoose.Schema({
    _id: String,
    estimote_id: String,
    name: String,
    description: String,
    interests: [String]
});
    
    module.exports = mongoose.model('User', UserSchema);
    
    