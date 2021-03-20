function sendToServer() {
    var account = {
        id: document.querySelector('#idAccount').value,
        name: document.querySelector('#name').value,
        surname: document.querySelector('#surname').value,
        age: document.querySelector('#age').value,
        /*phoneNum : [{
            number: document.querySelector('#phoneNumHome').value,
            type: "home"
        }, {
            number: document.querySelector('#phoneNumWork').value,
            type: "work"
        }
        ],*/
        address: document.querySelector('#address').value,
        email: document.querySelector('#email').value,
        password: document.querySelector('#password').value
    };
    if (confirm("r u sure?")) {
        var request = new XMLHttpRequest();
        var body = JSON.stringify(account);
        request.open("POST", "/updateAcc", true);
        request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        request.send(body);
    } else {
        return false;
    }
}

function uploadPicture() {

}