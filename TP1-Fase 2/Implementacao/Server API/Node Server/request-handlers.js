
let mysql = require('mysql2/promise');
let config = require('./config.js');

// export module functions
module.exports.login = login;
module.exports.register = register;
module.exports.forgotPassword = forgotPassword;
module.exports.changePassword = changePassword;
module.exports.submitQuestion = submitQuestion;
module.exports.getQuestions = getQuestions;

// import local modules
const jsonResponse = require("./response");

async function login(request, response){

  try{
    var json = request.body;

    console.log(request.body);

    if(/*!IsJsonString(json) ||*/ !json.hasOwnProperty('User') || !json.hasOwnProperty('Password')){
      jsonResponse.badRequest(response);
      return;
    }

    var username_email = json.User;
    var password = json.Password;

    var connection = await mysql.createConnection(config);

    var [user] = await connection.query('SELECT id FROM players WHERE username = ? or email = ?', [username_email, username_email]);

    if (user.length > 0){

      var [login] = await connection.query('SELECT COUNT(*) as user FROM players WHERE id = ? and password = ?', [user[0].id, password]);

      if (login[0].user > 0){
        console.log("New login with " + json.User);
        jsonResponse.ok(response);
        connection.end();
        return;
      }
    }
    console.log("Fail login with " + json.User);
    connection.end();
    jsonResponse.unauthorized(response);
  }
  catch(e){
    console.log(e);
    jsonResponse.internalError(response);
  }
}

async function register(request, response){

  try{
    var json = request.body;

    if(!IsJsonString(json) || !json.hasOwnProperty('Username') || !json.hasOwnProperty('Email') || !json.hasOwnProperty('Password')){
      response.sendStatus(400);
      return;
    }

    var username = json.Username;
    var email = json.Email;
    var password = json.Password;

    var connection = await mysql.createConnection(config);

    var [users] = await connection.query('SELECT COUNT(*) as count FROM players WHERE username = ? or email = ?', username, email);

    if (users.count == 0){

      var [register] = await connection.query('INSERT INTO player (email, username, password) VALUES (?,?,?)', email, username, password);

      response.sendStatus(200);
      return;
    }
    connection.end();
    response.sendStatus(401);
  }
  catch(e){
    console.log(e);
    response.sendStatus(500);
  }
}

async function forgotPassword(request, response){

}

async function changePassword(request, response){

}

async function submitQuestion(request, response){

}

async function getQuestions(request, response){

}

function IsJsonString(str) {
  try {
      JSON.parse(str);
  } catch (e) {
      return false;
  }
  return true;
}