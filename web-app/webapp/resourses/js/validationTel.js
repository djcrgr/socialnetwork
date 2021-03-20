function validateTelWork(name) {
    "use strict";
    var r = /^(\+)?(\(\d{2,3}\)?\d|\d)(([ \-]?\d)|( ?\(\d{2,3}\) ?)){5,12}\d$/;
    if (name.match(r)) {
        document.getElementById("phoneNumWork").value = formatPhoneNumber(name);
    } else {
        alert("Not valid")
    }
}

function validateTelHome(name) {
    "use strict";
    var r = /^(\+)?(\(\d{2,3}\)?\d|\d)(([ \-]?\d)|( ?\(\d{2,3}\) ?)){5,12}\d$/;
    if (name.match(r)) {
        document.getElementById("phoneNumHome").value = formatPhoneNumber(name);
    } else {
        alert("Not valid")
    }
}

function formatPhoneNumber(phoneNumberString) {
    var cleaned = ('' + phoneNumberString).replace(/\D/g, '')
    var match = cleaned.match(/^(\d)(\d{3})(\d{3})(\d{2})(\d{2})$/)
    if (match) {
        return match[1] + '(' + match[2] + ')' + match[3] + '-' + match[4] + '-' + match[5]
    }
    return null
}