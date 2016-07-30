/*CodularHome • Writers • RSS Starting with Node and Web Sockets

Let us worry about building your integrations. Focus on your core product.
ads via Carbon
Introduction
Web Sockets are probably in use more around you now than you think, most things with real-time interactions are most probably running through these little gems. Web sockets are commonly used with a suitable polyfill used where they're not fully supported.

They're great for instant transfers of data from one machine to one or many other connected clients, used in things like instant chats, collaborative sketch environments and many more. Unfortunately support is still only in recent browsers so we can look to use a polyfill, or third party library to help - one of the best being socket.io.

Here we'll be covering the basics of using Web Sockets with node.js, and not using socket.io.

node.js
This is a great platform that is built on the same JavaScript engine that is used in Google Chrome, and is event driven making it ideal for the situation where you want to use Web Sockets. For this article, you'll need access to somewhere which has node installed and an IP address (or domain) you can access it on. We're going to be using a micro instance on Amazon EC2, as it's a cheap effective way to play around.

Getting Started
We're going to split this into two parts, one that is the server side, and one that is the client side. This is a very basic example, and will not include any security checking as that is well outside the scope of this for now. But you should be aware of checking the origin of requests, and ensuring they come from where you're expecting. Also, escaping HTML etc, if you're injecting returned strings straight into your DOM as HTML content.

Server Side (node)
There are a few steps that we need to follow to get the node side working, these are:

Create instance of the server and listen to a specific port
Create a web socket server
Listen for connections
Callback for connections
Create server and listen
For this, we need to require the http library, and then create a new server:

*/

var http = require('http');
var server = http.createServer(function(request, response) {});


//We're using an empty function within the createServer, as we're not actually serving anything through a HTTP request. Next, we need to tell this server to listen on a particular port, for fun we'll use the port 1234:

server.listen(1234, function() {
    console.log((new Date()) + ' Server is listening on port 1234');
});
/*The first parameter here is the port that we want to listen two, and the second is a function that is a callback method, we've just thrown a quick message output to let us know that it's connected.

We now have a server that's running and listening on port 1234, we now need to use this to create our WebSocket Server:

Create Web Socket Server
We now create the Web Socket Server on the back of the HTTP server that we established. Here we need to require the websocket library. If this isn't available you will need to use npm to install it. Onwards:
*/

var WebSocketServer = require('websocket').server;
wsServer = new WebSocketServer({
    httpServer: server
});

/*We now have a web socket server that is running, and available for us to start adding some event listeners. In this case, we want to add one for when a new request to join is made:

Listening for Connections
To do this, we use the .on method within the WebSocketServer object that we have created previously, listening for the event request. We then provide a callback where we will then put all of our code which will execute when someone joins to the socket server.
*/

wsServer.on('request', function(r){
    // Code here to run on connection
});
/*We can now move on to the bulk of the code that will fit within these connections:

Callback for connections
This is the code that will be placed within that function outlined above, this has a few purposes to fulfill:

Accept the connection
Store connected clients
Listen for incoming messages and broadcast messages to clients
Listen for a client disconnecting and remove from list of clients
Accept the connection

We must accept the connection before we can do anything with it, this gives us an object that represents that client that is connected. We're going to be using the echo-protocol for the connection, and using the accept method within the request object that is passed into the callback as a parameter (which we have identified as r):
*/


var connection = r.accept('echo-protocol', r.origin);


/*
Now, we can use this connection to send messages to the client, or add specific listeners for the client etc.

Store connected clients

Now, we need to create an object that will have the clients in as well as an incrementing number to identify each client. These must sit outside of this event listener:

*/

var count = 0;
var clients = {};


//Next, within the event listener, we need to store the id for this client, and cache their connection to the clients:

// Specific id for this client & increment count
var id = count++;
// Store the connection method so we can loop through & contact all clients
clients[id] = connection;
//Note: We can quickly throw in a quick logging message to show that we have a new client connected:

 console.log((new Date()) + ' Connection accepted [' + id + ']');

/*Listen for incoming messages and broadcast

Now, we can attach event listeners to the connection, meaning we can add one for when we get a message from the client to the server. Within this, we want to take the message that they have sent us, and simply send it out to every other client that is connected. Super, super simple:
*/

// Create event listener
connection.on('message', function(message) {

    // The string message that was sent to us
    var msgString = message.utf8Data;

    // Loop through all clients
    for(var i in clients){
        // Send a message to the client with the message
        clients[i].sendUTF(msgString);
    }

});

/*Realistically, you wouldn't want to just send a message around like this, you'd want to send something more refined to the other clients, this in most cases would be a JSON string which carries other meta-data, such as time and sender.

Listen for client disconnecting

This is as simple as listening for the close event, and then deleting the disconnecting client from the client storage object. We'll throw in a console message just for fun, and to keep track of things!*/

connection.on('close', function(reasonCode, description) {
    delete clients[id];
    console.log((new Date()) + ' Peer ' + connection.remoteAddress + ' disconnected.');
});


//We now have a very basic system setup now on the server side that should accept web socket connections and broadcast our message out to every connected client. Next, we need to work on the front end.

/*Client Side (HTML/JS)
We're going to be using standard JavaScript, no need for that jQuery nonsense here. Sure, you can make things slide in, and out more easily and make it all beautiful but ... we're learning, not beautifying!

Crack open your favourite editor, and lay out the key elements:

A div for the messages from the server to go in (give it an id of chatlog)
An input of type button that will send the message out.
An input of type text that will have the message entered (give it an id of message)
note: ids are dirty, but that's cool as we're learning, not making production ready code

JavaScript
Here is where we bring the whole thing to life. The logic is this:

Connect to the web socket server
When the button is pressed send out our message to the server
Listen to a response from the server, add it to the chatlog div that we created
Connect to web socket server

This can't be easier, we instantiate a new instance of the WebSocket object, and pass it two parameters:

Address of the web socket server, including the port. Note: we need to use the ws protocol here, not http
The protocol we're using for this transfer as we outlined in our node script we need echo-protocol
Let's go:

*/

var ws = new WebSocket('ws://some-address-here.com:1234', 'echo-protocol');

/*Now we have an active web socket connection, if you're doing this in increments and run this now with the node script running, you should see a new connection logged in the console on your server.

Send our message to the server

We're not going to be doing much fancy stuff here, we're going to add an event to the button - be dirty, do it inline, I dare you - that will use the send method of our web socket server to send our message:*/

function sendMessage(){
    var message = document.getElementById('message').value;
    ws.send(message);
}
/*Simple as that, nothing complicated, nothing hard. Now, there's no real point testing that, as we'll not get any response or anything at the moment. However, let's throw in an event listener for when the server sends us a message:*/

/*Listen for server response

Now, we just write an event listener, and take the message that's passed to use and append it to the div. We're going to be super dirty, and use innerHTML. But realistically you should use DOM manipulation, because it's faster.*/

ws.addEventListener("message", function(e) {
    // The data is simply the message that we're sending back
    var msg = e.data;

    // Append the message
    document.getElementById('chatlog').innerHTML += '<br>' + msg;
});


/*Now you should be able to throw everything together, and talk to yourself. If you want to try with more people simply open a new window and run the page in that.

When you disconnect you should see a console message stating that someone has disconnected, and the unique id of that user.*/

ws.send('Conclusion');

/*This was a super super simple introduction to some Node and Web Socket fun. Nothing too complex, but enough to get started on the basics for what could be used for real time gaming, conversations or anything that requires some data transfer in real time. Remember it's best to cache elements in JavaScript, and to optimise your code, but that's for you to do.*/

