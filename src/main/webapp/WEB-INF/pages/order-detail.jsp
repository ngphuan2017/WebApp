<%-- 
    Document   : order-detail
    Created on : Sep 9, 2023, 1:31:58 PM
    Author     : phuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url value="/api/orders/deleted" var="endpoint" />
<c:url value="/products" var="producted" />

<h1 class="text-center text-success mt-4 mb-4" data-aos="flip-down"><i class="fa-solid fa-bag-shopping"></i> CHI TIẾT ĐƠN HÀNG</h1>

<c:if test="${orders != null}">
    <div class="container">
        <table class="table" data-aos="fade-left">
            <thead>
                <tr>
                    <th></th>
                    <th>Sản phẩm</th>
                    <th>Tên sản phẩm</th>
                    <th>Đơn giá</th>
                    <th>Số lượng</th>
                    <th>Số tiền</th>
                    <th>Trạng thái</th>
                    <th>Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <c:set var="totalQuantity" value="0" />
                <c:forEach items="${orders}" var="o">
                    <tr id="order${o.id}">
                        <td>
                            <a href="${producted}/${o.productId.id}"><i class="fa-solid fa-eye"></i></a>
                        </td>
                        <td>
                            <img src="${o.productId.image}" width="33px" height="33px" />
                        </td>
                        <td>${o.productId.name}</td>
                        <td class="currency">
                            <span class="money">${o.price} VNĐ</span>
                        </td>
                        <td>${o.number}</td>
                        <td class="currency">
                            <span class="money" id="total${o.id}" style="color: #ee4d2d;">
                                ${o.price*o.number}
                            </span> <i class="fa-solid fa-coins" style="color: #ffdd10;"></i>
                        </td>
                        <td id="order-status${o.id}">${o.orderstatus.statusname}</td>
                        <td id="order-button${o.id}">
                            <c:choose>
                                <c:when test="${o.orderstatus.id == 9}">
                                    <button class="btn btn-outline-danger" onclick="deleteItemOrder('${endpoint}/${o.id}', ${o.id}, ${o.number}, ${o.price*o.number})">Hủy đơn</button>
                                </c:when>
                                <c:otherwise>
                                    <button class="btn btn-outline-secondary" disabled>Hủy đơn</button>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    <c:choose>
                        <c:when test="${o.orderstatus.id != 12}">
                            <c:set var="totalAmount" value="${o.orderId.amount}" />
                            <c:set var="totalQuantity" value="${totalQuantity + o.number}" />
                        </c:when>
                    </c:choose>
                </c:forEach>
            </tbody>
        </table>

        <div class="container alert alert-info text-center" data-aos="zoom-in">
            <h6>Tổng số lượng: <span style="color: #ee4d2d; font-size: 20px; font-weight: 600;" id="total-quantity">${totalQuantity}</span> sản phẩm</h6>
            <h5 class="currency">Tổng thanh toán: <span style="color: #ee4d2d; font-size: 25px; font-weight: 700;" class="money" id="total-amount">${totalAmount}</span> VNĐ</h5>
        </div>
    </div>
</c:if>