<%-- 
    Document   : product-detail
    Created on : Mar 18, 2023, 1:10:53 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<c:url value="/api/cart" var="endpoint"/>
<c:url value="/api/products/${product.id}/rating" var="rating"/>
<c:url value="/api/products/${product.id}/comments" var="comment"/>
<c:url value="/api/products/${product.id}/comments/voted" var="voted"/>
<c:url value="/api/products/${product.id}/comments/report" var="report"/>
<c:url value="/api/products/${product.id}/comments/deleted" var="deleted"/>
<c:url value="/api/products/${product.id}/comments/changed" var="changed"/>
<c:url value="/api/users/level" var="leveled"/>
<c:url value="/login" var="loginUrl"/>

<div class="container">
    <div class="row" style="margin: 30px 0;">
        <div class="d-none" id="currentUserId">${pageContext.session.getAttribute("currentUser").id}</div>
        <div class="d-none" id="currentUserRole">${pageContext.session.getAttribute("currentUser").userRole.id}</div>
        <div class="col-lg-5 col-md-5 col-sm-5 col-12">
            <div style="padding: 15px;" data-aos="zoom-in">
                <img src="${product.image}" class="rounded" width="100%" max-height="450px" id="review-product"/>
            </div>
            <div class="row text-center">
                <div class="col-lg-3 col-md-3 col-sm-6 col-6 p-2">
                    <img src="${product.image}" class="img-thumbnail" onmouseover="reviewProduct(event)" style="border: 2px solid red;" width="90px;" height="90px;"/>
                </div>
                <c:forEach items="${imageList}" var="img">
                    <div class="col-lg-3 col-md-3 col-sm-6 col-6 p-2">
                        <img src="${img}" class="img-thumbnail" onmouseover="reviewProduct(event)" width="90px;" height="90px;"/>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="col-lg-7 col-md-7 col-sm-7 col-12 overflow-hidden" data-aos="fade-left">
            <div style="padding: 20px;">
                <div style="font-size: 24px; font-family: Arial, sans-serif;">
                    <span><i class="fa-solid fa-angles-right"></i> ${product.name}</span>
                </div>
                <div class="d-flex" style="margin-top: 20px;">
                    <div class="d-none" id="average-rating">${product.averageRating}</div>
                    <div class="line-padding" style="border-right: 1px solid #ccc;"><span class="line-bottom"
                                                                                          id="averageRating">${product.averageRating}</span>
                        <i class="fa-solid fa-star" style="color: orange;"></i></div>
                    <div class="line-padding" style="border-right: 1px solid #ccc;"><span class="line-bottom"
                                                                                          id="reviewCount">${product.reviewCount}</span>
                        Đánh Giá
                    </div>
                    <div class="line-padding"><span class="line-bottom">${product.unitsSold}</span> Đã Bán</div>
                </div>
                <div class="text-danger currency"
                     style="margin-top: 10px; padding: 15px 20px; font-size: 23px; background-color: #fafafa">
                    <del id="price-del" style="display: none; margin-right: 10px; font-size: 16px; color: #888;"><span
                            class="money" id="price-old">${product.price}</span>&#x20AB;
                    </del>
                    <span class="money" id="price-new"
                          style="font-size: 30px; font-weight: 600;">${product.price}</span>&#x20AB;
                    <div class="badge bg-danger" id="price-discount"
                         style="display: none; margin: 0 0 0 15px; padding: 3px 4px; font-size: 12px;"><span
                            id="discount">${product.discount.discount}</span>% GIẢM
                    </div>
                </div>
                <div>
                    <p class="text-success">${product.description}</p>
                </div>
                <div class="input-group">
                    <span class="m-2" style="color: #757575">Số Lượng</span>
                    <span class="input-group-btn">
                        <button type="button" style="padding: 9px;"
                                class="quantity-left-minus btn btn-outline-secondary btn-number" data-type="minus"
                                data-field="">
                            <i class="fa-solid fa-minus"></i>
                        </button>
                    </span>
                    <input type="number" id="quantity" name="quantity" class="form-control input-number text-center"
                           value="1" onblur="checkQuantity()" min="1" max="1000">
                    <span class="input-group-btn">
                        <button type="button" style="padding: 9px;"
                                class="quantity-right-plus btn btn-outline-secondary btn-number" data-type="plus"
                                data-field="">
                            <i class="fa-solid fa-plus"></i>
                        </button>
                    </span>
                    <span class="m-2" style="color: #757575">( ${product.quantity} sản phẩm có sẵn )</span>
                    <i class="d-none" id="product-quantity">${product.quantity}</i>
                </div>
                <div class="d-flex justify-content-center" style="margin-top: 20px;">
                    <button style="font-size: 16px; padding: 10px;" type="button" class="btn btn-outline-success"
                            onclick="addToCart('${endpoint}', ${product.id}, '${product.name}', ${product.discount.discount} > 0 ? ${product.price} * (1 - ${product.discount.discount} / 100) : ${product.price}, '${product.image}')">
                        <i class="fa-solid fa-cart-plus"></i> Thêm Vào Giỏ Hàng
                    </button>
                </div>
                <div class="d-flex justify-content-center text-center" style="margin-top: 20px;">
                    <span class="p-2"><i class="fa-solid fa-rotate-left" style="color: #d0011b"></i> 7 ngày miễn phí trả hàng</span>
                    <span class="p-2"><i class="fa-solid fa-shield" style="color: #d0011b"></i> Hàng chính hãng 100%</span>
                    <span class="p-2"><i class="fa-solid fa-truck-fast" style="color: #d0011b"></i> Miễn phí vận chuyển trong bán kính 3km</span>
                </div>
                <div class="d-flex justify-content-center" style="margin-top: 20px; font-size: 16px;">
                    <c:choose>
                        <c:when test="${pageContext.request.userPrincipal.name == null}">
                        </c:when>
                        <c:when test="${pageContext.request.userPrincipal.name != null}">
                            <span style="line-height: 46px; color: #999;">Đánh Giá Sản Phẩm</span>
                            <div class="rate">
                                <input type="radio" id="star5" name="rate" value="5"
                                       onclick="ratingStar('${rating}', this, ${pageContext.session.getAttribute("currentUser").id}, ${product.id})"/>
                                <label for="star5" title="5 stars">5 stars</label>
                                <input type="radio" id="star4" name="rate" value="4"
                                       onclick="ratingStar('${rating}', this, ${pageContext.session.getAttribute("currentUser").id}, ${product.id})"/>
                                <label for="star4" title="4 stars">4 stars</label>
                                <input type="radio" id="star3" name="rate" value="3"
                                       onclick="ratingStar('${rating}', this, ${pageContext.session.getAttribute("currentUser").id}, ${product.id})"/>
                                <label for="star3" title="3 stars">3 stars</label>
                                <input type="radio" id="star2" name="rate" value="2"
                                       onclick="ratingStar('${rating}', this, ${pageContext.session.getAttribute("currentUser").id}, ${product.id})"/>
                                <label for="star2" title="2 stars">2 stars</label>
                                <input type="radio" id="star1" name="rate" value="1"
                                       onclick="ratingStar('${rating}', this, ${pageContext.session.getAttribute("currentUser").id}, ${product.id})"/>
                                <label for="star1" title="1 stars">1 star</label>
                            </div>
                        </c:when>
                    </c:choose>

                </div>
                <div class="alert alert-success" style="display: none;">
                    <strong>Thông Báo!</strong> Đánh giá sản phẩm thành công.
                </div>

                <div class="alert alert-danger" style="display: none;">
                    <strong>Thông Báo!</strong> Bạn đã đánh giá sản phẩm này rồi.
                </div>
            </div>
        </div>
    </div>
    <hr/>

    <div>
        <textarea class="form-control" rows="2" id="content-comment" name="content" style="background-color: #dff;"
                  placeholder="Mời bạn bình luận, vui lòng không spam, share link kiếm tiền, thiếu lành mạnh,... để tránh bị khóa tài khoản"></textarea>
        <div class="d-flex justify-content-center m-2">
            <c:choose>
                <c:when test="${pageContext.request.userPrincipal.name == null}">
                    <p style="font-size: 16px;">Vui lòng <a href="${loginUrl}">đăng nhập</a> để bình luận!</p>
                </c:when>
                <c:when test="${pageContext.request.userPrincipal.name != null}">
                    <button onclick="addComment('${comment}', '${voted}', '${report}', '${deleted}', '${changed}', '${leveled}')"
                            class="btn btn-outline-danger">Thêm bình luận
                    </button>
                </c:when>
            </c:choose>
        </div>
    </div>
    <div class="spinner-grow text-primary spinner m-auto"></div>
    <section class="overflow-hidden" style="background-color: #e7effd;">
        <div class="container m-2 p-4 text-dark" data-aos="fade-left">
            <div class="row d-flex justify-content-center">
                <div class="col-lg-9 col-md-10 col-sm-11 col-12" id="comments">
                </div>
            </div>
        </div>
        <ul class="pagination justify-content-center" id="pagination-comment">

        </ul>
    </section>
</div>

<div class="js-modal">
    <div class="modal-container-black js-modal-container">
        <div class="js-modal-close">
            <i class="fa-solid fa-xmark"></i>
        </div>
        <header class="modal-header-black">
            <h3><i class="fa-solid fa-address-card"></i> Thông tin</h3>
        </header>
        <div class="modal-body">
            <div class="row">
                <div class="col-lg-4 col-md-4 col-sm-6 col-12">
                    <div class="modal-img-black" id="modal-account-img">

                    </div>
                </div>
                <div class="col-lg-7 col-md-7 col-sm-6 col-12">
                    <div class="modal-content card-body" id="modal-account-about">

                    </div>
                </div>
            </div>
        </div>
        <div class="footer-modal-black" id="modal-account-title">

        </div>
    </div>
</div>

<div class="alert alert-danger alert-absolute"
     style="display: none; position: fixed; right: 1%; bottom: 1%; z-index: 106;">
    <strong>Thông Báo!</strong> Bạn đã bình chọn cho bình luận này rồi.
</div>
<div class="alert alert-danger alert-absolute-nologin"
     style="display: none; position: fixed; right: 1%; bottom: 1%; z-index: 106;">
    <strong>Thông Báo!</strong> Vui lòng đăng nhập để thực hiện thao tác.
</div>
<div class="alert alert-success alert-absolute-report"
     style="display: none; position: fixed; right: 1%; bottom: 1%; z-index: 106;">
    <strong>Thông Báo!</strong> Báo cáo đã được gửi đến <strong>Quản trị viên</strong>.
</div>

<script src="<c:url value="/resources/js/comment.js" />"></script>
<script src="<c:url value="/resources/js/product-detail.js" />"></script>
<script>
                        const quantityInput = document.getElementById('quantity');
                        const quantityValue = quantityInput.value;
                        let quantityCart = quantityValue;
                        const updateQuantityValue = () => {
                            quantityCart = quantityInput.value;
                        };
                        const handleQuantityIncrease = () => {
                            quantityInput.stepUp();
                            updateQuantityValue(); // Cập nhật giá trị quantityValue sau khi thay đổi giá trị trong ô input
                        };

                        // Hàm xử lý sự kiện khi ấn nút trừ
                        const handleQuantityDecrease = () => {
                            quantityInput.stepDown();
                            updateQuantityValue(); // Cập nhật giá trị quantityValue sau khi thay đổi giá trị trong ô input
                        };
                        // Gọi hàm cập nhật giá trị khi có sự kiện 'input'
                        quantityInput.addEventListener('input', updateQuantityValue);
                        // Gọi hàm cập nhật giá trị khi ấn nút cộng hoặc nút trừ
                        document.querySelector('.quantity-right-plus').addEventListener('click', handleQuantityIncrease);
                        document.querySelector('.quantity-left-minus').addEventListener('click', handleQuantityDecrease);
                        window.addEventListener('load', () => {
                            loadComments('${comment}', '${voted}', '${report}', '${deleted}', '${changed}', '${leveled}');
                        });
</script>
