<%-- 
    Document   : base
    Created on : Jul 19, 2023, 5:35:15 PM
    Author     : phuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>
            <tiles:insertAttribute name="title"/>
        </title>
        <link href="<c:url value="https://res.cloudinary.com/dkmug1913/image/upload/v1687075781/WebApp/iconshop_uxsy3a.png" />" rel="shortcut icon"/>
        <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet"/>
        <link href="<c:url value="/resources/css/user.css" />" rel="stylesheet"/>
        <link href="<c:url value="/resources/css/responsive.css" />" rel="stylesheet"/>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="stylesheet"/>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet"/>
        <link href="<c:url value="/resources/bootstrap-5.3.2-dist/css/bootstrap.min.css" />" rel="stylesheet">
        <link href="<c:url value="/resources/aos-2.3.4-dist/css/aos.css" />" rel="stylesheet">
        <script src="<c:url value="/resources/aos-2.3.4-dist/js/aos.js" />"></script>
        <script src="<c:url value="/resources/bootstrap-5.3.2-dist/js/bootstrap.bundle.min.js" />"></script>
        <script src="<c:url value="/resources/jquery-3.6.1/jquery-3.6.1.js" />"></script>
        <script src="<c:url value="/resources/js/moment.min.js" />"></script>
        <script src="<c:url value="/resources/js/moment-with-locales.min.js" />"></script>
        <script src="<c:url value="/resources/sweetalert2-10/sweetalert2-10.js" />"></script>
    </head>
    <body>
        <div id="main">
            <tiles:insertAttribute name="header"/>
            <tiles:insertAttribute name="content"/>
            <tiles:insertAttribute name="footer"/>
        </div>
        <a id="backtop" href="#main" class="gototop fa-solid fa-circle-chevron-up" style="display: none;"></a>

        <script src="<c:url value="/resources/js/product.js" />"></script>
        <script src="<c:url value="/resources/js/cart.js" />"></script>
        <script src="<c:url value="/resources/js/orders.js" />"></script>
        <script src="<c:url value="/resources/js/comment.js" />"></script>
        <script src="<c:url value="/resources/js/stats.js" />"></script>
        <script src="<c:url value="/resources/js/index.js" />"></script>
        <script src="<c:url value="/resources/popperjs-2.9.1/popper.min.js" />"></script>
    </body>
</html>
