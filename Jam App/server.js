(function()
{

// Our express application
var express = require('express'),
    path = require('path'),
    app     = express();
    // cors = require('cors');

app.set("port", process.env.PORT || 300);

// app.use(cors());

// Express middleware
app
    .use(express.static('./'))
    // Any request not matched so far, send to main.html
    .get('*', function(req, res){
        res.sendFile('index.html', {root: path.join(__dirname, './')});
    })
    .listen(app.get('port'), function(){
        console.log('Server is listening on port ' + app.get('port'));
    });



var current_id = 0;
var clients = {};


function newConnection(connection, id)
{
    clients[id] = connection;
}

function sendMessage(message, id)
{
    console.log('Sending new message');

    var current_size = Object.keys(clients).length; 
    console.log('Current number of clients: ' + current_size);

    for(var i in clients)
    {
        if (i != id)
        {
            console.log('WE GOT A NOT MATCH SONNYBOY!!!!');
            clients[i].sendUTF(message);
        }
        else
        {
            console.log('echo back');
            clients[i].sendUTF(message);
        }
    }
}

function logMessage(message, id)
{
    console.log(id + ': ' + message);    
}

function closeConnection(id)
{
    delete clients[id];
}








var http = require('http');
var server = http.createServer(function(request, response) {});
var port = 1337;

server.listen(port, function() 
{
    console.log((new Date()) + ' Server is listening on port 300, ws 1337');
});

var WebSocketServer = require('websocket').server;
wsServer = new WebSocketServer({
    httpServer: server
});




wsServer.on('request', function(r)
{

    var connection = r.accept('echo-protocol', r.origin);
    var id = current_id++;

    newConnection(connection, id);

    console.log((new Date()) + ' Connection accepted [' + id + ']');

    connection.on('message', function(msg)
    {
        var message = msg.utf8Data;
        sendMessage(message, id);
        logMessage(message, id);
    });

    connection.on('close', function(reasonCode, description)
    {
        console.log((new Date()) + ' Peer ' + connection.remoteAddress + ' disconnected.');
        closeConnection(id);
    });
});


})();
