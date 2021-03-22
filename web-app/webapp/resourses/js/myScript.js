function sendToServer() {
    var account = {
        id: document.querySelector('#idAccount').value,
        name: document.querySelector('#name').value,
        surname: document.querySelector('#surname').value,
        age: document.querySelector('#age').value,
        address: document.querySelector('#address').value,
        email: document.querySelector('#email').value,
        password: document.querySelector('#password').value
    };
    if (confirm("r u sure?")) {
        var request = new XMLHttpRequest();
        var body = JSON.stringify(account);
        request.responseType = "json";
        request.open("POST", "/updateAcc", true);
        request.setRequestHeader('Content-Type', 'application/json');
        request.send(body);
    } else {
        return false;
    }
}

function uploadPicture() {
    /*phoneNum : [{
                number: document.querySelector('#phoneNumHome').value,
                type: "home"
            }, {
                number: document.querySelector('#phoneNumWork').value,
                type: "work"
            }
            ],*/
}