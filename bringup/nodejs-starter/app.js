/**
*   Main entry point for nodejs backend.
*/

var express = require('express');
var path = require('path');
var favicon = require('static-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');

var routes = require('./routes/index');
var users = require('./routes/users');

/*************************************
* App set up
**************************************/ 
var app = express();
/* View stuff */
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'hjs');
//app.set('partials', {welcome: 'welcome'});

/* Middleware configuration - An Express application is essentially a series of middleware calls. */
/* http://expressjs.com/guide/using-middleware.html */
app.use(favicon());
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded());
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));
/* Route stuff */
app.use('/', routes);
app.use('/users', users);
/// catch 404 and forwarding to error handler
app.use(function (req, res, next) {
    var err = new Error('Not Found');
    err.status = 404;
    next(err);
});

/*************************************** 
* Environment handlers
****************************************/
/* Development environment will print stack traces on error */
if (app.get('env') === 'development') {
    app.use(function (err, req, res, next) {
        res.status(err.status || 500);
        res.render('error', {
            message: err.message,
            error: err
        });
    });
}

/* Defaults to production */
app.use(function (err, req, res, next) {
    res.status(err.status || 500);
    res.render('error', {
        message: err.message,
        error: {}
    });
});

module.exports = app; 
