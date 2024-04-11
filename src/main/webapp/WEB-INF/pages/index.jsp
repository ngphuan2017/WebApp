<%-- 
    Document   : index
    Created on : Jul 19, 2023, 5:40:28 PM
    Author     : phuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url value="/" var="action"/>
<c:url value="/products" var="detail"/>
<c:url value="/api/products" var="endpointview"/>
<c:url value="/api/cart" var="endpoint"/>
<c:url value="/api/check/cart" var="checked"/>
<c:url value="/api/users" var="usered"/>
<c:url value="/api/users/level" var="leveled"/>

<c:choose>
    <c:when test="${empty param.categorysubId && empty param.kw}">
        <div id="demo" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img src="https://res.cloudinary.com/dkmug1913/image/upload/v1687089462/WebApp/slider1_srjkgr.jpg"
                         class="d-block" style="width:100%">
                </div>
                <div class="carousel-item">
                    <img src="https://res.cloudinary.com/dkmug1913/image/upload/v1687089465/WebApp/slider2_ssinep.jpg"
                         class="d-block" style="width:100%">
                </div>
                <div class="carousel-item">
                    <img src="https://res.cloudinary.com/dkmug1913/image/upload/v1687089467/WebApp/slider3_bd6qzi.jpg"
                         class="d-block" style="width:100%">
                </div>
                <div class="carousel-item">
                    <img src="https://res.cloudinary.com/dkmug1913/image/upload/v1687089469/WebApp/slider4_xjmrb8.jpg"
                         class="d-block" style="width:100%">
                </div>
                <div class="carousel-item">
                    <img src="https://res.cloudinary.com/dkmug1913/image/upload/v1687089474/WebApp/dochoi_i93xog.jpg"
                         class="d-block" style="width:100%">
                </div>
                <div class="carousel-item">
                    <img src="https://res.cloudinary.com/dkmug1913/image/upload/v1697729774/WebApp/cac-truong-hop-lam-gia-my-pham_dhpzjo.jpg"
                         class="d-block" style="width:100%">
                </div>
                <div class="banner">
                    <div class="clouds">
                        <img src="https://res.cloudinary.com/dkmug1913/image/upload/v1687075887/WebApp/cloud1_ortplp.png"
                             style="--i:1;">
                        <img src="https://res.cloudinary.com/dkmug1913/image/upload/v1687075891/WebApp/cloud2_ngpdc4.png"
                             style="--i:2;">
                        <img src="https://res.cloudinary.com/dkmug1913/image/upload/v1687075895/WebApp/cloud3_szlrii.png"
                             style="--i:3;">
                    </div>
                </div>
                <div class="text-slider">
                    <img id="logo-banner" data-aos="fade-down" data-aos-delay="300" data-aos-once="true"
                         src="https://res.cloudinary.com/dkmug1913/image/upload/v1687075874/WebApp/shops_ibolhy.png"
                         alt="Phú An Shop">
                    <h5 style="font-weight: bolder;" data-aos="fade-right" data-aos-delay="500" data-aos-once="true">Giá rẻ và
                        chất lượng</h5>
                </div>
            </div>
        </div>
    </c:when>
</c:choose>
<c:if test="${products == null || empty products}">
    <div class="text-center m-2">
        <img class="rounded"
             src="https://res.cloudinary.com/dkmug1913/image/upload/v1710819437/WebApp/tl_apxwmw.webp"
             alt="not-found"/>
        <h3 class="text-center text-secondary m-2"><i class="fa-solid fa-magnifying-glass"></i> Không tìm thấy sản phẩm</h3>
    </div>
</c:if>
<c:if test="${products != null && not empty products}">
    <section class="container overflow-hidden">
        <c:if test="${empty param.categorysubId && empty param.kw}">
            <div class="content-title" data-aos="fade-left">
                <h2 class="section-heading"><span class="colored-letter">Top mua hàng</span></h2>
            </div>
            <div class="row">
                <div class="col-lg-6 col-md-6 col-sm-6 col-12 d-flex justify-content-center" data-aos="zoom-in">
                    <div class="top-account">
                        <c:choose>
                            <c:when test="${listTopAccount != null}">
                                <h5 class="text-center text-danger p-2">Top tiêu dùng tháng</h5>
                                <div class="top10-account">
                                    <table class="table table-hover table-borderless">
                                        <c:forEach items="${listTopAccount}" var="u">
                                            <tr>
                                                <td class="position-relative">
                                                    <div class="d-none required-exp-${u[0]}"></div>
                                                    <a href="javascript:;" onclick="accountView('${usered}/${u[0]}', '${leveled}')" class="js-modal-user">
                                                        <img width="30px" height="30px" src="${u[2] != null && not empty u[2] ? u[2] : 'https://res.cloudinary.com/dkmug1913/image/upload/v1690819242/WebApp/Avatar/none_ibdmnr.png'}" alt="${u[1]}" />
                                                        <img class="avatar-frame m-2" width="30px" height="30px" src="${u[4].url}"/>
                                                    </a>
                                                </td>
                                                <td>${u[1]}</td>
                                                <td class="text-end text-primary currency"><span class="money">${u[3]}</span> VNĐ</td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <h5 class="text-center text-danger p-2">Top tiêu dùng tháng</h5>
                                <p class="text-center text-primary">Danh sách trống</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-6 col-12 d-flex justify-content-center" data-aos="zoom-in">
                    <div class="top-account">
                        <c:choose>
                            <c:when test="${listAccountLogin != null}">
                                <h5 class="text-center text-danger p-2">Hoạt động gần đây</h5>
                                <div class="top10-account">
                                    <table class="table table-hover table-borderless">
                                        <c:forEach items="${listAccountLogin}" var="u">
                                            <tr>
                                                <td class="position-relative">
                                                    <div class="d-none required-exp-${u.id}"></div>
                                                    <a href="javascript:;" onclick="accountView('${usered}/${u.id}', '${leveled}')" class="js-modal-user">
                                                        <img width="30px" height="30px" src="${u.avatar != null && not empty u.avatar ? u.avatar : 'https://res.cloudinary.com/dkmug1913/image/upload/v1690819242/WebApp/Avatar/none_ibdmnr.png'}" alt="${u.fullname}" />
                                                        <img class="avatar-frame m-2" width="30px" height="30px" src="${u.avatarFrame.url}"/>
                                                    </a>
                                                </td>
                                                <td>${u.fullname}</td>
                                                <td class="text-end text-secondary fst-italic time-ago">${u.updatedDate}</td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <h5 class="text-center text-danger p-2">Hoạt động gần đây</h5>
                                <p class="text-center text-primary">Danh sách trống</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </c:if>
        <div class="content-title" data-aos="fade-left">
            <h2 class="section-heading"><span class="colored-letter">Top bán chạy</span></h2>
        </div>
        <div class="row">
            <c:forEach items="${products}" var="p">
                <div class="col-lg-2 col-md-3 col-sm-4 col-6 mb-3" data-aos="fade-left" data-aos-once="true">
                    <div class="card card-block">
                        <h4 class="card-title text-right"><i class="fa-solid fa-gear"></i></h4>
                        <div class="price-discount" style="display: none;">-<span
                                class="discount">${p.discount.discount}</span>%
                        </div>
                        <img style="width: 100%; max-height: 120px; min-height: 120px; padding: 5px 15%;"
                             src="${p.image}"
                             alt="image">
                        <div class="card-body" style="height: 160px;">
                            <h6 class="card-title text-center"
                                style="min-height: 50px; margin-bottom: 5px; font-weight: 700;">${p.name}</h6>
                            <div class="currency text-center">
                                <del class="price-del" style="display: none; margin: 0; color: #999;"><span
                                        class="money price-old">${p.price}</span></del>
                            </div>
                            <div class="currency text-center"
                                 style="height: 30px; margin: 0; color: #dc3545; font-weight: 700;"><span
                                    class="money price-new">${p.price}</span> <i class="fa-solid fa-coins"
                                    style="color: #ffdd10;"></i></div>
                            <div class="d-flex justify-content-center">
                                <a href="${detail}/${p.id}" class="btn btn-primary" style="margin-right: 20px;"><i
                                        class="far fa-eye"></i></a>
                                <a href="javascript:;" onclick="productView('${endpointview}/${p.id}', '${checked}', '${endpoint}')"
                                   class="btn btn-danger js-add-cart"><i class="fa-solid fa-cart-shopping"></i></a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <ul class="pagination justify-content-center">
            <c:if test="${page.totalPage > 1}">
                <c:choose>
                    <c:when test="${page.page > 1}">
                        <c:choose>
                            <c:when test="${not empty param.categorysubId && not empty param.kw}">
                                <c:set var="cateIdParam" value="categorysubId=${param.categorysubId}"/>
                                <c:set var="kwParam" value="kw=${param.kw}"/>
                                <li class="page-item">
                                    <a class="page-link" href="${action}?${cateIdParam}&${kwParam}&page=${page.page - 1}">«</a>
                                </li>
                            </c:when>
                            <c:when test="${not empty param.categorysubId}">
                                <c:set var="cateIdParam" value="categorysubId=${param.categorysubId}"/>
                                <li class="page-item">
                                    <a class="page-link" href="${action}?${cateIdParam}&page=${page.page - 1}">«</a>
                                </li>
                            </c:when>
                            <c:when test="${not empty param.kw}">
                                <c:set var="kwParam" value="kw=${param.kw}"/>
                                <li class="page-item">
                                    <a class="page-link" href="${action}?${kwParam}&page=${page.page - 1}">«</a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item">
                                    <a class="page-link" href="${action}?page=${page.page - 1}">«</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item disabled">
                            <span class="page-link">«</span>
                        </li>
                    </c:otherwise>
                </c:choose>
                <c:forEach begin="1" end="${page.totalPage}" var="item" varStatus="loop">
                    <c:set var="pageParam" value="page=${loop.index}"/>
                    <c:choose>
                        <c:when test="${not empty param.categorysubId && not empty param.kw}">
                            <c:set var="categorysubIdParam" value="categorysubId=${param.categorysubId}"/>
                            <c:set var="kwParam" value="kw=${param.kw}"/>
                            <c:if test="${not empty pageContext.request.queryString}">
                                <c:set var="queryStringWithoutPage" value=""/>
                                <c:forEach var="paramName" items="${pageContext.request.parameterMap.keySet()}">
                                    <c:if test="${not 'page' == paramName}">
                                        <c:set var="queryStringWithoutPage"
                                               value="${queryStringWithoutPage}&amp;${paramName}=${param[paramName]}"/>
                                    </c:if>
                                </c:forEach>
                                <c:set var="pageParam"
                                       value="${pageParam}&amp;${queryStringWithoutPage}${categorysubIdParam}${kwParam}"/>
                            </c:if>
                            <li class="page-item${loop.index == page.page ? ' active' : ''}">
                                <a class="page-link" href="${action}?${pageParam}">${loop.index}</a>
                            </li>
                        </c:when>
                        <c:when test="${not empty param.categorysubId}">
                            <c:set var="categorysubIdParam" value="categorysubId=${param.categorysubId}"/>
                            <c:if test="${not empty pageContext.request.queryString}">
                                <c:set var="queryStringWithoutPage" value=""/>
                                <c:forEach var="paramName" items="${pageContext.request.parameterMap.keySet()}">
                                    <c:if test="${not 'page' == paramName}">
                                        <c:set var="queryStringWithoutPage"
                                               value="${queryStringWithoutPage}&amp;${paramName}=${param[paramName]}"/>
                                    </c:if>
                                </c:forEach>
                                <c:set var="pageParam"
                                       value="${pageParam}&amp;${queryStringWithoutPage}${categorysubIdParam}"/>
                            </c:if>
                            <li class="page-item${loop.index == page.page ? ' active' : ''}">
                                <a class="page-link" href="${action}?${pageParam}">${loop.index}</a>
                            </li>
                        </c:when>
                        <c:when test="${not empty param.kw}">
                            <c:set var="kwParam" value="kw=${param.kw}"/>
                            <c:if test="${not empty pageContext.request.queryString}">
                                <c:set var="queryStringWithoutPage" value=""/>
                                <c:forEach var="paramName" items="${pageContext.request.parameterMap.keySet()}">
                                    <c:if test="${not 'page' == paramName}">
                                        <c:set var="queryStringWithoutPage"
                                               value="${queryStringWithoutPage}&amp;${paramName}=${param[paramName]}"/>
                                    </c:if>
                                </c:forEach>
                                <c:set var="pageParam"
                                       value="${pageParam}&amp;${queryStringWithoutPage}${kwParam}"/>
                            </c:if>
                            <li class="page-item${loop.index == page.page ? ' active' : ''}">
                                <a class="page-link" href="${action}?${pageParam}">${loop.index}</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item${loop.index == page.page ? ' active' : ''}">
                                <a class="page-link" href="${action}?${pageParam}">${loop.index}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:choose>
                    <c:when test="${page.page < page.totalPage}">
                        <c:choose>
                            <c:when test="${not empty param.categorysubId && not empty param.kw}">
                                <c:set var="cateIdParam" value="categorysubId=${param.categorysubId}"/>
                                <c:set var="kwParam" value="kw=${param.kw}"/>
                                <li class="page-item">
                                    <a class="page-link" href="${action}?${cateIdParam}&${kwParam}&page=${page.page + 1}">»</a>
                                </li>
                            </c:when>
                            <c:when test="${not empty param.categorysubId}">
                                <c:set var="cateIdParam" value="categorysubId=${param.categorysubId}"/>
                                <li class="page-item">
                                    <a class="page-link" href="${action}?${cateIdParam}&page=${page.page + 1}">»</a>
                                </li>
                            </c:when>
                            <c:when test="${not empty param.kw}">
                                <c:set var="kwParam" value="kw=${param.kw}"/>
                                <li class="page-item">
                                    <a class="page-link" href="${action}?${kwParam}&page=${page.page + 1}">»</a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item">
                                    <a class="page-link" href="${action}?page=${page.page + 1}">»</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item disabled">
                            <span class="page-link">»</span>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </ul>
    </section>
</c:if>
<div class="js-modal">
    <div class="modal-container js-modal-container">
        <div class="js-modal-close">
            <i class="fa-solid fa-xmark"></i>
        </div>
        <header class="modal-header">
            <img style="margin-top: 20px;"
                 src="https://res.cloudinary.com/dkmug1913/image/upload/v1687075924/WebApp/shopping-cart_o9rvn4.png"
                 alt="cart">
            <p style="margin-top: 10px;">Giỏ Hàng</p>
        </header>
        <div class="modal-body">
            <div class="modal-img" id="modal-product-img">

            </div>
            <div class="container">
                <div class="row button-quantity">
                    <div class="col-md-4 col-sm-6 col-12">
                        <div class="input-group">
                            <span class="input-group-btn">
                                <button type="button" style="padding: 9px;" onclick="checkQuantity(1)"
                                        class="quantity-left-minus btn btn-danger btn-number" data-type="minus"
                                        data-field="">
                                    <i class="fa-solid fa-minus"></i>
                                </button>
                            </span>
                            <input type="number" id="quantity" name="quantity"
                                   class="form-control input-number text-center" value="1" onblur="checkQuantity(0)"
                                   min="1" max="1000">
                            <span class="input-group-btn">
                                <button type="button" style="padding: 9px;" onclick="checkQuantity(1)"
                                        class="quantity-right-plus btn btn-success btn-number" data-type="plus"
                                        data-field="">
                                    <i class="fa-solid fa-plus"></i>
                                </button>
                            </span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="btn-buy-product" id="modal-product-buy">

            </div>
        </div>
        <div class="footer-modal">
            <p class="modal-help">Báo cáo <a href="mailto:phuan2017@gmail.com">sự cố?</a></p>
        </div>
    </div>
</div>
<div class="js-modal-account">
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
</script>