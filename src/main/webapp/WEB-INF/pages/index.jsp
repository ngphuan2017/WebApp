<%-- 
    Document   : index
    Created on : Jul 19, 2023, 5:40:28 PM
    Author     : phuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="demo" class="carousel slide" data-bs-ride="carousel">
    <div class="carousel-inner">
        <div class="carousel-item active">
            <img src="https://res.cloudinary.com/dkmug1913/image/upload/v1687089462/WebApp/slider1_srjkgr.jpg" class="d-block" style="width:100%">
        </div>
        <div class="carousel-item">
            <img src="https://res.cloudinary.com/dkmug1913/image/upload/v1687089465/WebApp/slider2_ssinep.jpg" class="d-block" style="width:100%">
        </div>
        <div class="carousel-item">
            <img src="https://res.cloudinary.com/dkmug1913/image/upload/v1687089467/WebApp/slider3_bd6qzi.jpg" class="d-block" style="width:100%">
        </div>
        <div class="carousel-item">
            <img src="https://res.cloudinary.com/dkmug1913/image/upload/v1687089469/WebApp/slider4_xjmrb8.jpg" class="d-block" style="width:100%">
        </div>
        <div class="carousel-item">
            <img src="https://res.cloudinary.com/dkmug1913/image/upload/v1687089474/WebApp/dochoi_i93xog.jpg" class="d-block" style="width:100%">
        </div>
        <div class="carousel-item">
            <img src="https://res.cloudinary.com/dkmug1913/image/upload/v1687089476/WebApp/mypham_atwcrf.jpg" class="d-block" style="width:100%">
        </div>
        <div class="banner">
            <div class="clouds">
                <img src="https://res.cloudinary.com/dkmug1913/image/upload/v1687075887/WebApp/cloud1_ortplp.png" style="--i:1;">
                <img src="https://res.cloudinary.com/dkmug1913/image/upload/v1687075891/WebApp/cloud2_ngpdc4.png" style="--i:2;">
                <img src="https://res.cloudinary.com/dkmug1913/image/upload/v1687075895/WebApp/cloud3_szlrii.png" style="--i:3;">
            </div>
        </div>
        <div class="text-slider">
            <img id="logo-banner" src="https://res.cloudinary.com/dkmug1913/image/upload/v1687075874/WebApp/shops_ibolhy.png" alt="Phú An Shop">
            <h5 style="font-weight: bolder;">Giá rẻ và chất lượng</h5>
        </div>
    </div>
</div>
<section class="container">
    <div class="content-title">
        <h2 class="section-heading"><span class="colored-letter">Top bán chạy</span></h2>
    </div>
    <div class="row">
        <c:forEach items="${products}" var="p">
            <c:url value="/products/${p.id}" var="detail" />
            <div class="col-lg-2 col-md-3 col-sm-4 col-6">
                <div class="card card-block">
                    <h4 class="card-title text-right"><i class="fa-solid fa-gear"></i></h4>
                    <div class="price-discount" style="display: none;">-<span class="discount">${p.discount}</span>%</div>
                    <img style="width: 100%; max-height: 120px; min-height: 120px; padding: 5px 15%;" src="${p.image}" alt="image">
                    <div class="card-body" style="height: 160px;">
                        <h6 class="card-title text-center" style="min-height: 50px; margin-bottom: 5px; font-weight: 700;">${p.name}</h6>
                        <div class="currency text-center"><del class="price-del" style="display: none; margin: 0; color: #999;"><span class="money price-old">${p.price}</span></del></div>
                        <div class="currency text-center" style="height: 30px; margin: 0; color: #dc3545; font-weight: 700;"><span class="money price-new">${p.price}</span> <i class="fa-solid fa-coins" style="color: #ffdd10;"></i></div>
                        <div class="d-flex justify-content-center">
                            <c:url value="/api/products/${p.id}/view" var="endpointview" />
                            <c:url value="/api/cart" var="endpoint" />
                            <a href="${detail}" class="btn btn-primary" style="margin-right: 20px;"><i class="far fa-eye"></i></a>
                            <a href="javascript:;" onclick="productView('${endpointview}', '${endpoint}')" class="btn btn-danger js-add-cart"><i class="fa-solid fa-cart-shopping"></i></a>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    <div class="content-title">
        <h2 class="section-heading"><span class="colored-letter">Khuyến mãi hôm nay</span></h2>
    </div>
    <div class="row">
        
    </div>
</section>
<div class="js-modal">
    <div class="modal-container js-modal-container">
        <div class="js-modal-close">
            <i class="fa-solid fa-xmark"></i>
        </div>
        <header class="modal-header">
            <img style="margin-top: 20px;" src="https://res.cloudinary.com/dkmug1913/image/upload/v1687075924/WebApp/shopping-cart_o9rvn4.png" alt="cart">
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
                                <button type="button" style="padding: 9px;" class="quantity-left-minus btn btn-danger btn-number" data-type="minus"
                                        data-field="">
                                    <i class="fa-solid fa-minus"></i>
                                </button>
                            </span>
                            <input type="number" id="quantity" name="quantity" class="form-control input-number text-center" value="1" min="1"
                                   max="1000">
                            <span class="input-group-btn">
                                <button type="button" style="padding: 9px;" class="quantity-right-plus btn btn-success btn-number" data-type="plus"
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
<marquee class="slider-letter">PHÚ AN SHOP LUÔN ĐẶT CHẤT LƯỢNG VÀ UY TÍN LÊN HÀNG ĐẦU</marquee>
