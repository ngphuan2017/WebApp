<%-- 
    Document   : blank-base
    Created on : Jul 19, 2023, 5:35:53 PM
    Author     : phuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>
            <tiles:insertAttribute name="title"/>
        </title>
        <link href="<c:url value="https://res.cloudinary.com/dkmug1913/image/upload/v1687075781/WebApp/iconshop_uxsy3a.png" />" rel="shortcut icon"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
        <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Open+Sans" />
        <link href="<c:url value="/resources/css/style.css"/> " rel="stylesheet"/>
        <link rel="stylesheet" href="<c:url value="/resources/css/login.css"/> ">
        <link rel="stylesheet" href="<c:url value="/resources/css/register.css"/> ">
        <script src="<c:url value="/resources/jquery-3.6.1/jquery-3.6.1.min.js" />"></script>
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
    </head>
    <body>
        <tiles:insertAttribute name="content"/>
        <footer class="bg-white">
            <div class="container my-auto">
                <div style="text-align: center;" class="my-auto copyright"><span>Copyright © ANITSHOP 2023</span></div>
            </div>
        </footer>
    </body>
</html>
