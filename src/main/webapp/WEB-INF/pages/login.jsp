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
<div class="main">
    <div class="content">
        <div id="wrapper">
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
                    <input type="text" class="form-input" id="username" name="username" placeholder="Tên đăng nhập">
                </div>
                <div class="form-group">
                    <i class="fa-solid fa-key"></i>
                    <input type="password" id="pwd" name="password" class="form-input" placeholder="Mật khẩu">
                    <div id="eye">
                        <i class="far fa-eye-slash"></i>
                    </div>
                </div>
                <div style="width: 100%; padding: 5px 0;">
                    <div style="width: 49%; display: inline-block; text-align: left;">
                        <input style="cursor: pointer;" type="checkbox" id="remenberpasswork">
                        <span style="color: #fff; font-size: 12px;"> Ghi nhớ đăng nhập</span>
                    </div>
                    <div style="width: 49%; display: inline-block; text-align: right;">
                        <a href="#" style="color: #09f; font-size: 12px;">Quên mật khẩu?</a>
                    </div>
                </div>
                <p class="inner">
                    <a href="javascript: void 0;" class="button" id="submitButton">
                        <span class="border"></span>
                        <span class="top"></span>
                        <span class="right"></span>
                        <span class="bottom"></span>
                        <span class="left"></span>
                        <span class="text">Đăng Nhập</span>
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
                    <div class="google-icon-wrapper">
                        <img class="google-icon" src="https://upload.wikimedia.org/wikipedia/commons/5/53/Google_%22G%22_Logo.svg"/>
                    </div>
                    <p class="btn-text"><b>Đăng nhập bằng Google</b></p>
                </div>
                <div class="facebook-btn">
                    <div class="facebook-icon-wrapper">
                        <i class="fa-brands fa-square-facebook"></i>
                    </div>
                    <p class="btn-text"><b>Đăng nhập bằng Facebook</b></p>
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

    // Hàm xử lý sự kiện khi ấn vào nút <a>
    function handleSubmit(event) {
        event.preventDefault(); // Ngăn chặn hành vi mặc định của liên kết

        // Gửi form bằng cách sử dụng phương thức submit() của form
        myForm.submit();
    }

    // Gán hàm xử lý cho sự kiện click trên nút <a>
    submitButton.addEventListener('click', handleSubmit);
</script>