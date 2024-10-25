<%-- 
    Document   : header
    Created on : Jul 19, 2023, 5:36:45 PM
    Author     : phuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="se" uri="http://www.springframework.org/security/tags" %>


<c:url value="/" var="action"/>
<c:url value="/about" var="about"/>
<c:url value="/wheel-of-forture" var="wheel"/>
<c:url value="/me/profile" var="profile"/>
<c:url value="/me/notification" var="notification"/>
<c:url value="/forgot-password/change-password" var="changePassword"/>
<c:url value="/cart" var="cart"/>
<c:url value="/login" var="login"/>
<c:url value="/logout" var="logout"/>
<c:url value="/admin" var="admin"/>
<header>
    <div class="shop d-flex justify-content-between align-items-center">
        <div class="logo">
            <a href="${action}" title="Phú An Shop" rel="home">
                <img src="<c:url value="https://res.cloudinary.com/dkmug1913/image/upload/v1687075830/WebApp/logo_km2dfc.png" />"
                     alt="Phú An Shop"/>
            </a>
        </div>
        <div class="search flex-grow-1 mx-3">
            <form class="form-inline d-flex justify-content-center align-items-center w-75" action="${action}" title="Tìm kiếm">
                <input id="text-search" name="kw" type="search" class="form-control" placeholder="Nhập sản phẩm bạn muốn tìm kiếm" />
                <button id="btn-search" type="submit" class="btn btn-outline-primary">
                    <i class="fa-solid fa-magnifying-glass"></i>
                </button>
            </form>
        </div>
        <div class="language-selection">
            <div class="dropdown">
                <button class="btn btn-light dropdown-toggle d-flex align-items-center" type="button" id="languageDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                    <img id="selectedFlag" src="https://flagcdn.com/w20/vn.png" alt="Tiếng Việt" class="me-2">
                    <span id="selectedLanguage">Tiếng Việt</span>
                </button>
                <ul class="dropdown-menu" aria-labelledby="languageDropdown">
                    <li>
                        <a class="dropdown-item d-flex align-items-center" href="javascript:;" onclick="changeLanguage('vi')">
                            <img src="https://flagcdn.com/w20/vn.png" alt="Tiếng Việt" class="me-2">
                            Tiếng Việt
                            <span class="check-icon ms-auto" id="check-vi" style="display: none;">✔️</span>
                        </a>
                    </li>
                    <li>
                        <a class="dropdown-item d-flex align-items-center" href="javascript:;" onclick="changeLanguage('en')">
                            <img src="https://flagcdn.com/w20/gb.png" alt="English" class="me-2">
                            English
                            <span class="check-icon ms-auto" id="check-en" style="display: none;">✔️</span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </div>

    <nav id="nav" class="navbar navbar-expand-sm">
        <div class="container-fluid">
            <a class="navbar-brands" href="${action}" title="Trang chủ"><i class="fa-solid fa-house"></i> Trang chủ</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="collapsibleNavbar">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="${about}" title="Giới thiệu">Giới thiệu</a>
                    </li>
                    <c:forEach items="${categories}" var="c">
                        <li class="nav-item dropdown" onmouseover="showDropdownMenu(this)"
                            onmouseout="hideDropdownMenu(this)">
                            <a class="nav-link dropdown-toggle" href="javascript:;"
                               data-bs-toggle="dropdown" title="${c.name}">${c.name}</a>
                            <ul class="dropdown-menu">
                                <c:forEach items="${categorySub}" var="s">
                                    <c:url value="/" var="url">
                                        <c:param name="categorysubId" value="${s.id}"/>
                                    </c:url>
                                    <c:choose>
                                        <c:when test="${s.categoryId.id == c.id && s.id != 14}">
                                            <li><a class="dropdown-item" href="${url}" title="${s.name}">${s.name}</a></li>
                                            </c:when>
                                            <c:when test="${s.categoryId.id == c.id && s.id == 14}">
                                            <li><a class="dropdown-item" href="${wheel}" title="${s.name}">${s.name}</a></li>
                                            </c:when>
                                        </c:choose>
                                    </c:forEach>
                            </ul>
                        </li>
                    </c:forEach>
                    <li class="nav-item">
                        <a class="nav-link" href="${cart}"><i class="fa-solid fa-cart-shopping" title="Giỏ hàng"></i> Giỏ hàng <span
                                class="badge bg-danger cart-quantity">${cartStats.totalQuantity}</span></a>
                    </li>
                    <c:choose>
                        <c:when test="${pageContext.request.userPrincipal.name == null}">
                            <li class="nav-item">
                                <a class="nav-link text-primary" href="${login}" title="Đăng nhập"><i class="fa-solid fa-user"></i> Đăng nhập</a>
                            </li>
                        </c:when>
                        <c:when test="${pageContext.request.userPrincipal.name != null}">
                            <li class="nav-item dropdown" onmouseover="showDropdownMenu(this)"
                                onmouseout="hideDropdownMenu(this)">
                                <a class="nav-link text-success" href="javascript:;" data-bs-toggle="dropdown">
                                    <span id="badge-position" class="${sessionScope.currentUser.notification > 0 ? '' : 'd-none'} position-absolute top-0 start-100 translate-middle p-2 bg-danger border border-light rounded-circle"></span>
                                    <div class="position-relative float-start">
                                        <img width="30px" height="30px"
                                         src="${sessionScope.currentUser.avatar}"/>
                                        <img class="avatar-frame" width="30px" height="30px"
                                         src="${sessionScope.currentUser.avatarFrame.url}"/>
                                    </div>
                                    <span>&nbsp;${sessionScope.currentUser.fullname}</span>
                                </a>
                                <ul class="dropdown-menu">
                                    <li><a class="dropdown-item" href="${profile}"><i class="fa-solid fa-user"></i> Tài
                                            khoản</a></li>
                                    <li><a class="dropdown-item" href="${notification}"
                                           onclick="setNotification(0, '0')"><i
                                                class="fa-solid fa-bell"></i>
                                            Thông báo <span
                                                class="badge bg-danger"
                                                id="session-notification">${sessionScope.currentUser.notification}</span></a>
                                    </li>
                                    <li><a class="dropdown-item" href="${changePassword}"><i
                                                class="fa-solid fa-unlock-keyhole"></i> Đổi mật khẩu</a></li>
                                    <li><a class="dropdown-item" href="${logout}"><i
                                                class="fa-solid fa-right-from-bracket"></i> Đăng xuất</a></li>
                                </ul>
                            </li>
                        </c:when>
                    </c:choose>
                    <se:authorize access="hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')">
                        <li class="nav-item">
                            <a id="admin" style="font-size: 12px;" class="nav-link" href="${admin}" title="Admin">Admin</a>
                        </li>
                    </se:authorize>
                </ul>
                <form class="d-flex" action="${action}" title="Tìm kiếm">
                    <div class="input-group">
                        <button class="btn btn-outline-none" type="button" id="searchToggle"><i
                                class="fa-solid fa-magnifying-glass" style="color: white;"></i></button>
                        <input class="form-control" name="kw" type="search" placeholder="Search" id="searchInput"
                               style="display: none;">
                    </div>
                </form>
            </div>
        </div>
    </nav>
</header>