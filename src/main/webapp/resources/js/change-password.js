/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
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

var flagCaptcha = 0;
var flagPassword = 0;
var flagConfirmPassword = 0;
function validatePassword() {
    var submitButton = document.getElementById("submitChangePassword");
    submitButton.classList.remove("disabled-link");
    submitButton.classList.add("disabled-link");
    flagPassword = 0;
    var password = document.getElementById("password").value;
    var passwordError = document.getElementById('password-error');
    const passwordMinLength = 6;

    if (password.includes(" ")) {
        passwordError.textContent = 'Mật khẩu không chứa khoảng trắng';
        passwordError.style.display = 'block';
    } else if (password.length < passwordMinLength) {
        passwordError.textContent = 'Mật khẩu chứa tối thiểu ' + passwordMinLength + ' ký tự';
        passwordError.style.display = 'block';
    } else {
        passwordError.textContent = '';
        passwordError.style.display = 'none';
        flagPassword = 1;
    }
}

function validateConfirmPassword() {
    var submitButton = document.getElementById("submitChangePassword");
    submitButton.classList.remove("disabled-link");
    submitButton.classList.add("disabled-link");
    flagConfirmPassword = 0;
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPassword").value;
    var confirmPasswordError = document.getElementById('confirmPassword-error');

    if (password !== confirmPassword) {
        confirmPasswordError.textContent = 'Mật khẩu không trùng khớp';
        confirmPasswordError.style.display = 'block';
    } else {
        confirmPasswordError.textContent = '';
        confirmPasswordError.style.display = 'none';
        flagConfirmPassword = 1;
        if (flagPassword === 1 && flagConfirmPassword === 1 && flagCaptcha === 1) {
            submitButton.classList.remove("disabled-link");
        }
    }
}

function enableSubmitButton() {
    flagCaptcha = 1;
    if (flagPassword === 1 && flagConfirmPassword === 1 && flagCaptcha === 1) {
        var submitButton = document.getElementById("submitChangePassword");
        submitButton.classList.remove("disabled-link");
    }
}
