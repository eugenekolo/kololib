var express = require('express');
var router = express.Router();
// TODO(eugenek): Probably want to include some JSON thing here at minimum

/* GET home page. */
router.get('/', function (req, res) {
  res.render('index', { 
    title: 'nodejs starter',
    author: 'Eugene Kolo',
    example: 'Hello world!',
    example_partial: 'Hello partial!',
    partials: {
      partial: 'partial',      
    }       
  });
});

module.exports = router;
