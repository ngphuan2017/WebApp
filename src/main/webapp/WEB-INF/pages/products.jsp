<%-- 
    Document   : products
    Created on : Mar 18, 2023, 1:45:57 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<h1 class="text-info text-center">QUẢN TRỊ SẢN PHẨM</h1>

<c:if test="${errMsg != null}">
    <div class="alert alert-danger">${errMsg}</div>
</c:if>
<c:url value="/admin/products" var="action" />
<form:form method="POST" action="${action}"
           modelAttribute="product" enctype="multipart/form-data">
    
    <form:errors path="*" element="div" cssClass="alert alert-danger" />
    <div class="form-floating mb-3 mt-3">
        <form:input class="form-control" id="name" placeholder="Tên sản phẩm" path="name" name="name" />
        <label for="name">Tên sản phẩm</label>
    </div>
    <div class="form-floating">
        <textarea class="form-control" id="description" path="description" name="description" placeholder="Mô tả">${product.description}</textarea>
        <label for="description">Mô tả</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input type="number" class="form-control" id="price" placeholder="Giá sản phẩm" path="price" name="price" />
        <label for="price">Giá sản phẩm</label>
    </div>
    <div class="form-floating">
        <form:select class="form-select" id="categoryId" name="categoryId" path="categoryId">
            <c:forEach items="${categories}" var="c">
                <c:choose>
                    <c:when test="${product.categoryId.id == c.id}">
                        <option value="${c.id}" selected>${c.name}</option>
                    </c:when>
                    <c:otherwise>
                         <option value="${c.id}">${c.name}</option>
                    </c:otherwise>
                </c:choose>
               
            </c:forEach>
        </form:select>
        <label for="categoryId" class="form-label">Danh mục sản phẩm:</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input type="file" class="form-control" id="file" path="file" name="file" />
        <label for="file">Ảnh sản phẩm</label>
    </div>
    <c:if test="${product.image != null && product.image != ''}">
        <div class="form-floating mb-3 mt-3">
        <img src="${product.image}" width="120" />
        </div>
    </c:if>
    <div class="form-floating mt-2">
        <c:choose>
            <c:when test="${product.id > 0}">
                <form:hidden path="id" />
                <form:hidden path="image" />
                <input type="submit" value="Cập nhật sản phẩm" class="btn btn-success" />
            </c:when>
            <c:otherwise>
               <input type="submit" value="Thêm sản phẩm" class="btn btn-success" />
            </c:otherwise>
        </c:choose>
        
    </div>
</form:form>

<table class="table">
    <tr>
        <th></th>
        <th>Id</th>
        <th>Tên sản phẩm</th>
        <th>Gía sản phẩm</th>
        <th></th>
    </tr>
    <c:forEach items="${products}" var="p">
        <tr id="product${p.id}">
            <td>
                <img src="${p.image}" width="180" />
            </td>
            <td>${p.id}</td>
            <td>${p.name}</td>
            <td>${p.price} VNĐ</td>
            <td>
                <div id="spinner${p.id}" style="display:none" class="spinner-border text-primary"></div>
                <c:url value="/api/products/${p.id}" var="endpoint" />
                <input  type="button" onclick="deleteProduct('${endpoint}', ${p.id})" value="Xóa" class="btn btn-danger" />
                <a href="<c:url value="/admin/products/${p.id}" />" class="btn btn-info">Cập nhật</a>
            </td>
        </tr>
    </c:forEach>
</table>

<script src="<c:url value="/js/product.js" />"></script>
