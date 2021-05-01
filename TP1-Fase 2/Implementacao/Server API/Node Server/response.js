
module.exports.ok = ok
module.exports.playerProfile = playerProfile
module.exports.unauthorized = unauthorized
module.exports.badRequest = badRequest
module.exports.internalError = internalError


function ok(response){

    var json = JSON.stringify({
        status: "ok"
    });

    sendResponse(json, response, 200);
}

function playerProfile(response, username, email){

    var json = JSON.stringify({
        status: "ok",
        username: username,
        email: email
    });

    sendResponse(json, response, 200);
}

function unauthorized(response){

    var json = JSON.stringify({
        status: "unauthorized"
    });

    sendResponse(json, response, 401);
}

function badRequest(response){

    var json = JSON.stringify({
        status: "bad request"
    });

    sendResponse(json, response, 400);
}

function internalError(response){

    var json = JSON.stringify({
        status: "internal server error"
    });

    sendResponse(json, response, 500);
}

function sendResponse(json, response, status){
    response.statusCode = status;
    response.type('json');
    response.send(json);
}
