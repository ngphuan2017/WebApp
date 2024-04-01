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

    if (fusernameError !== null) {
        fusernameError.textContent = '';
        fusernameError.style.display = 'none';
    }
    if (username.includes(" ")) {
        usernameError.textContent = 'Tên đăng nhập không chứa khoảng trắng';
        usernameError.style.display = 'block';
    } else if (username.length < usernameMinLength) {
        usernameError.textContent = 'Tên đăng nhập tối thiểu ' + usernameMinLength + ' ký tự';
        usernameError.style.display = 'block';
    } else {
        usernameError.textContent = '';
        usernameError.style.display = 'none';
        return true;
    }
    return false;
}

function validateFullname() {
    var fullname = document.getElementById("fullname").value;
    var fullnameError = document.getElementById('fullname-error');
    var ffullnameError = document.getElementById('form-fullname-error');
    const fullnameMaxLength = 100;

    if (ffullnameError !== null) {
        ffullnameError.textContent = '';
        ffullnameError.style.display = 'none';
    }
    if (fullname.length > fullnameMaxLength) {
        fullnameError.textContent = 'Họ và tên tối đa ' + fullnameMaxLength + ' ký tự';
        fullnameError.style.display = 'block';
    } else if (fullname.trim() === "") {
        fullnameError.textContent = 'Vui lòng nhập họ và tên';
        fullnameError.style.display = 'block';
    } else {
        fullnameError.textContent = '';
        fullnameError.style.display = 'none';
        return true;
    }
    return false;
}

function validateEmail() {
    var email = document.getElementById("email").value;
    var emailError = document.getElementById('email-error');
    var femailError = document.getElementById('form-email-error');
    var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

    if (femailError !== null) {
        femailError.textContent = '';
        femailError.style.display = 'none';
    }
    if (email.includes(" ")) {
        emailError.textContent = 'Email không chứa khoảng trắng';
        emailError.style.display = 'block';
    } else if (!emailPattern.test(email)) {
        emailError.textContent = 'Email chưa đúng định dạng';
        emailError.style.display = 'block';
    } else {
        emailError.textContent = '';
        emailError.style.display = 'none';
        return true;
    }
    return false;
}

function validatePassword() {
    var password = document.getElementById("password").value;
    var passwordError = document.getElementById('password-error');
    var fpasswordError = document.getElementById('form-password-error');
    const passwordMinLength = 6;

    if (fpasswordError !== null) {
        fpasswordError.textContent = '';
        fpasswordError.style.display = 'none';
    }
    if (password.includes(" ")) {
        passwordError.textContent = 'Mật khẩu không chứa khoảng trắng';
        passwordError.style.display = 'block';
    } else if (password.length < passwordMinLength) {
        passwordError.textContent = 'Mật khẩu chứa tối thiểu ' + passwordMinLength + ' ký tự';
        passwordError.style.display = 'block';
    } else {
        passwordError.textContent = '';
        passwordError.style.display = 'none';
        return true;
    }
    return false;
}

function validatePhone() {
    var phone = document.getElementById("phone").value;
    var phoneError = document.getElementById('phone-error');
    var fphoneError = document.getElementById('form-phone-error');
    var phonePattern = /^[0-9]{10}$/;

    if (fphoneError !== null) {
        fphoneError.textContent = '';
        fphoneError.style.display = 'none';
    }
    if (!phonePattern.test(phone)) {
        phoneError.textContent = 'Số điện thoại chưa đúng';
        phoneError.style.display = 'block';
    } else {
        phoneError.textContent = '';
        phoneError.style.display = 'none';
        return true;
    }
    return false;
}

function validateConfirmPassword() {
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPassword").value;
    var confirmPasswordError = document.getElementById('confirmPassword-error');
    var fconfirmPasswordError = document.getElementById('form-confirmPassword-error');

    if (fconfirmPasswordError !== null) {
        fconfirmPasswordError.textContent = '';
        fconfirmPasswordError.style.display = 'none';
    }
    if (password !== confirmPassword) {
        confirmPasswordError.textContent = 'Mật khẩu không trùng khớp';
        confirmPasswordError.style.display = 'block';
    } else {
        confirmPasswordError.textContent = '';
        confirmPasswordError.style.display = 'none';
        return true;
    }
    return false;
}

function changeCity(city) {
    var citySelect = document.getElementById("citySelect");
    var selectedCityLabel = citySelect.options[citySelect.selectedIndex].label;
    var pathCity = document.getElementById("pathCity");
    pathCity.value = selectedCityLabel;
    var districtSelect = document.getElementById("districtSelect");
    var endpoint = city + "/" + citySelect.value;
    fetch(endpoint, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/xml'
        }
    }).then(res =>
        res.json()
    ).then(data => {
        districtSelect.innerHTML = '';
        var option = document.createElement("option");
        option.value = "";
        option.label = "Quận/Huyện";
        option.style.color = "black";
        option.selected = true;
        districtSelect.appendChild(option);
        data.forEach(district => {
            var option = document.createElement("option");
            option.value = district.id;
            option.label = district.district;
            option.style.color = "black";
            districtSelect.appendChild(option);
        });
    }).catch(error => {
        console.info(error);
    });
}

function changeDistrict(district) {
    var districtSelect = document.getElementById("districtSelect");
    var selectedDistrictLabel = districtSelect.options[districtSelect.selectedIndex].label;
    var pathDistrict = document.getElementById("pathDistrict");
    pathDistrict.value = selectedDistrictLabel;
    var wardSelect = document.getElementById("wardSelect");

    var endpoint = district + "/" + districtSelect.value;

    fetch(endpoint, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/xml'
        }
    }).then(res =>
        res.json()
    ).then(data => {
        wardSelect.innerHTML = '';
        var option = document.createElement("option");
        option.value = "";
        option.label = "Phường/Xã";
        option.style.color = "black";
        option.selected = true;
        wardSelect.appendChild(option);
        data.forEach(ward => {
            var option = document.createElement("option");
            option.value = ward.id;
            option.label = ward.ward;
            option.style.color = "black";
            wardSelect.appendChild(option);
        });
    }).catch(error => {
        console.info(error);
    });
}

function changeWard() {
    var wardSelect = document.getElementById("wardSelect");
    var selectedWardLabel = wardSelect.options[wardSelect.selectedIndex].label;
    var pathWard = document.getElementById("pathWard");
    pathWard.value = selectedWardLabel;
}

function checkVerification(endpoint) {
    let email = document.getElementById("email");
    let sendEmail = document.getElementById("send-email");
    document.getElementById("form-submit").style.display = 'none';
    document.getElementById("spinner-loading").style.display = 'block';
    if (validateFullname() && validatePhone() && validateEmail() && validateUsername() && validatePassword() && validateConfirmPassword()) {
        fetch(endpoint, {
            method: "POST",
            body: JSON.stringify({
                "propersion": email.value
            }),
            headers: {
                "Content-Type": "application/json"
            }
        }).then(res => {
            if (res.status === 201) {
                document.getElementById("form-register").style.display = 'none';
                document.getElementById("verification-email").style.display = 'block';
                sendEmail.textContent = email.value;
                document.getElementById("form-submit").style.display = 'block';
                document.getElementById("spinner-loading").style.display = 'none';
            }
        });
    } else {
        Swal.fire('Cảnh báo!', 'Vui lòng điền đầy đủ thông tin!', 'warning');
        document.getElementById("form-submit").style.display = 'block';
        document.getElementById("spinner-loading").style.display = 'none';
    }
}

function cancelCheckVerification() {
    document.getElementById("form-register").style.display = 'block';
    document.getElementById("verification-email").style.display = 'none';
}

var flagCaptcha = 0;

function enableSubmitButton() {
    flagCaptcha = 1;
    if (flagCaptcha === 1 && flagOtpInput === 1) {
        var submitButton = document.getElementById("submit-send");
        submitButton.classList.remove("disabled-link");
    }
}

var flagOtpInput = 0;

function checkAllInputsFilled() {
    let otpInputs = document.querySelectorAll('.v-form-group .otp-email');
    for (var i = 0; i < otpInputs.length; i++) {
        if (otpInputs[i].value === "") {
            var submitButton = document.getElementById("submit-send");
            submitButton.classList.add("disabled-link");
            return false;
        }
    }
    return true;
}
function handleAllInputsFilled() {
    if (checkAllInputsFilled()) {
        flagOtpInput = 1;
        if (flagCaptcha === 1 && flagOtpInput === 1) {
            var submitButton = document.getElementById("submit-send");
            submitButton.classList.remove("disabled-link");
        }
    }
}

function checkedVerify(endpoint) {
    document.getElementById("submit-send").style.display = 'none';
    document.getElementById("spinner-verify").style.display = 'block';
    let email = document.getElementById("email");
    let otpInputs = document.getElementsByClassName('otp-email');
    let otpCode = "";
    for (let i = 0; i < otpInputs.length; i++) {
        otpCode += otpInputs[i].value.trim();
    }
    fetch(endpoint, {
        method: "PUT",
        body: JSON.stringify({
            "propersion": email.value,
            "otpCode": otpCode
        }),
        headers: {
            "Content-Type": "application/json"
        }
    }).then(res => {
        if (res.status === 200) {
            document.getElementById("spinner-verify").style.display = 'none';
            document.getElementById("submit-send").style.display = 'block';
            Swal.fire('Xác thực thành công!', 'Xin vui lòng đợi trong giây lát!', 'success');
            handleSubmit();
        } else {
            document.getElementById("spinner-verify").style.display = 'none';
            document.getElementById("submit-send").style.display = 'block';
            Swal.fire('Lỗi!', 'Mã xác thực của bạn không đúng!', 'error');
        }
    });
}
