function sendMessage(connection, message)
{
	if(connection.connected) 
    {
        connection.sendUTF(message);
    }
}


function consumeMessage(message)
{
	console.log('Received Message:');
	console.log(message);
}




var some_data = {
	key: 4,
	chords: [
		2, 3, 1, 6, 3
	]
};

var other_data = {
	key: 'A Minor',
	chords: [
		'A Minor',
		'C Major',
		'G Major'
	]
};


// var message_to_send = "AYY WHAT UP BRO??!!";
var message_to_send = JSON.stringify(some_data);

// var ip = '192.168.254.21';
var ip = 'localhost';
var port = 1337;

var WebSocketClient = require('websocket').client;
 
var client = new WebSocketClient();
 
client.on('connectFailed', function(error) 
{
    console.log('Connect Error: ' + error.toString());
});
 
client.on('connect', function(connection) 
{
    console.log('WebSocket Client Connected');
    connection.on('error', function(error) 
    {
        console.log("Connection Error: " + error.toString());
    });
    connection.on('close', function() 
    {
        console.log('echo-protocol Connection Closed');
    });


    connection.on('message', function(message) 
    {
        if (message.type === 'utf8') 
        {
            consumeMessage(message.utf8Data);
        }
    });

    sendMessage(connection, message_to_send);
});
 
client.connect('ws://'+ip+':'+port+'/', 'echo-protocol');




