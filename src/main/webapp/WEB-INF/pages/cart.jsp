<%-- 
    Document   : cart
    Created on : Apr 1, 2023, 1:14:34 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<h1 class="text-center text-success">GIỎ HÀNG</h1>

<c:if test="${carts != null}">
    
    <table class="table">
        <tr>
            <th>Mã sản phẩm</th>
            <th>Tên sản phẩm</th>
            <th>Đơn giá</th>
            <th>Số lượng</th>
            <th></th>
        </tr>
        <c:forEach items="${carts.values()}" var="c">
            <c:url value="/api/cart/${c.id}" var="endpoint" />
            <tr id="cart${c.id}">
                <td>${c.id}</td>
                <td>${c.name}</td>
                <td>${String.format("%,.0f", Double.parseDouble(c.price))} VNĐ</td>
                <td>
                    <input type="number" value="${c.quantity}"
                           onblur="updateItem('${endpoint}', this)"
                           class="form-control" />
                </td>
                <td>
                    
                    <button class="btn btn-danger" onclick="deleteItem('${endpoint}', ${c.id})">Xóa</button>
                </td>
            </tr>
        </c:forEach>
    </table> 
    
    <div class="alert alert-info">
        <h4>Tổng số sản phẩm: <span class="cart-counter">${cartStats.totalQuantity}</span></h4>
        <h4>Tổng số tiền: <span class="cart-amount">${String.format("%,.0f", Double.parseDouble(cartStats.totalAmount))}</span> VNĐ</h4>
    </div>

    <div>
         <c:choose>
            <c:when test="${pageContext.request.userPrincipal.name == null}">
                <c:url value="/login" var="loginUrl" />
                <p>Vui lòng <a href="${loginUrl}">đăng nhập</a> để thanh toán!</p>
            </c:when>
            <c:when test="${pageContext.request.userPrincipal.name != null}">
                <c:url value="/api/pay" var="pUrl" />
                <input type="button" onclick="pay('${pUrl}')" value="Thanh toán" class="btn btn-success" />
            </c:when>
        </c:choose>
        
    </div>
</c:if>

<script src="<c:url value="/js/cart.js" />"></script>
<script>
    window.onload = function() {
        
    }
</script>
