<%-- 
    Document   : admin-base
    Created on : Jul 19, 2023, 5:33:13 PM
    Author     : phuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><tiles:insertAttribute name="title"/></title>
    <link href="<c:url value="https://res.cloudinary.com/dkmug1913/image/upload/v1687075781/WebApp/iconshop_uxsy3a.png" />"
          rel="shortcut icon"/>
    <link href="<c:url value="/resources/css/loading.css" />" rel="stylesheet"/>
    <link rel="stylesheet" href="<c:url value="/resources/assets/bootstrap/css/bootstrap.min.css" />">
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i&amp;display=swap">
    <link rel="stylesheet" href="<c:url value="/resources/assets/fonts/fontawesome-all.min.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/css/admin.css" />">
    <script src="<c:url value="/resources/jquery-3.6.1/jquery-3.6.1.js" />"></script>
    <script src="<c:url value="/resources/js/moment.min.js" />"></script>
    <script src="<c:url value="/resources/sweetalert2-10/sweetalert2-10.js" />"></script>
</head>

<body id="page-top">

<div id="loading-animation" class="loadingio-spinner-double-ring-nkp2c2ew929">
    <div class="ldio-sy03huelm1">
        <div></div>
        <div></div>
        <div>
            <div></div>
        </div>
        <div>
            <div></div>
        </div>
    </div>
</div>

<div id="wrapper">
    <tiles:insertAttribute name="sidebar"/>
    <div class="d-flex flex-column" id="content-wrapper">
        <div id="content">
            <tiles:insertAttribute name="topbar"/>
            <div class="container-fluid" style="margin-top: 94px;">
                <tiles:insertAttribute name="content"/>
            </div>
        </div>
        <footer class="bg-white sticky-footer">
            <div class="container my-auto">
                <div class="text-center my-auto copyright"><span>Copyright © ANITSHOP 2023</span></div>
            </div>
        </footer>
    </div>
    <a class="border rounded d-inline scroll-to-top" href="#page-top"><i class="fas fa-angle-up"></i></a>
</div>
<script>
    window.onload = () => {
        let loading = document.getElementById('loading-animation');
        setTimeout(() => {
            loading.style.display = 'none';
        }, 1000);
    };
</script>
<script src="<c:url value="/resources/assets/bootstrap/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/resources/assets/js/chart.min.js" />"></script>
<script src="<c:url value="/resources/assets/js/bs-init.js" />"></script>
<script src="<c:url value="/resources/assets/js/theme.js" />"></script>
<script src="<c:url value="/resources/js/admin.js" />"></script>
</body>

</html>