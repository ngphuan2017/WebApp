/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
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

function AvatarBrowse() {
    document.getElementById("avatarBrowse").click();
}

function showPreviewDiv(event) {
    if (event.target.files.length > 0) {
        let src = URL.createObjectURL(event.target.files[0]);
        let preview = document.getElementById("img-preview");
        preview.style.backgroundImage = `url(${src})`;
        preview.style.display = "block";
    }
}

function validateUsername() {
    var username = document.getElementById("username").value;
    var usernameError = document.getElementById('username-error');
    var fusernameError = document.getElementById('form-username-error');
    const usernameMinLength = 6;

    fusernameError.textContent = '';
    fusernameError.style.display = 'none';

    if (username.includes(" ")) {
        usernameError.textContent = 'Tên đăng nhập không chứa khoảng trắng';
        usernameError.style.display = 'block';
    } else if (username.length < usernameMinLength) {
        usernameError.textContent = 'Tên đăng nhập tối thiểu ' + usernameMinLength + ' ký tự';
        usernameError.style.display = 'block';
    } else {
        usernameError.textContent = '';
        usernameError.style.display = 'none';
    }
}

function validateFullname() {
    var fullname = document.getElementById("fullname").value;
    var fullnameError = document.getElementById('fullname-error');
    var ffullnameError = document.getElementById('form-fullname-error');
    const fullnameMaxLength = 100;

    ffullnameError.textContent = '';
    ffullnameError.style.display = 'none';

    if (fullname.length > fullnameMaxLength) {
        fullnameError.textContent = 'Họ và tên tối đa ' + fullnameMaxLength + ' ký tự';
        fullnameError.style.display = 'block';
    } else if (fullname.trim() === "") {
        fullnameError.textContent = 'Vui lòng nhập họ và tên';
        fullnameError.style.display = 'block';
    } else {
        fullnameError.textContent = '';
        fullnameError.style.display = 'none';
    }
}

function validateEmail() {
    var email = document.getElementById("email").value;
    var emailError = document.getElementById('email-error');
    var femailError = document.getElementById('form-email-error');
    var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

    femailError.textContent = '';
    femailError.style.display = 'none';

    if (email.includes(" ")) {
        emailError.textContent = 'Email không chứa khoảng trắng';
        emailError.style.display = 'block';
    } else if (!emailPattern.test(email)) {
        emailError.textContent = 'Email chưa đúng định dạng';
        emailError.style.display = 'block';
    } else {
        emailError.textContent = '';
        emailError.style.display = 'none';
    }
}

function validatePassword() {
    var password = document.getElementById("password").value;
    var passwordError = document.getElementById('password-error');
    var fpasswordError = document.getElementById('form-password-error');
    const passwordMinLength = 6;

    fpasswordError.textContent = '';
    fpasswordError.style.display = 'none';

    if (password.includes(" ")) {
        passwordError.textContent = 'Mật khẩu không chứa khoảng trắng';
        passwordError.style.display = 'block';
    } else if (password.length < passwordMinLength) {
        passwordError.textContent = 'Mật khẩu chứa tối thiểu ' + passwordMinLength + ' ký tự';
        passwordError.style.display = 'block';
    } else {
        passwordError.textContent = '';
        passwordError.style.display = 'none';
    }
}

function validatePhone() {
    var phone = document.getElementById("phone").value;
    var phoneError = document.getElementById('phone-error');
    var fphoneError = document.getElementById('form-phone-error');
    var phonePattern = /^[0-9]{10}$/;

    fphoneError.textContent = '';
    fphoneError.style.display = 'none';

    if (!phonePattern.test(phone)) {
        phoneError.textContent = 'Số điện thoại chưa đúng';
        phoneError.style.display = 'block';
    } else {
        phoneError.textContent = '';
        phoneError.style.display = 'none';
    }
}

function validateConfirmPassword() {
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPassword").value;
    var confirmPasswordError = document.getElementById('confirmPassword-error');
    var fconfirmPasswordError = document.getElementById('form-confirmPassword-error');

    fconfirmPasswordError.textContent = '';
    fconfirmPasswordError.style.display = 'none';

    if (password !== confirmPassword) {
        confirmPasswordError.textContent = 'Mật khẩu không trùng khớp';
        confirmPasswordError.style.display = 'block';
    } else {
        confirmPasswordError.textContent = '';
        confirmPasswordError.style.display = 'none';
    }
}