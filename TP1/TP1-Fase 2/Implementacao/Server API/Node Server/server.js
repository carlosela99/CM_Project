// need in case of self-signed certificate
process.env.NODE_TLS_REJECT_UNAUTHORIZED = '0';

// import external modules
const express = require("express");
const fs = require('fs');
const https = require('https');
const app = express();

app.use(express.json());

// service listening port
const port = 5407;

// import local modules
const requestHandlers = require("./request-handlers");

// ssl certificate
const options = {
    key: fs.readFileSync('./certs/self-signed.key'),
    cert: fs.readFileSync('./certs/self-signed.crt')
};

// Authentication requests
app.post("/login", requestHandlers.login);
app.post("/register", requestHandlers.register);
app.post("/confirm-register", requestHandlers.confirmRegister);
app.post("/forget-password", requestHandlers.forgetPassword);
app.post("/forget-password-change", requestHandlers.forgetPasswordChange);

// Profile requests
app.post("/change-password", requestHandlers.changePassword);

// Game requests
app.get("/questions", requestHandlers.getQuestions);
app.post("/submit-question", requestHandlers.submitQuestion);

// 404 not found
app.use(function (req, res, next) {
    res.status(404).json({error: 'not found'})
    console.log("404 connection - " + req.originalUrl);
})

// start server
https.createServer(options, app).listen(port, "0.0.0.0");
console.log("\nService listening on port " + port + "...\n")
