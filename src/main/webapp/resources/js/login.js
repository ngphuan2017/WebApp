$(document).ready(function () {
    $('.eye').click(function () {
        $(this).toggleClass('open');
        $(this).children('i').toggleClass('fa-eye-slash fa-eye');
        if ($(this).hasClass('open')) {
            $(this).prev().attr('type', 'text');
        } else {
            $(this).prev().attr('type', 'password');
        }
    });
});
// Kiểm tra xem người dùng đã chọn "Ghi nhớ đăng nhập" hay không
if (localStorage.getItem("rememberLogin") === "true") {
// Đổ dữ liệu đã lưu vào trường input username và password
    document.getElementById("username").value = localStorage.getItem("savedUsername");
    document.getElementById("pwd").value = localStorage.getItem("savedPassword");
}

function hideErrorMessage() {
    var errorMessageDiv = document.getElementById("error-message");
    errorMessageDiv.textContent = '';
}

function forgotPassword() {
    document.getElementById("form-login").style.display = 'none';
    document.getElementById("forgot-password").style.display = 'block';
}

function cancelForgotPassword() {
    document.getElementById("forgot-password").style.display = 'none';
    document.getElementById("form-login").style.display = 'block';
}
//////////////////////////reCaptcha//////////
var flagCaptcha = 0;

function enableSubmitButton() {
    flagCaptcha = 1;
    if (flagCaptcha === 1 && flagEmail === 1) {
        var submitButton = document.getElementById("submitSend");
        submitButton.classList.remove("disabled-link");
    }
}

var flagEmail = 0;

function selectUserByEmail(endpoint, obj) {
    let email = obj.value;
    endpoint += "?email=" + email;
    var submitButton = document.getElementById("submitSend");
    submitButton.classList.remove("disabled-link");
    submitButton.classList.add("disabled-link");
    fetch(endpoint, {
        method: "GET"
    }).then(res => {
        if (res.status === 404) {
            document.getElementById("user-email").style.color = "yellow";
            document.getElementById("user-email").style.fontSize = "14px";
            document.getElementById("user-email").textContent = "Không tìm thấy tài khoản người dùng";
            return;
        }
        return res.json();
    }).then(users => {
        if (!users) {
            flagEmail = 0;
            return;
        } else {
            let userContainer = document.getElementById("user-email");
            userContainer.innerHTML = "";
            users.forEach(user => {
                const radioInput = document.createElement("input");
                radioInput.type = "radio";
                radioInput.name = "user";
                radioInput.value = user.id;
                userContainer.appendChild(radioInput);

                const label = document.createElement("label");
                label.textContent = user.username;
                label.style.color = "yellow";
                label.style.fontSize = "14px";
                userContainer.appendChild(label);

                userContainer.appendChild(document.createElement("br"));
            });
            const firstRadio = userContainer.querySelector("input[type='radio']");
            if (firstRadio) {
                firstRadio.checked = true;
            }
            flagEmail = 1;
            if (flagCaptcha === 1 && flagEmail === 1) {
                submitButton.classList.remove("disabled-link");
            }
        }
    });
}