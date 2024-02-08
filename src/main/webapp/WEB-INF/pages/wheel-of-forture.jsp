<%-- 
    Document   : wheel-of-forture
    Created on : Nov 3, 2023, 2:15:09 AM
    Author     : phuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="se" uri="http://www.springframework.org/security/tags" %>

<c:url value="/api/wheel-of-forture" var="wheel"/>

<div class="bg">
    <a id="api-wheel-of-forture" href="${wheel}" class="d-none"></a>
    <i id="current-user-id" class="d-none">${currentUser.id}</i>
    <div class="wrapper" id="wrapper">
        <section id="luckywheel" class="hc-luckywheel">
            <div class="hc-luckywheel-container">
                <canvas class="hc-luckywheel-canvas" width="500px" height="500px"
                >Vòng Xoay May Mắn
                </canvas
                >
            </div>
            <se:authorize access="isAuthenticated()">
                <a class="hc-luckywheel-btn" href="javascript:;">Xoay</a>
            </se:authorize>
            <se:authorize access="isAnonymous()">
                <a class="hc-luckywheel-btn disabled" href="javascript:;">Xoay</a>
            </se:authorize>
        </section>
        <se:authorize access="isAuthenticated()">
            <div class="quantity-luckywheel text-center">
                <span class="text-white">Số lượt quay<p id="number-turn" style="font-size: 25px;">5</p></span>
            </div>
        </se:authorize>
        <se:authorize access="isAnonymous()">
            <div class="alert alert-danger quantity-luckywheel">
                <strong>Thông Báo!</strong> Vui lòng đăng nhập để nhận lượt quay.
            </div>
        </se:authorize>
    </div>
</div>

<script src="<c:url value="/resources/wheel-of-forture/wheel-of-forture.js" />"></script>