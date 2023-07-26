<%-- 
    Document   : admin-sidebar
    Created on : Jul 19, 2023, 5:34:14 PM
    Author     : phuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-dark align-items-start sidebar sidebar-dark accordion bg-gradient-primary p-0">
  <div class="container-fluid d-flex flex-column p-0"><a class="navbar-brand d-flex justify-content-center align-items-center sidebar-brand m-0" href="<c:url value="/admin" />">
    <div class="sidebar-brand-icon rotate-n-15"><i class="fas fa-laugh-wink"></i></div>
    <div class="sidebar-brand-text mx-3"><span>Phú An Shop</span></div>
  </a>
    <hr class="sidebar-divider my-0">
    <ul class="navbar-nav text-light" id="accordionSidebar">
      <li class="nav-item"><a class="nav-link active" href="<c:url value="/admin" />"><i class="fas fa-tachometer-alt"></i><span>Administrator</span></a></li>
      <li class="nav-item"><a class="nav-link" href="<c:url value="/profile" />"><i class="fas fa-user"></i><span>Profile</span></a></li>
      <li class="nav-item"><a class="nav-link" href="<c:url value="/table" />"><i class="fas fa-table"></i><span>Table</span></a></li>
      <li class="nav-item"><a class="nav-link" href="<c:url value="/login" />"><i class="far fa-user-circle"></i><span>Login</span></a></li>
      <li class="nav-item"><a class="nav-link" href="<c:url value="/register" />"><i class="fas fa-user-circle"></i><span>Register</span></a></li>
    </ul>
    <div class="text-center d-none d-md-inline"><button class="btn rounded-circle border-0" id="sidebarToggle" type="button"></button></div>
  </div>
</nav>
