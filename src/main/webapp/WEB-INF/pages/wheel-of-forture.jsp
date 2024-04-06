<%-- 
    Document   : wheel-of-forture
    Created on : Nov 3, 2023, 2:15:09 AM
    Author     : phuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="se" uri="http://www.springframework.org/security/tags" %>

<c:url value="/api/wheel-of-forture" var="wheel"/>
<c:url value="/api/wheel-of-forture/add/${currentUser.id}" var="addWheel"/>
<c:url value="/api/users/wheel" var="wheelValue"/>

<div class="bg">
    <a id="api-wheel-of-forture" href="${wheel}" class="d-none"></a>
    <a id="api-wheel-of-forture-added" href="${addWheel}" class="d-none"></a>
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
                <c:choose>
                    <c:when test="${currentUser.wheel > 0}">
                        <a class="hc-luckywheel-btn" href="javascript:;" onclick="setWheelValue('${wheelValue}/${currentUser.id}')">Xoay</a>
                    </c:when>
                    <c:otherwise>
                        <a class="hc-luckywheel-btn disabled" href="javascript:;">Xoay</a>
                    </c:otherwise>
                </c:choose>
            </se:authorize>
            <se:authorize access="isAnonymous()">
                <a class="hc-luckywheel-btn disabled" href="javascript:;">Xoay</a>
            </se:authorize>
        </section>
        <se:authorize access="isAuthenticated()">
            <div class="quantity-luckywheel text-center">
                <span class="text-white">Số lượt quay<p id="number-turn" style="font-size: 25px;">${currentUser.wheel}</p></span>
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