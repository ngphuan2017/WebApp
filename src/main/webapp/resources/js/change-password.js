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

function checkVerification(endpoint, email) {
    let sendEmail = document.getElementById("send-email");
    document.getElementById("submitChangePassword").style.display = 'none';
    document.getElementById("spinner-loading").style.display = 'block';
    document.getElementById("cancelSubmit").classList.add("disabled-link");
    fetch(endpoint, {
        method: "POST",
        body: JSON.stringify({
            "propersion": email
        }),
        headers: {
            "Content-Type": "application/json"
        }
    }).then(res => {
        if (res.status === 201) {
            document.getElementById("form-change-password").style.display = 'none';
            document.getElementById("verification-email").style.display = 'block';
            sendEmail.textContent = email;
            // Gọi hàm để khởi tạo các xử lý cho OTP khi phần nhập OTP được hiển thị
            initOtpInputHandlers();
            document.getElementById("submitChangePassword").style.display = 'block';
            document.getElementById("spinner-loading").style.display = 'none';
            document.getElementById("cancelSubmit").classList.remove("disabled-link");
        }
    });
}

function cancelCheckVerification() {
    document.getElementById("form-change-password").style.display = 'block';
    document.getElementById("verification-email").style.display = 'none';
}

function initOtpInputHandlers() {
    document.querySelectorAll('.otp-email').forEach((input, index, inputs) => {
        input.addEventListener('input', (e) => {
            let value = e.target.value;

            // Chuyển sang ô tiếp theo nếu đã nhập 1 ký tự
            if (value.length === 1 && index < inputs.length - 1) {
                inputs[index + 1].focus();
            }
        });

        // Ngăn không cho nhập ký tự không phải là số khi nhấn phím.
        input.addEventListener('keydown', (e) => {
            const key = e.key;
            const isCtrlV = (e.ctrlKey || e.metaKey) && key === 'v';

            // Chỉ cho phép các phím số từ 0-9 và các phím điều hướng như Backspace, Tab, Arrow keys, Delete, hoặc Ctrl + V
            if (!/^[0-9]$/.test(key) && key !== "Backspace" && key !== "Tab" && key !== "ArrowLeft" && key !== "ArrowRight" && key !== "Delete" && !isCtrlV) {
                e.preventDefault();
            }

            // Xử lý Backspace: Di chuyển con trỏ về ô trước đó.
            if (key === 'Backspace' && index > 0 && input.value === '') {
                inputs[index - 1].focus();
            }

            // Xử lý Delete: Di chuyển con trỏ sang ô kế tiếp.
            if (key === 'Delete' && index < inputs.length - 1) {
                inputs[index + 1].focus();
            }
        });

        // Xử lý khi người dùng dán cả chuỗi
        input.addEventListener('paste', (e) => {
            e.preventDefault();

            // Lấy dữ liệu từ clipboard
            let pasteData = e.clipboardData.getData('text');

            // Kiểm tra xem chuỗi có đúng 6 ký tự và chỉ chứa số không
            if (/^\d{6}$/.test(pasteData)) {
                const pasteArray = pasteData.split(''); // Chia từng ký tự

                pasteArray.forEach((char, i) => {
                    if (inputs[index + i]) {
                        inputs[index + i].value = char; // Điền ký tự vào các ô
                        if (index + i < inputs.length - 1) {
                            inputs[index + i + 1].focus(); // Di chuyển con trỏ sang ô tiếp theo
                        }
                    }
                });
                let submitButton = document.getElementById("submit-send");
                submitButton.classList.remove("disabled-link");
            } else {
                // Nếu chuỗi dán không hợp lệ, hiển thị thông báo
                Swal.fire('Thông báo!', 'Chuỗi dán không hợp lệ. Vui lòng dán đúng 6 số.', 'info');
            }
        });
    });
}

function checkAllInputsFilled() {
    let otpInputs = document.querySelectorAll('.v-form-group .otp-email');
    for (var i = 0; i < otpInputs.length; i++) {
        if (otpInputs[i].value === "") {
            let submitButton = document.getElementById("submit-send");
            submitButton.classList.add("disabled-link");
            return false;
        }
    }
    return true;
}
function handleAllInputsFilled() {
    if (checkAllInputsFilled()) {
        var submitButton = document.getElementById("submit-send");
        submitButton.classList.remove("disabled-link");
    }
}

function checkedVerify(endpoint, email) {
    if (email === null || email === "") {
        Swal.fire('Lỗi!', 'Tài khoản chưa cập nhật thông tin Email!', 'error');
    } else {
        document.getElementById("submit-send").style.display = 'none';
        document.getElementById("spinner-verify").style.display = 'block';
        document.getElementById("cancelSave").classList.add("disabled-link");
        let otpInputs = document.getElementsByClassName('otp-email');
        let otpCode = "";
        for (let i = 0; i < otpInputs.length; i++) {
            otpCode += otpInputs[i].value.trim();
        }
        fetch(endpoint, {
            method: "PUT",
            body: JSON.stringify({
                "propersion": email,
                "otpCode": otpCode
            }),
            headers: {
                "Content-Type": "application/json"
            }
        }).then(res => {
            if (res.status === 200) {
                Swal.fire('Xác thực thành công!', 'Xin vui lòng đợi trong giây lát!', 'success');
                handleSubmit();
            } else if (res.status === 403){
                Swal.fire('Lỗi!', 'Mã xác thực của bạn không đúng!', 'error');
            }
            else if (res.status === 401){
                Swal.fire('Lỗi!', 'Mã xác thực của bạn đã hết hạn!', 'error');
            }
            else {
                Swal.fire('Lỗi!', 'Đã xảy ra lỗi, nhưng đừng bực mình - đây không phải là lỗi của bạn!', 'error');
            }
            document.getElementById("spinner-verify").style.display = 'none';
            document.getElementById("submit-send").style.display = 'block';
            document.getElementById("cancelSave").classList.remove("disabled-link");
        });
    }
}