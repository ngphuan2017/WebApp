<%-- 
    Document   : product-detail
    Created on : Mar 18, 2023, 1:10:53 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<h1 class="text-center text-info">CHI TIẾT SẢN PHẨM</h1>
<div class="row">
    <div class="col-md-5 col-xs-12">
        <img src="${product.image}" class="img-fluid" />
    </div>
    <div class="col-md-7 col-xs-12">
        <h1 class="text-danger">${product.name}</h1>
        <h2>${product.price}</h2>
        <p class="text-success">${product.description}</p>
    </div>
</div>
<hr />

<c:url value="/api/products/${product.id}/comments" var="url" />
<div>
    <textarea class="form-control" rows="5" id="content-comment" name="content"></textarea>
    <input type="button" value="Them binh luan" onclick="addComment('${url}')" class="btn btn-danger m-1" />
</div>
<div class="spinner-grow text-primary spinner"></div>
<div id="comments">
    
</div>



<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.4/moment.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.4/moment-with-locales.min.js"></script>
<script src="<c:url value="/js/comment.js" />"></script>
<script>
    window.onload = function() {
        loadComments('${url}');
    };
</script>
