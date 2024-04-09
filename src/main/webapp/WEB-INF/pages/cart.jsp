<%-- 
    Document   : cart
    Created on : Apr 1, 2023, 1:14:34 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:url value="/" var="action" />
<c:url value="/me/profile" var="profile"/>
<c:url value="/api/cart" var="endpoint" />
<c:url value="/api/cart/voucher" var="voucher" />
<c:url value="/api/products" var="producted" />
<c:url value="/login" var="loginUrl" />
<c:url value="/api/pay" var="pUrl" />

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
                    <tr id="cart${c.id}">
                        <td>
                            <a class="js-add-cart" href="javascript:;" onclick="productDetailView('${producted}/${c.id}')"><i class="fa-solid fa-eye"></i></a>
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
                                   onblur="updateItem('${endpoint}/${c.id}', this, ${c.id}, ${c.price})"
                                   class="form-control" />
                        </td>
                        <td class="currency">
                            <span class="money" id="total${c.id}" style="color: #ee4d2d;">
                                ${(c.price*c.quantity)}
                            </span> <i class="fa-solid fa-coins" style="color: #ffdd10;"></i>
                        </td>
                        <td>
                            <button class="btn btn-danger" onclick="deleteItem('${endpoint}/${c.id}', ${c.id})">Xóa</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table> 

        <div class="container alert alert-info text-center" data-aos="zoom-in">
            <h6>Tổng số lượng: <span style="color: #ee4d2d; font-size: 20px; font-weight: 600;" class="cart-counter" id="total-quantity">${cartStats.totalQuantity}</span> sản phẩm</h6>
            <h6 class="currency">Tổng tiền: <span style="color: #ee4d2d; font-size: 20px; font-weight: 600;" class="cart-amount money">${cartStats.totalAmount}</span> VNĐ</h6>
            <span class="d-none" id="d-voucher-discount"></span>
            <h7>Voucher giảm giá: <span style="color: #ee4d2d; font-size: 18px; font-weight: 600;" id="voucher-discount">0</span> VNĐ</h7>
            <span class="d-none" id="d-total-price">${cartStats.totalAmount}</span>
            <h5 class="currency">Tổng thanh toán: <span style="color: #ee4d2d; font-size: 25px; font-weight: 700;" class="cart-amount money" id="total-price">${cartStats.totalAmount}</span> VNĐ</h5>
        </div>
        <label for="voucher">Mã Voucher:</label>
        <input type="text" class="form-control text-center" id="voucher" onchange="checkVoucherCode('${voucher}')" placeholder="Nhập Mã Voucher..." />
        <div class="alert alert-secondary text-center text-danger container d-none" id="voucher-content"></div>
        <label for="option-pay" class="form-labe">Phương thức thanh toán:</label>
        <select class="form-select text-center" name="option-pay" id="option-pay">
            <option value="1">Thanh toán khi nhận hàng</option>
            <option value="2">Cổng thanh toán VNPay</option>
            <option value="3">Ví điện tử Momo</option>
            <option value="4" disabled>Chuyển khoản ngân hàng <span>(Đang bảo trì)</span></option>
        </select>
        <div class="d-flex justify-content-center mt-4 mb-4">
            <c:if test="${pageContext.request.userPrincipal.name == null}">
                <p style="font-size: 16px;">Vui lòng <a href="${loginUrl}">đăng nhập</a> để thanh toán!</p>
            </c:if>
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <c:set var="address" value="${pageContext.session.getAttribute('currentUser').address}" />
                <c:set var="phone" value="${pageContext.session.getAttribute('currentUser').phone}" />
                <c:choose>
                    <c:when test="${address != null && not empty address && fn:contains(address, '-') && phone != null && not empty phone}">
                        <c:set var="parts" value="${fn:split(address, '-')}" />
                        <c:if test="${fn:length(parts) == 3}">
                            <c:set var="part1" value="${fn:trim(parts[0])}" />
                            <c:set var="part2" value="${fn:trim(parts[1])}" />
                            <c:set var="part3" value="${fn:trim(parts[2])}" />
                            <c:if test="${not empty part1 && not empty part2 && not empty part3}">
                                <a href="javascript:;" class="animated-button1" onclick="pay('${pUrl}')">
                                    <span></span>
                                    <span></span>
                                    <span></span>
                                    <span></span>
                                    Đặt Hàng
                                </a>
                            </c:if>
                            <c:if test="${empty part1 || empty part2 || empty part3}">
                                <p style="font-size: 16px;">Vui lòng cập nhật đầy đủ <a href="${profile}">thông tin liên hệ</a> để đặt hàng!</p>
                            </c:if>
                        </c:if>
                        <c:if test="${fn:length(parts) != 3}">
                            <p style="font-size: 16px;">Vui lòng cập nhật đầy đủ <a href="${profile}">thông tin liên hệ</a> để đặt hàng!</p>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <p style="font-size: 16px;">Vui lòng cập nhật đầy đủ <a href="${profile}">thông tin liên hệ</a> để đặt hàng!</p>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </div>
    </div>
</c:if>
<div class="js-modal">
    <div class="modal-container-black js-modal-container">
        <div class="js-modal-close">x</div>
        <header class="modal-header-black">
            <span><i class="fa-solid fa-address-card"></i> Thông tin</span>
        </header>
        <div class="modal-body">
            <div class="row">
                <div class="col-lg-4 col-md-4 col-sm-6 col-12">
                    <div class="modal-img-black" id="modal-account-img">

                    </div>
                </div>
                <div class="col-lg-7 col-md-7 col-sm-6 col-12">
                    <div class="modal-content" id="modal-account-about">

                    </div>
                </div>
            </div>
        </div>
        <div class="footer-modal-black" id="modal-account-title">

        </div>
    </div>
</div>