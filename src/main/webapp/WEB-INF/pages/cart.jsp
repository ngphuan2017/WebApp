<%-- 
    Document   : cart
    Created on : Apr 1, 2023, 1:14:34 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:url value="/" var="action" />

<h1 class="text-center text-success mt-4 mb-4" data-aos="flip-down"><i class="fa-solid fa-cart-shopping"></i> GIỎ HÀNG</h1>

<c:if test="${carts == null || empty carts}">
    <div class="text-center m-2">
        <h3>"Hổng" có gì trong giỏ hết</h3>
        <p>Về trang cửa hàng để chọn mua sản phẩm bạn nhé!!!</p>
        <a class="btn btn-outline-danger" href="${action}">Mua sắm ngay</a>
    </div>
</c:if>
<c:if test="${carts != null && not empty carts}">
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
                    <th>Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${carts.values()}" var="c">
                    <c:url value="/api/cart/${c.id}" var="endpoint" />
                    <tr id="cart${c.id}">
                        <td>
                            <a href="<c:url value="/products/${c.id}"/>"><i class="fa-solid fa-eye"></i></a>
                        </td>
                        <td>
                            <img src="${c.image}" width="33px" height="33px" />
                        </td>
                        <td>${c.name}</td>
                        <td class="currency">
                            <span class="money">${c.price} VNĐ</span>
                        </td>
                        <td>
                            <i class="d-none" id="product-quantity-${c.id}-old">${c.quantity}</i>
                            <input type="number" value="${c.quantity}" id="product-quantity-${c.id}"
                                   onblur="updateItem('${endpoint}', this, ${c.id}, ${c.price})"
                                   class="form-control" />
                        </td>
                        <td class="currency">
                            <span class="money" id="total${c.id}" style="color: #ee4d2d;">
                                ${(c.price*c.quantity)}
                            </span> <i class="fa-solid fa-coins" style="color: #ffdd10;"></i>
                        </td>
                        <td>
                            <button class="btn btn-danger" onclick="deleteItem('${endpoint}', ${c.id})">Xóa</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table> 

        <div class="container alert alert-info text-center" data-aos="zoom-in">
            <h6>Tổng số lượng: <span style="color: #ee4d2d; font-size: 20px; font-weight: 600;" class="cart-counter" id="total-quantity">${cartStats.totalQuantity}</span> sản phẩm</h6>
            <h5 class="currency">Tổng thanh toán: <span style="color: #ee4d2d; font-size: 25px; font-weight: 700;" class="cart-amount money">${cartStats.totalAmount}</span> VNĐ</h5>
        </div>
        <input type="text" class="form-control text-center" placeholder="Mã Voucher" />
        <label for="optionPay" class="form-labe">Phương thức thanh toán:</label>
        <select class="form-select text-center" name="optionPay" id="option-pay">
            <option value="1">Thanh toán khi nhận hàng</option>
            <option value="2">Cổng thanh toán VNPay</option>
            <option value="3" disabled>Chuyển khoản ngân hàng <span>(Đang bảo trì)</span></option>
            <option value="4" disabled>Ví điện tử Momo <span>(Đang bảo trì)</span></option>
        </select>
        <div class="d-flex justify-content-center mt-4 mb-4">
            <c:choose>
                <c:when test="${pageContext.request.userPrincipal.name == null}">
                    <c:url value="/login" var="loginUrl" />
                    <p style="font-size: 16px;">Vui lòng <a href="${loginUrl}">đăng nhập</a> để thanh toán!</p>
                </c:when>
                <c:when test="${pageContext.request.userPrincipal.name != null}">
                    <c:url value="/api/pay" var="pUrl" />
                    <a href="javascript:;" class="animated-button1" onclick="pay('${pUrl}')">
                        <span></span>
                        <span></span>
                        <span></span>
                        <span></span>
                        Đặt Hàng
                    </a>
                </c:when>
            </c:choose>
        </div>
    </div>
</c:if>