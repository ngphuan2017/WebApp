<%-- 
    Document   : register
    Created on : Apr 15, 2023, 2:36:57 PM
    Author     : admin
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<script src="<c:url value="/resources/js/register.js" />"></script>

<c:url value="/" var="action" />
<c:url value="/login" var="login" />
<c:url value="/register" var="register"/>
<c:url value="/api/users/verification" var="verification"/>
<c:url value="/api/users/verification/checked" var="checkedverify"/>
<c:url value="/api/users/register/city" var="city"/>
<c:url value="/api/users/register/district" var="district"/>
<c:url value="https://res.cloudinary.com/dkmug1913/image/upload/v1680755795/gzivuxwv1azal0niw2vk.png" var="logoBackground" />

<div class="main">
    <div class="content">
        <div class="wrapper">
            <form:form action="${register}" method="POST" class="user" enctype="multipart/form-data"
                       modelAttribute="user" id="form-register">
                <form:hidden path="id"/>
                <form:hidden path="avatar"/>
                <div style="display: flex; width: 100%; justify-content: center; margin-bottom: 20px;">
                    <a href="${action}">
                        <img style="width: 90px; border-radius: 50%;" src="${logoBackground}" alt="logo">
                    </a>
                    <div>
                        <span class="form-heading">&nbsp;&nbsp;&nbsp;ĐĂNG KÝ</span>
                    </div>
                </div>
                <div class="row">
                    <div class="r-form-group col-md-4">
                        <div class="r-form-input">
                            <i class="fa-solid fa-user-pen"></i>
                            <form:input path="fullname" class="form-input" id="fullname" onchange="validateFullname()"
                                        type="text" placeholder="Họ và tên"/>
                        </div>
                        <form:errors path="fullname" id="form-fullname-error" element="div" class="form-error"/>
                        <div class="error" id="fullname-error"></div>
                    </div>
                    <div class="r-form-group col-md-4">
                        <div class="r-form-input">
                            <i class="fa-solid fa-phone"></i>
                            <form:input path="phone" class="form-input" id="phone" onchange="validatePhone()"
                                        type="text" placeholder="Số điện thoại"/>
                        </div>
                        <form:errors path="phone" id="form-phone-error" element="div" class="form-error"/>
                        <div class="error" id="phone-error"></div>
                    </div>
                </div>
                <div class="row">
                    <div class="r-form-group col-md-4">
                        <div class="r-form-input">
                            <i class="fa-solid fa-envelope"></i>
                            <form:input path="email" class="form-input" id="email" onchange="validateEmail()"
                                        type="email" placeholder="Email"/>
                        </div>
                        <form:errors path="email" id="form-email-error" element="div" class="form-error"/>
                        <div class="error" id="email-error"></div>
                    </div>
                    <div class="r-form-group col-md-4">
                        <div class="r-form-input">
                            <i class="fa-solid fa-user"></i>
                            <form:input path="username" class="form-input" id="username" onchange="validateUsername()"
                                        type="text" placeholder="Tên đăng nhập"/>
                        </div>
                        <form:errors path="username" id="form-username-error" element="div" class="form-error"/>
                        <div class="error" id="username-error"></div>
                    </div>
                </div>
                <div class="row">
                    <div class="r-form-group col-md-4">
                        <div class="r-form-input">
                            <i class="fa-solid fa-key"></i>
                            <form:input path="password" class="form-input" id="password" onchange="validatePassword()"
                                        type="password" placeholder="Mật khẩu"/>
                            <div class="eye">
                                <i class="far fa-eye-slash"></i>
                            </div>
                        </div>
                        <form:errors path="password" id="form-password-error" element="div" class="form-error"/>
                        <div class="error" id="password-error"></div>
                    </div>
                    <div class="r-form-group col-md-4">
                        <div class="r-form-input">
                            <i class="fa-solid fa-key"></i>
                            <form:input path="confirmPassword" class="form-input" id="confirmPassword" onchange="validateConfirmPassword()"
                                        type="password" placeholder="Nhập lại mật khẩu"/>
                            <div class="eye">
                                <i class="far fa-eye-slash"></i>
                            </div>
                        </div>
                        <form:errors path="confirmPassword" id="form-confirmPassword-error" element="div" class="form-error"/>
                        <div class="error" id="confirmPassword-error"></div>
                    </div>
                </div>
                <div class="row">
                    <div class="r-form-group col-md-4">
                        <div class="r-form-input">
                            <i class="fa-solid fa-venus-mars"></i>
                            <form:select path="gender" class="form-input">
                                <form:option value="" label="Giới tính" cssStyle="color: black;" selected="true" />
                                <form:option value="1" label="Nam" cssStyle="color: black;" />
                                <form:option value="2" label="Nữ" cssStyle="color: black;" />
                                <form:option value="3" label="Khác" cssStyle="color: black;" />
                            </form:select>
                        </div>
                    </div>
                    <div class="r-form-group col-md-4">
                        <div class="r-form-input">
                            <form:hidden id="pathCity" path="city"/>
                            <i class="fa-solid fa-city"></i>
                            <select class="form-input" onchange="changeCity('${city}')" id="citySelect">
                                <option value="" label="Tỉnh/Thành Phố" style="color: black;" selected="true" />
                                <c:forEach items="${listCitys}" var="c">
                                    <option value="${c.id}" label="${c.city}" style="color: black;" />
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="r-form-group col-md-4">
                        <div class="r-form-input">
                            <form:hidden id="pathDistrict" path="district"/>
                            <i class="fa-solid fa-building-circle-arrow-right"></i>
                            <select class="form-input" onchange="changeDistrict('${district}')" id="districtSelect">
                                <option value="" label="Quận/Huyện" style="color: black;" selected="true" />
                            </select>
                        </div>
                    </div>
                    <div class="r-form-group col-md-4">
                        <div class="r-form-input">
                            <form:hidden id="pathWard" path="ward"/>
                            <i class="fa-solid fa-tree-city"></i>
                            <select class="form-input" onchange="changeWard()" id="wardSelect">
                                <option value="" label="Phường/Xã" style="color: black;" selected="true" />
                            </select>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4" style="margin: 15px 10px;">
                        <p style="text-align: center; color: #f5f5f5; font-size: 14px;">Chọn ảnh đại diện</p>
                        <a href="javascript:;" onclick="AvatarBrowse()" class="btn-button">
                            <div class="btn-icon">
                                <i class="fa-regular fa-floppy-disk"></i>
                            </div>
                        </a>
                        <form:input type="file" id="avatarBrowse" path="file"
                                    onchange="showPreviewDiv(event);"
                                    accept="image/*" class="form-input" cssStyle="display: none"/>
                    </div>
                    <div class="col-md-4" style="margin: 15px 10px;">
                        <div id="img-preview"
                             style="background-image: url('https://res.cloudinary.com/dkmug1913/image/upload/v1690819242/WebApp/Avatar/none_ibdmnr.png');
                             width: 100px; height: 100px;
                             margin: auto;
                             background-position: center;
                             background-size: contain;
                             background-repeat: no-repeat;
                             border-radius: 50%;
                             border: 1px solid lightgray;">
                        </div>
                    </div>
                </div>
                <div class="d-flex-center">
                    <input type="submit" id="submit-button" style="display: none;" value=""/>
                    <a href="javascript:;" class="form-submit" id="form-submit" onclick="checkVerification('${verification}')"><span>Đăng Ký</span></a>
                    <div class="spinner-loading" id="spinner-loading"></div>
                </div>
                <div style="width: 100%; text-align: center; padding: 10px;">
                    <span style="color: #fff; font-size: 13px;">Đã có tài khoản</span>
                    <a href="${login}" style="color: #09f; font-size: 13px;">Đăng nhập ngay</a>
                </div>
            </form:form>
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
                    <p>Đã gửi email với mã xác minh đến "<span id="send-email"></span>. Nhập mã ở đây:</p>
                </div>
                <div class="v-form-group">
                    <input type="text" class="v-form-input otp-email" onchange="handleAllInputsFilled()" maxlength="1">
                    <input type="text" class="v-form-input otp-email" onchange="handleAllInputsFilled()" maxlength="1">
                    <input type="text" class="v-form-input otp-email" onchange="handleAllInputsFilled()" maxlength="1">
                    <input type="text" class="v-form-input otp-email" onchange="handleAllInputsFilled()" maxlength="1">
                    <input type="text" class="v-form-input otp-email" onchange="handleAllInputsFilled()" maxlength="1">
                    <input type="text" class="v-form-input otp-email" onchange="handleAllInputsFilled()" maxlength="1">
                </div>
                <div class="resend-email">
                    <a class="disabled-link" href="javascript:;" style="color: #09f;">Gửi lại mã</a>
                </div>
                <div style="display: flex; justify-content: center;">
                    <div class="g-recaptcha" data-sitekey="6LfuJ-UnAAAAAJ5PRW85U1UQy4JS8lqUe7vfrcxs"
                         data-callback="enableSubmitButton"></div>
                </div>
                <p id="recaptcha-error" style="display: none; font-size: 14px; color: red; text-align: center;">Bạn là
                    Robot?!</p>
                <p class="inner">
                    <a href="javascript:;" class="button disabled-link" id="submit-send" onclick="checkedVerify('${checkedverify}')">
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
                    <a href="javascript:;" onclick="cancelCheckVerification()">
                        <p class="btn-text"><b>Quay lại chỉnh sửa</b></p>
                    </a>
                </div>
            </div>
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
    function handleSubmit() {
        const myForm = document.getElementById('form-register');
        myForm.submit();
    }
</script>