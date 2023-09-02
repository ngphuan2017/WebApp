<%-- 
    Document   : login
    Created on : Apr 15, 2023, 2:36:57 PM
    Author     : admin
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<script src="<c:url value="/resources/js/login.js" />"></script>
<c:url value="/" var="action" />
<c:url value="/login" var="login" />
<c:url value="/register" var="register" />
<c:url value="/forgot-password" var="forgot" />
<div class="main">
    <div class="content">
        <div class="wrapper">
            <form method="post" action="${login}" id="form-login">
                <div style="display: flex; width: 100%; justify-content: center; margin-bottom: 20px;">
                    <a href="${action}">
                        <img style="width: 90px; border-radius: 50%;" src="<c:url value="https://res.cloudinary.com/dkmug1913/image/upload/v1680755795/gzivuxwv1azal0niw2vk.png" />" alt="logo">
                    </a>
                    <div>
                        <span class="form-heading">&nbsp;&nbsp;&nbsp;ĐĂNG NHẬP</span>
                    </div>
                </div>
                <div class="form-group">
                    <i class="fa-solid fa-user"></i>
                    <input type="text" class="form-input" id="username" name="username" placeholder="Tên đăng nhập" onchange="hideErrorMessage()">
                </div>
                <div class="form-group">
                    <i class="fa-solid fa-key"></i>
                    <input type="password" id="pwd" name="password" class="form-input" placeholder="Mật khẩu" onchange="hideErrorMessage()">
                    <div class="eye">
                        <i class="far fa-eye-slash"></i>
                    </div>
                </div>
                <div id="error-message" style="font-size: 13px; color: red; display: block; text-align: center;">${error}</div>
                <div style="width: 100%; padding: 5px 0;">
                    <div class="checkbox-wrapper">
                        <input type="checkbox" id="remember-me" checked>
                        <label for="remember-me" class="check">
                            <svg width="18px" height="18px" viewBox="0 0 18 18">
                            <path d="M1,9 L1,3.5 C1,2 2,1 3.5,1 L14.5,1 C16,1 17,2 17,3.5 L17,14.5 C17,16 16,17 14.5,17 L3.5,17 C2,17 1,16 1,14.5 L1,9 Z"></path>
                            <polyline points="1 9 7 14 15 4"></polyline>
                            </svg>
                        </label>
                        <span style="color: #fff; font-size: 12px;"> Ghi nhớ đăng nhập</span>
                    </div>
                    <div style="width: 40%; display: inline-block; text-align: right;">
                        <a href="javascript:;" onclick="forgotPassword()" style="color: #09f; font-size: 12px;">Quên mật khẩu?</a>
                    </div>
                </div>
                <p class="inner">
                    <a href="javascript:;" class="button" id="submitButton">
                        <span class="border"></span>
                        <span class="top"></span>
                        <span class="right"></span>
                        <span class="bottom"></span>
                        <span class="left"></span>
                        <span class="text"><input type="submit" style="display: none;" value=""/>Đăng Nhập</span>
                    </a>
                </p>
                <div style="width: 100%; text-align: center; padding: 10px;">
                    <span style="color: #fff; font-size: 11px;">Bạn chưa có tài khoản</span>
                    <a href="${register}" style="color: #09f; font-size: 12px;">Đăng ký ngay</a>
                </div>
                <div style="display: flex; margin-top: 20px;">
                    <div class="kengang"></div>
                    <span style="color: #ccc; padding: 0 16px; text-transform: uppercase; font-size: 12px;">hoặc</span>
                    <div class="kengang"></div>
                </div>
                <div class="google-btn">
                    <a href="https://accounts.google.com/o/oauth2/auth?scope=profile+email&redirect_uri=http://localhost:8080/WebApp/login/login-google&response_type=code&client_id=845125163352-7naesd2b78gchec9rg08ecg8s5euh31l.apps.googleusercontent.com&approval_prompt=force">
                        <div class="google-icon-wrapper">
                            <img class="google-icon" src="https://upload.wikimedia.org/wikipedia/commons/5/53/Google_%22G%22_Logo.svg"/>
                        </div>
                        <p class="btn-text"><b>Đăng nhập bằng Google</b></p>
                    </a>
                </div>
                <div class="facebook-btn">
                    <a href="https://www.facebook.com/dialog/oauth?client_id=1030898755004343&redirect_uri=http://localhost:8080/WebApp/login/login-facebook&scope=email,public_profile,user_gender,user_link,user_location">
                        <div class="facebook-icon-wrapper">
                            <i class="fa-brands fa-square-facebook"></i>
                        </div>
                        <p class="btn-text"><b>Đăng nhập bằng Facebook</b></p> 
                    </a>
                </div>
            </form>
            <form method="post" action="${forgot}" id="forgot-password" style="display: none;">
                <div style="display: flex; width: 100%; justify-content: center; margin-bottom: 20px;">
                    <a href="${action}">
                        <img style="width: 90px; border-radius: 50%;" src="<c:url value="https://res.cloudinary.com/dkmug1913/image/upload/v1680755795/gzivuxwv1azal0niw2vk.png" />" alt="logo">
                    </a>
                    <div>
                        <span class="form-heading" style="font-size: 25px;">&nbsp;&nbsp;&nbsp;<i class="fa-solid fa-lock"></i> QUÊN MẬT KHẨU</span>
                    </div>
                </div>
                <div style="color: #fff; text-align: center; font-size: 14px;">
                    <span>Vui lòng nhập Email đã đăng ký tài khoản để nhận mã xác nhận</span>
                </div>
                <div class="form-group">
                    <i class="fa-solid fa-envelope"></i>
                    <input type="email" class="form-input" id="sendverify" name="email" placeholder="Email" onchange="selectUserByEmail('${forgot}', this)">
                </div>
                <div style="margin-top: 15px; margin-bottom: 20px; display: flex; line-height: 20px;" id="user-email">

                </div>
                <div style="display: flex; justify-content: center;">
                    <div class="g-recaptcha" data-sitekey="6LfuJ-UnAAAAAJ5PRW85U1UQy4JS8lqUe7vfrcxs" data-callback="enableSubmitButton"></div>
                </div>
                <p id="recaptcha-error" style="display: none; font-size: 14px; color: red; text-align: center;">Bạn là Robot?!</p>
                <p class="inner">
                    <a href="javascript:;" class="button disabled-link" id="submitSend">
                        <span class="border"></span>
                        <span class="top"></span>
                        <span class="right"></span>
                        <span class="bottom"></span>
                        <span class="left"></span>
                        <span class="text"><input type="submit" style="display: none;" value=""/>Gửi mã xác nhận</span>
                    </a>
                </p>
                <div class="facebook-btn">
                    <a href="javascript:;" onclick="cancelForgotPassword()" disabled>
                        <p class="btn-text"><b>Quay lại đăng nhập</b></p> 
                    </a>
                </div>
            </form>
        </div>
        <div class="banner">
            <div class="clouds">
                <img src="<c:url value="https://res.cloudinary.com/dkmug1913/image/upload/v1687075887/WebApp/cloud1_ortplp.png" />" style="--i:1;">
                <img src="<c:url value="https://res.cloudinary.com/dkmug1913/image/upload/v1687075891/WebApp/cloud2_ngpdc4.png" />" style="--i:2;">
                <img src="<c:url value="https://res.cloudinary.com/dkmug1913/image/upload/v1687075895/WebApp/cloud3_szlrii.png" />" style="--i:3;">
            </div>
        </div>
    </div>
</div>
<script>
    // Tham chiếu đến thẻ form và nút <a>
    const myForm = document.getElementById('form-login');
    const submitButton = document.getElementById('submitButton');
    const sendForm = document.getElementById('forgot-password');
    const submitSend = document.getElementById('submitSend');
    // Hàm xử lý sự kiện khi ấn vào nút <a>
    function handleSubmit(event) {
        event.preventDefault(); // Ngăn chặn hành vi mặc định của liên kết
        // Gửi form bằng cách sử dụng phương thức submit() của form
        myForm.submit();
    }
    ////////////
    function sendSubmit(event) {
        event.preventDefault(); // Ngăn chặn hành vi mặc định của liên kết
        // Gửi form bằng cách sử dụng phương thức submit() của form
        sendForm.submit();
    }
    // Gán hàm xử lý cho sự kiện click trên nút <a>
    submitButton.addEventListener('click', handleSubmit);
    //////
    submitSend.addEventListener('click', sendSubmit);
    //////////////////////////////////////////////
</script>