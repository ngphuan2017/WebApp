<%-- 
    Document   : change-password
    Created on : Jul 19, 2023, 5:39:56 PM
    Author     : phuan
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<script src="<c:url value="/resources/js/change-password.js" />"></script>
<c:url value="/" var="action" />
<c:url value="/forgot-password/change-password?ticket=${ticket}" var="changePassword" />
<c:url value="/api/users/verification" var="verification"/>
<c:url value="/api/users/verification/checked" var="checkedverify"/>
<c:url value="https://res.cloudinary.com/dkmug1913/image/upload/v1680755795/gzivuxwv1azal0niw2vk.png" var="logoBackground" />

<div class="main">
    <div class="content">
        <div style="background: url(https://res.cloudinary.com/dkmug1913/image/upload/v1693815774/WebApp/pngtree-abstract-technology-background-line-high-tech-electricity-image_430309_pwbp0l.jpg) top center / cover no-repeat;
             min-height: 97vh; font-size: 18px; display: flex; justify-content: center; align-items: center;">
            <form method="post" action="${changePassword}" id="form-change-password">
                <div style="display: flex; width: 100%; justify-content: center; margin-bottom: 20px;">
                    <a href="${action}">
                        <img style="width: 90px; border-radius: 50%;" src="${logoBackground}" alt="logo">
                    </a>
                    <div>
                        <span class="form-heading">&nbsp;&nbsp;&nbsp;ĐỔI MẬT KHẨU</span>
                    </div>
                </div>
                <div class="form-group">
                    <i class="fa-solid fa-key"></i>
                    <input type="password" id="password" name="password" class="form-input" placeholder="Nhập mật khẩu mới" onchange="validatePassword()">
                    <div class="eye">
                        <i class="far fa-eye-slash"></i>
                    </div>
                    <div class="error" id="password-error"></div>
                </div>
                <div class="form-group">
                    <i class="fa-solid fa-key"></i>
                    <input type="password" id="confirmPassword" name="confirmPassword" class="form-input" placeholder="Xác nhận mật khẩu mới" onchange="validateConfirmPassword()">
                    <div class="eye">
                        <i class="far fa-eye-slash"></i>
                    </div>
                    <div class="error" id="confirmPassword-error"></div>
                </div>
                <div style="display: flex; justify-content: center;">
                    <div class="g-recaptcha" data-sitekey="6LfuJ-UnAAAAAJ5PRW85U1UQy4JS8lqUe7vfrcxs" data-callback="enableSubmitButton"></div>
                </div>
                <p id="recaptcha-error" style="display: none; font-size: 14px; color: red; text-align: center;">Bạn là Robot?!</p>
                <p class="inner">
                    <a href="javascript:;" class="button disabled-link" id="submitChangePassword" onclick="checkVerification('${verification}', '${currentUser.email}')">
                        <span class="border"></span>
                        <span class="top"></span>
                        <span class="right"></span>
                        <span class="bottom"></span>
                        <span class="left"></span>
                        <span class="text"><input type="submit" style="display: none;" value=""/>Lưu thay đổi</span>
                    </a>
                </p>
                <div class="d-flex-center">
                    <div class="spinner-loading" id="spinner-loading"></div>
                </div>
                <div class="facebook-btn">
                    <a href="${action}" id="cancelSubmit">
                        <p class="btn-text"><b>Quay lại</b></p>
                    </a>
                </div>
            </form>
            <div id="verification-email" style="display: none;">
                <div style="display: flex; width: 100%; justify-content: center; margin-bottom: 20px;">
                    <a href="${action}">
                        <img style="width: 90px; border-radius: 50%;"
                             src="<c:url value="https://res.cloudinary.com/dkmug1913/image/upload/v1680755795/gzivuxwv1azal0niw2vk.png" />"
                             alt="logo">
                    </a>
                    <div>
                        <span class="form-heading" style="font-size: 25px;">&nbsp;&nbsp;&nbsp;
                            <i class="fa-solid fa-user-check"></i> MÃ XÁC MINH
                        </span>
                    </div>
                </div>
                <div style="color: #fff; text-align: center; font-size: 14px;">
                    <p>Đã gửi email với mã xác minh đến "<span id="send-email"></span>". Nhập mã ở đây:</p>
                </div>
                <div class="v-form-group">
                    <input type="text" inputmode="numeric" pattern="[0-9]*" class="v-form-input otp-email" onchange="handleAllInputsFilled()" maxlength="1">
                    <input type="text" inputmode="numeric" pattern="[0-9]*" class="v-form-input otp-email" onchange="handleAllInputsFilled()" maxlength="1">
                    <input type="text" inputmode="numeric" pattern="[0-9]*" class="v-form-input otp-email" onchange="handleAllInputsFilled()" maxlength="1">
                    <input type="text" inputmode="numeric" pattern="[0-9]*" class="v-form-input otp-email" onchange="handleAllInputsFilled()" maxlength="1">
                    <input type="text" inputmode="numeric" pattern="[0-9]*" class="v-form-input otp-email" onchange="handleAllInputsFilled()" maxlength="1">
                    <input type="text" inputmode="numeric" pattern="[0-9]*" class="v-form-input otp-email" onchange="handleAllInputsFilled()" maxlength="1">
                </div>
                <div class="resend-email">
                    <a class="disabled-link" href="javascript:;" style="color: #09f;">Gửi lại mã</a>
                </div>
                <p class="inner">
                    <a href="javascript:;" class="button disabled-link" id="submit-send" onclick="checkedVerify('${checkedverify}', '${currentUser.email}')">
                        <span class="border"></span>
                        <span class="top"></span>
                        <span class="right"></span>
                        <span class="bottom"></span>
                        <span class="left"></span>
                        <span class="text"><input type="button" style="display: none;" value=""/>Xác nhận</span>
                    </a>
                </p>
                <div class="d-flex-center">
                    <div class="spinner-loading" id="spinner-verify"></div>
                </div>
                <div class="facebook-btn">
                    <a href="javascript:;" onclick="cancelCheckVerification()" id="cancelSave">
                        <p class="btn-text"><b>Quay lại chỉnh sửa</b></p>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    function handleSubmit() {
        const myForm = document.getElementById('form-change-password');
        myForm.submit();
    }
</script>