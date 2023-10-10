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
<c:url value="/admin/product-management" var="product" />

<nav class="navbar navbar-dark align-items-start sidebar sidebar-dark accordion bg-gradient-primary p-0">
    <div class="container-fluid d-flex flex-column p-0"><a class="navbar-brand d-flex justify-content-center align-items-center sidebar-brand m-0" href="<c:url value="/" />">
            <div class="sidebar-brand-icon rotate-n-15"><i class="fas fa-laugh-wink"></i></div>
            <div class="sidebar-brand-text mx-3"><span>Phú An Shop</span></div>
        </a>
        <hr class="sidebar-divider my-0">
        <ul class="navbar-nav text-light" id="accordionSidebar">
            <li class="nav-item"><a class="nav-link ${sidebar == 'admin' ? 'active' : ''}" href="${admin}"><i class="fas fa-tachometer-alt"></i><span>Bảng điều khiển</span></a></li>
            <li class="nav-item"><a class="nav-link ${sidebar == 'order' ? 'active' : ''}" href="${order}"><i class="fas fa-clipboard-list"></i><span>Quản lý đơn hàng</span></a></li>
            <li class="nav-item"><a class="nav-link ${sidebar == 'customer' ? 'active' : ''}" href="${customer}"><i class="fas fa-user"></i><span>Quản lý khách hàng</span></a></li>
            <li class="nav-item"><a class="nav-link" href="${customer}"><i class="fas fa-table"></i><span>Combo khuyến mãi</span></a></li>
            <li class="nav-item"><a class="nav-link ${sidebar == 'product' ? 'active' : ''}" href="${product}"><i class="fas fa-table"></i><span>Quản lý sản phẩm</span></a></li>
            <li class="nav-item"><a class="nav-link" href="${customer}"><i class="fas fa-table"></i><span>Loại sản phẩm</span></a></li>
            <li class="nav-item"><a class="nav-link" href="${customer}"><i class="fas fa-table"></i><span>Quản lý yêu cầu</span></a></li>
        </ul>
        <div class="text-center d-none d-md-inline"><button class="btn rounded-circle border-0" id="sidebarToggle" type="button"></button></div>
    </div>
</nav>
