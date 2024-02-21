<%--
  Created by IntelliJ IDEA.
  User: phuan
  Date: 24/01/2024
  Time: 1:50 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1 class="text-center text-success mt-4 mb-4" data-aos="flip-down"><i class="fa-solid fa-bell"></i> THÔNG BÁO</h1>

<div class="container">
    <c:if test="${orderDetails != null}">
        <c:forEach items="${orderDetails}" var="o">
            <c:choose>
                <c:when test="${o.orderstatus.id == 9}">
                    <div class="row m-2 p-2">
                        <div class="col-lg-2 col-md-2 col-sm-2 col-4">
                            <img src="${o.productId.image}" alt="picture"
                                 style="width: 100%; max-height: 140px; min-height: 140px;"/>
                        </div>
                        <div class="col-lg-10 col-md-10 col-sm-10 col-8">
                            <h4>Đơn hàng của bạn đã được ghi nhận</h4>
                            <div>
                                <p>Kiện hàng <b>${o.productId.name}</b> của bạn sẽ được Shop xác nhận trong thời gian
                                    ngắn nhất. Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi!😊</p>
                                <p class="create-date">${o.createdDate}</p>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:when test="${o.orderstatus.id == 10}">
                    <div class="row m-2 p-2">
                        <div class="col-lg-2 col-md-2 col-sm-2 col-4">
                            <img src="${o.productId.image}" alt="picture"
                                 style="width: 100%; max-height: 140px; min-height: 140px;"/>
                        </div>
                        <div class="col-lg-10 col-md-10 col-sm-10 col-8">
                            <h4>Bạn có đơn hàng đang trên đường giao</h4>
                            <div>
                                <p>Shipper bảo rằng: Kiện hàng <b>${o.productId.name}</b> của bạn vẫn đang trong quá
                                    trình vận chuyển và dự kiến được giao trong 1-2 ngày tới. Vui lòng bỏ qua thông báo
                                    này nếu bạn đã nhận được hàng nhé!😊</p>
                                <p class="create-date">${o.updatedDate}</p>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:when test="${o.orderstatus.id == 11}">
                    <div class="row m-2 p-2">
                        <div class="col-lg-2 col-md-2 col-sm-2 col-4">
                            <img src="${o.productId.image}" alt="picture"
                                 style="width: 100%; max-height: 140px; min-height: 140px;"/>
                        </div>
                        <div class="col-lg-10 col-md-10 col-sm-10 col-8">
                            <h4>Giao kiện hàng thành công</h4>
                            <div>
                                <p>Kiện hàng <b>${o.productId.name}</b> của đơn hàng #${o.orderId.id} đã giao thành công
                                    đến bạn.</p>
                                <p class="create-date">${o.updatedDate}</p>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:when test="${o.orderstatus.id == 12}">
                    <div class="row m-2 p-2">
                        <div class="col-lg-2 col-md-2 col-sm-2 col-4">
                            <img src="${o.productId.image}" alt="picture"
                                 style="width: 100%; max-height: 140px; min-height: 140px;"/>
                        </div>
                        <div class="col-lg-10 col-md-10 col-sm-10 col-8">
                            <h4>Đơn hàng đã hủy</h4>
                            <div>
                                <p>Đã hủy kiện hàng <b>${o.productId.name}</b> của đơn hàng #${o.orderId.id}.</p>
                                <p class="create-date">${o.updatedDate}</p>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="row m-2 p-2">
                        <div class="col-lg-2 col-md-2 col-sm-2 col-4">
                            <img src="${o.productId.image}" alt="picture"
                                 style="width: 100%; max-height: 140px; min-height: 140px;"/>
                        </div>
                        <div class="col-lg-10 col-md-10 col-sm-10 col-8">
                            <h4>Trả hàng/Hoàn tiền</h4>
                            <div>
                                <p>Yêu cầu trả hàng/hoàn tiền đối với kiện hàng <b>${o.productId.name}</b> của đơn hàng
                                    #${o.orderId.id}.</p>
                                <p class="create-date">${o.updatedDate}</p>
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:if>
    <c:if test="${promotions != null}">
        <c:forEach items="${promotions}" var="p" varStatus="loop">
            <div class="row m-2 p-2">
                <div class="col-lg-2 col-md-2 col-sm-2 col-4">
                    <img src="https://res.cloudinary.com/dkmug1913/image/upload/v1706352647/WebApp/sg-11134004-7qver-lkhlv46clxt227_tn_jw82a9.png"
                         alt="picture" style="width: 100%; max-height: 140px; min-height: 140px;"/>
                </div>
                <div class="col-lg-10 col-md-10 col-sm-10 col-8">
                    <h4>${p.note}</h4>
                    <div>
                        <div style="background-image: url('${p.img}'); background-size: contain; background-repeat: no-repeat; width: 400px; height: 200px;">
                        </div>
                        <p style="margin: 14px 0;"><span class="discountCode"
                                                         style="background-color: yellow; color: red; font-weight: bolder; text-transform: uppercase;">${p.code}</span>
                            <a class="copy-button m-2" onclick="copyCode(${loop.index})"><i
                                    class="fa-regular fa-copy"></i></a>
                        </p>
                        <p>Mã hết hạn vào <span class="create-date">${p.endDate}</span>! Mã đã có sẵn trong ví! Hàng
                            loạt sản phẩm giá tốt đang chờ. Dùng
                            ngay thôi!</p>
                    </div>
                </div>
            </div>
        </c:forEach>
    </c:if>
</div>
