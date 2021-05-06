
let mysql = require('mysql2/promise');
let config = require('./config.js');

// export module functions
module.exports.login = login;
module.exports.register = register;
module.exports.confirmRegister = confirmRegister;
module.exports.forgetPassword = forgetPassword;
module.exports.forgetPasswordChange = forgetPasswordChange;
module.exports.changePassword = changePassword;
module.exports.submitQuestion = submitQuestion;
module.exports.getQuestions = getQuestions;

// import local modules
const jsonResponse = require("./response");
const mailer = require("./mailer");

function generateCode(){
  return Math.floor(Math.random() * (99999 - 10000 + 1) ) + 10000;
}

async function login(request, response){

  try{
    var json = request.body;

    console.log(request.body);

    if(!json.hasOwnProperty('User') || !json.hasOwnProperty('Password')){
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
        var [profile] = await connection.query('SELECT username, email FROM players WHERE id = ?', [user[0].id]);
        console.log("New login with " + json.User);
        jsonResponse.playerProfile(response, profile[0].username, profile[0].email);
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

    if(!json.hasOwnProperty('Username') || !json.hasOwnProperty('Email') || !json.hasOwnProperty('Password')){
      jsonResponse.badRequest(response);
      return;
    }

    var username = json.Username;
    var email = json.Email;
    var password = json.Password;

    var connection = await mysql.createConnection(config);

    var [users] = await connection.query('SELECT COUNT(*) as count FROM players WHERE username = ? or email = ?', [username, email]);

    if (users[0].count == 0){

      var code = generateCode();
      await connection.query('INSERT INTO players (email, username, password, confirmation_code) VALUES (?,?,?,?)', [email, username, password, code]);
      mailer.sendRegisterConfirmationCode(email, code);

      console.log("New register with " + json.Email);
      jsonResponse.ok(response);
    }
    else{

      console.log("Fail register with " + json.Email);
      connection.end();
      jsonResponse.unauthorized(response);
    } 
  }
  catch(e){
    console.log(e);
    jsonResponse.internalError(response);
  }
}

async function confirmRegister(request, response){

  try{
    var json = request.body;

    if(!json.hasOwnProperty('Email') || !json.hasOwnProperty('Code')){
      jsonResponse.badRequest(response);
      return;
    }

    var email = json.Email;
    var code = json.Code;

    var connection = await mysql.createConnection(config);

    var [users] = await connection.query('SELECT COUNT(*) as count FROM players WHERE email = ? and confirmation_code = ?', [email, code]);
    console.log(users[0].count);
    if (users[0].count == 1){

      await connection.query('UPDATE players SET confirmed = 1 WHERE email = ? and confirmation_code = ?', [email, code]);

      console.log("Email confirmed with " + json.Email);
      jsonResponse.ok(response);
    }
    else{

      console.log("Fail confirmation with " + json.Email);
      connection.end();
      jsonResponse.unauthorized(response);
    } 
  }
  catch(e){
    console.log(e);
    jsonResponse.internalError(response);
  }
}

async function forgetPassword(request, response){

  try{
    var json = request.body;

    if(!json.hasOwnProperty('Email')){
      jsonResponse.badRequest(response);
      return;
    }

    var email = json.Email;

    var connection = await mysql.createConnection(config);

    var [users] = await connection.query('SELECT COUNT(*) as count FROM players WHERE email = ?', [email]);

    if (users[0].count == 1){

      var code = generateCode();
      await connection.query('UPDATE players SET confirmation_code = ? WHERE email = ?', [code, email]);
      mailer.sendForgetPasswordCode(email, code);

      jsonResponse.ok(response);
    }
    else{

      connection.end();
      jsonResponse.unauthorized(response);
    } 
  }
  catch(e){
    console.log(e);
    jsonResponse.internalError(response);
  }
}

async function forgetPasswordChange(request, response){
  try{
    var json = request.body;

    if(!json.hasOwnProperty('Email') || !json.hasOwnProperty('Code') || !json.hasOwnProperty('Password')){
      jsonResponse.badRequest(response);
      return;
    }

    var email = json.Email;
    var code = json.Code;
    var password = json.Password;

    var connection = await mysql.createConnection(config);

    var [users] = await connection.query('SELECT COUNT(*) as count FROM players WHERE email = ? and confirmation_code = ?', [email, code]);

    if (users[0].count == 1){

      await connection.query('UPDATE players SET password = ? WHERE email = ?', [password, email]);
      jsonResponse.ok(response);
    }
    else{

      connection.end();
      jsonResponse.unauthorized(response);
    } 
  }
  catch(e){
    console.log(e);
    jsonResponse.internalError(response);
  }
}

async function changePassword(request, response){
  try{
    var json = request.body;

    if(!json.hasOwnProperty('Email') || !json.hasOwnProperty('OldPassword') || !json.hasOwnProperty('NewPassword')){
      jsonResponse.badRequest(response);
      return;
    }

    var email = json.Email;
    var old_pass = json.OldPassword;
    var new_pass = json.NewPassword;

    var connection = await mysql.createConnection(config);

    var [users] = await connection.query('SELECT COUNT(*) as count FROM players WHERE email = ? and password = ?', [email, old_pass]);

    if (users[0].count == 1){

      await connection.query('UPDATE players SET password = ? WHERE email = ?', [new_pass, email]);
      jsonResponse.ok(response);
    }
    else{

      connection.end();
      jsonResponse.unauthorized(response);
    } 
  }
  catch(e){
    console.log(e);
    jsonResponse.internalError(response);
  }
}

async function submitQuestion(request, response){

  try{
    var json = request.body;

    if(!json.hasOwnProperty('Email') 
      || !json.hasOwnProperty('Question')
      || !json.hasOwnProperty('RightAnswer')
      || !json.hasOwnProperty('WrongAnswer')
      || !json.hasOwnProperty('RegionA')
      || !json.hasOwnProperty('RegionB')){

      jsonResponse.badRequest(response);
      return;
    }

    var email = json.Email;
    var question = json.Question;
    var right_answer = json.RightAnswer;
    var wrong_answer = json.WrongAnswer;
    var region_a = json.RegionA;
    var region_b = json.RegionB;

    var connection = await mysql.createConnection(config);

    // verify if user exists
    var [users] = await connection.query('SELECT COUNT(*) as count FROM players WHERE email = ?', [email]);

    if (users[0].count == 1){

      var [user] = await connection.query('SELECT id FROM players WHERE email = ?', [email]);
      var id = user[0].id;
      await connection.query('INSERT INTO players (creator_id, question, right_answer, wrong_answer, region_a, region_b)', [id, question, right_answer, wrong_answer, region_a, region_b]);
      jsonResponse.ok(response);
    }
    else{

      connection.end();
      jsonResponse.unauthorized(response);
    } 
  }
  catch(e){
    console.log(e);
    jsonResponse.internalError(response);
  }
}

async function getQuestions(request, response){

}
