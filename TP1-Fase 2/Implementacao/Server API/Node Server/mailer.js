
const nodemailer = require('nodemailer');
const SENDER_EMAIL = "reclaimportugal@gmail.com";

module.exports.sendRegisterConfirmationCode = sendRegisterConfirmationCode

var transporter = nodemailer.createTransport({
  service: 'gmail',
  auth: {
    user: SENDER_EMAIL,
    pass: 'Reclaim$admin21'
  }
});


function sendRegisterConfirmationCode(email_address, confirmation_code){

    var message = 'Welcome to Reclaim Portugal!\n\n'
    message += 'Your account was created and you can start playing right now. ';
    message += 'To confirm your email address please insert the following code:\n\n';
    message += '\t' + confirmation_code;

    var mailOptions = {
        from: SENDER_EMAIL,
        to: email_address,
        subject: 'Reclaim Portugal Registration',
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

