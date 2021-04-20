// need in case of self-signed certificate
process.env.NODE_TLS_REJECT_UNAUTHORIZED = '0';

// import external modules
const express = require("express");
const fs = require('fs');
const https = require('https');
const app = express();

app.use(express.json());

// service listening port
const port = 5656; // MUDAR

// import local modules
const requestHandlers = require("./request-handlers");

// ssl certificate
const options = {
    key: fs.readFileSync('./certs/self-signed.key'), // GERAR NOVOS
    cert: fs.readFileSync('./certs/self-signed.crt')
};

// requests
app.get("/questions", requestHandlers.getQuestions);
app.put("/login", requestHandlers.login);
app.put("/register", requestHandlers.register);
app.put("/forgot-password", requestHandlers.forgotPassword);
app.put("/change-password", requestHandlers.changePassword);
app.put("/submit-question", requestHandlers.submitQuestion);
// MUDAR PARA POSTS

// 404 not found
app.use(function (req, res, next) {
    res.status(404).json({error: 'not found'})
    console.log("404 connection - " + req.originalUrl);
})

// start server
https.createServer(options, app).listen(port, "0.0.0.0");
console.log("\nService listening on port " + port + "...\n")
