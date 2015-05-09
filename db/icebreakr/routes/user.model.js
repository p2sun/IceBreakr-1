var mongoose = require('mongoose');
var Schema = mongoose.Schema;
var findOrCreate = require('mongoose-findorcreate');

var UserSchema = new Schema({
    //by default id twitter id is our id bitches
	_id: String,
    estimoteId: String,
	name: String,
	imageUrl: String,
	description: {type: String, default: 'Ready to Mingle!!!'},
	interests: [{ type:String }] 
});
UserSchema.plugin(findOrCreate);

module.exports = mongoose.model('User', UserSchema);