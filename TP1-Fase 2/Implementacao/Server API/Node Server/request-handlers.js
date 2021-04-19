
let mysql = require('mysql2/promise');
let config = require('./config.js');

// export module functions
module.exports.login = login;
module.exports.register = register;
module.exports.forgotPassword = forgotPassword;
module.exports.changePassword = changePassword;
module.exports.submitQuestion = submitQuestion;
module.exports.getQuestions = getQuestions;


async function login(request, response){

  try{
    var body = request.body;

    if(!IsJsonString(request.body) || !json.hasOwnProperty('User') || !json.hasOwnProperty('Password')){
      response.sendStatus(400);
      return;
    }

    var username_email = body.User;
    var password = body.Password;

    var connection = await mysql.createConnection(config);

    var [user] = await connection.query('SELECT id as user FROM players WHERE username = ? or email = ?', username_email, username_email);

    if (user.id != undefined){

      var [login] = await connection.query('SELECT COUNT(*) as user FROM players WHERE id = ? and password = ?', user.id, password);

      if (login.user > 0){

        response.sendStatus(200);
        connection.end();
        return;
      }
    }
    connection.end();
    response.sendStatus(201);
  }
  catch{
    response.sendStatus(500);
  }
}

async function register(request, response){

  try{
    var body = request.body;

    if(!IsJsonString(request.body) || !json.hasOwnProperty('Username') || !json.hasOwnProperty('Email') || !json.hasOwnProperty('Password')){
      response.sendStatus(400);
      return;
    }

    var username = body.Username;
    var email = body.Email;
    var password = body.Password;

    var connection = await mysql.createConnection(config);

    var [users] = await connection.query('SELECT COUNT(*) as count FROM players WHERE username = ? or email = ?', username, email);

    if (users.count == 0){

      var [register] = await connection.query('INSERT INTO player (email, username, password) VALUES (?,?,?)', email, username, password);

      response.sendStatus(200);
      return;
    }
    connection.end();
    response.sendStatus(201);
  }
  catch{
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