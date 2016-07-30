var message_to_send = 'yo dawg';

var ip = '192.168.254.21';
var port = 1337;

var ws = new WebSocket('ws://'+ip+':'+port, 'echo-protocol');



ws.addEventListener("message", function(e) 
{
    var msg = e.data;
    consumeMessage(msg);
});

function consumeMessage(message)
{
	console.log('Received Message:');
	console.log(message);
}

function sendMessage(message)
{
    ws.send(message);
}

sendMessage(message_to_send);

