<%-- 
    Document   : admin-sidebar
    Created on : Jul 19, 2023, 5:34:14 PM
    Author     : phuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:url value="/admin" var="admin" />
<c:url value="/admin/customer-management" var="customer" />
<c:url value="/admin/order-management" var="order" />
<c:url value="/admin/orders-detail-management" var="orderDetail" />
<c:url value="/admin/product-management" var="product" />
<c:url value="/admin/promotion-management" var="promotion" />
<c:url value="/admin/category-management" var="category" />
<c:url value="/admin/comment-management" var="comment" />
<c:url value="/admin/report-management" var="report" />

<nav class="navbar navbar-dark align-items-start sidebar sidebar-dark accordion bg-gradient-primary p-0" style="z-index: 2;">
    <div style="position: fixed;">
        <div class="container-fluid d-flex flex-column p-0">
            <ul class="navbar-nav text-light" id="accordionSidebar">
                <li class="nav-item"><a class="nav-link ${sidebar == 'admin' ? 'active' : ''}" href="${admin}"><i class="fas fa-home"></i><span>Trang chủ quản trị</span></a></li>
                <li class="nav-item"><a class="nav-link ${sidebar == 'order' ? 'active' : ''}" href="${order}"><i class="fas fa-shopping-cart"></i><span>Quản lý đơn hàng</span></a></li>
                <li class="nav-item"><a class="nav-link ${sidebar == 'orderDetail' ? 'active' : ''}" href="${orderDetail}"><i class="fas fa-shopping-bag"></i><span>Quản lý các kiện hàng</span></a></li>
                <li class="nav-item"><a class="nav-link ${sidebar == 'customer' ? 'active' : ''}" href="${customer}"><i class="fas fa-user"></i><span>Quản lý tài khoản</span></a></li>
                <li class="nav-item"><a class="nav-link ${sidebar == 'promotion' ? 'active' : ''}" href="${promotion}"><i class="fas fa-gift"></i><span>Quản lý khuyến mãi</span></a></li>
                <li class="nav-item"><a class="nav-link ${sidebar == 'product' ? 'active' : ''}" href="${product}"><i class="fas fa-list"></i><span>Quản lý sản phẩm</span></a></li>
                <li class="nav-item"><a class="nav-link ${sidebar == 'category' ? 'active' : ''}" href="${category}"><i class="fas fa-table"></i><span>Phân loại sản phẩm</span></a></li>
                <li class="nav-item"><a class="nav-link ${sidebar == 'comment' ? 'active' : ''}" href="${comment}"><i class="fas fa-comment-dots"></i><span>Quản lý bình luận</span></a></li>
                <li class="nav-item"><a class="nav-link ${sidebar == 'report' ? 'active' : ''}" href="${report}"><i class="fas fa-flag"></i><span>Quản lý yêu cầu</span></a></li>
            </ul>
            <div class="text-center d-none d-md-inline"><button class="btn rounded-circle border-0" id="sidebarToggle" type="button"></button></div>
        </div>
    </div>
</nav>
