var express = require('express');
var router = express.Router();

/* GET users listing. */
router.get('/', function (req, res) {
  res.send("Users interface. Stuff like login.");
});

module.exports = router;
