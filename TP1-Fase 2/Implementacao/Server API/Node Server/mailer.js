
const nodemailer = require('nodemailer');
const SENDER_NAME = "Reclaim Portugal";
const SENDER_EMAIL = "reclaimportugal@gmail.com";

module.exports.sendRegisterConfirmationCode = sendRegisterConfirmationCode
module.exports.sendForgetPasswordCode = sendForgetPasswordCode

var transporter = nodemailer.createTransport({
  service: 'gmail',
  auth: {
    user: SENDER_EMAIL,
    pass: 'Reclaim$admin21'
  }
});


function sendRegisterConfirmationCode(email_address, confirmation_code){

    var subject = 'Confirm your account'

    var message = 'Welcome to Reclaim Portugal!\n\n'
    message += 'Your account has been sucessfully created and you can start playing right now. ';
    message += 'To confirm your email address please insert the following code:\n\n';
    message += '\t' + confirmation_code;

    sendEmail(email_address, subject, message)
}

function sendForgetPasswordCode(email_address, confirmation_code){

    var subject = 'Change password'

    var message = 'Change password request.\n\n'
    message += 'Please confirm your email address in order to change your password. ';
    message += 'To confirm your email please insert the following code:\n\n';
    message += '\t' + confirmation_code;

    sendEmail(email_address, subject, message)
}

function sendEmail(email_address, subject, message){

  var mailOptions = {
    from: getSender(),
    to: email_address,
    subject: subject,
    text: message
  };

  transporter.sendMail(mailOptions, function(error, info){
    if (error) {
      console.log(error);
    } else {
      console.log('Email sent: ' + info.response);
    }
  });
}

function getSender(){
  return SENDER_NAME + ' <' + SENDER_EMAIL + '>';
}

