console.log("Start the sample");

var http = require("http");

http.createServer(function (request, response) {
   response.writeHead(200, {'Content-Type': 'text/plain'});
   
   response.end('Bang-Bang\n');
}).listen(8081);


