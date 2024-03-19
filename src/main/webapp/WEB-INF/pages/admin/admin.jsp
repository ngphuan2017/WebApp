<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url value="/admin/api/download-pdf" var="pdf" />
<c:url value="/admin/order-management" var="order" />
<c:url value="/admin/report-management" var="report" />

<div class="container-fluid">
    <div class="d-sm-flex justify-content-between align-items-center mb-4">
        <h3 class="text-dark mb-0">Thống kê & Báo cáo</h3><a class="btn btn-primary btn-sm d-none d-sm-inline-block"
                                                             role="button" href="${pdf}"><i
                class="fas fa-download fa-sm text-white-50"></i>&nbsp;Doanh thu tháng này</a>
    </div>
    <div class="row">
        <div class="col-md-6 col-xl-3 mb-4">
            <div class="card shadow border-start-primary py-2">
                <div class="card-body">
                    <div class="row align-items-center no-gutters">
                        <div class="col me-2">
                            <div class="text-uppercase text-primary fw-bold text-xs mb-1">
                                <span>Thu nhập (Hôm nay)</span></div>
                            <div class="text-dark fw-bold h5 mb-0 currency">
                                <span class="money">
                                    <c:choose>
                                        <c:when test="${dayStats != null}">
                                            <c:forEach items="${dayStats}" var="d">
                                                ${d[3]}
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            0
                                        </c:otherwise>
                                    </c:choose>
                                </span>&#x20AB;
                            </div>
                        </div>
                        <div class="col-auto"><i class="fas fa-calendar fa-2x text-gray-300"></i></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-6 col-xl-3 mb-4">
            <div class="card shadow border-start-success py-2">
                <div class="card-body">
                    <div class="row align-items-center no-gutters">
                        <div class="col me-2">
                            <div class="text-uppercase text-success fw-bold text-xs mb-1"><span>Tổng thu nhập (năm nay)</span>
                            </div>
                            <div class="text-dark fw-bold h5 mb-0 currency">
                                <span class="money">
                                    <c:choose>
                                        <c:when test="${yearStats != null}">
                                            <c:forEach items="${yearStats}" var="y">
                                                ${y[3]}
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            0
                                        </c:otherwise>
                                    </c:choose>
                                </span>&#x20AB;
                            </div>
                        </div>
                        <div class="col-auto"><i class="fas fa-dollar-sign fa-2x text-gray-300"></i></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-6 col-xl-3 mb-4">
            <a class="card shadow border-start-info py-2 text-decoration-none" href="${order}">
                <div class="card-body">
                    <div class="row align-items-center no-gutters">
                        <div class="col me-2">
                            <div class="text-uppercase text-info fw-bold text-xs mb-1"><span>Đơn hàng đang đợi</span></div>
                            <div class="text-dark fw-bold h5 mb-0"><span>${orderDetailbar}</span></div>
                        </div>
                        <div class="col-auto"><i class="fas fa-clipboard-list fa-2x text-gray-300"></i></div>
                    </div>
                </div>
            </a>
        </div>
        <div class="col-md-6 col-xl-3 mb-4">
            <a class="card shadow border-start-warning py-2 text-decoration-none" href="${report}">
                <div class="card-body">
                    <div class="row align-items-center no-gutters">
                        <div class="col me-2">
                            <div class="text-uppercase text-warning fw-bold text-xs mb-1"><span>Yêu cầu đang chờ</span>
                            </div>
                            <div class="text-dark fw-bold h5 mb-0"><span>${reportbar}</span></div>
                        </div>
                        <div class="col-auto"><i class="fas fa-comments fa-2x text-gray-300"></i></div>
                    </div>
                </div>
            </a>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-7 col-xl-8">
            <div class="card shadow mb-4">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h6 class="text-primary fw-bold m-0">Tổng quan thu nhập (8 tháng gần nhất)</h6>
                    <div class="dropdown no-arrow">
                        <button class="btn btn-link btn-sm dropdown-toggle" aria-expanded="false"
                                data-bs-toggle="dropdown" type="button"><i class="fas fa-ellipsis-v text-gray-400"></i>
                        </button>
                        <div class="dropdown-menu shadow dropdown-menu-end animated--fade-in">
                            <p class="text-center dropdown-header">Công cụ</p><a class="dropdown-item"
                                                                                 href="javascript:;">&nbsp;Xem chi tiết</a><a
                                                                                 class="dropdown-item" href="javascript:;">&nbsp;Tải hình ảnh</a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="javascript:;">&nbsp;Tùy chọn khác</a>
                        </div>
                    </div>
                </div>
                <div class="card-body">
                    <div class="chart-area">
                        <canvas data-bss-chart="{&quot;type&quot;:&quot;bar&quot;,&quot;data&quot;:{&quot;labels&quot;:[<c:forEach items="${revenues}" var="r">&quot;Tháng ${r[2]}&quot;,</c:forEach>&quot;&quot;],&quot;datasets&quot;:[{&quot;label&quot;:&quot;Thu nhập&quot;,&quot;fill&quot;:true,&quot;data&quot;:[<c:forEach items="${revenues}" var="r">&quot;${r[3]}&quot;,</c:forEach>&quot;&quot;],&quot;backgroundColor&quot;:&quot;#36b9cc&quot;,&quot;borderColor&quot;:&quot;rgba(78, 115, 223, 1)&quot;}]},&quot;options&quot;:{&quot;maintainAspectRatio&quot;:false,&quot;legend&quot;:{&quot;display&quot;:false,&quot;labels&quot;:{&quot;fontStyle&quot;:&quot;normal&quot;}},&quot;title&quot;:{&quot;fontStyle&quot;:&quot;normal&quot;},&quot;scales&quot;:{&quot;xAxes&quot;:[{&quot;gridLines&quot;:{&quot;color&quot;:&quot;rgb(234, 236, 244)&quot;,&quot;zeroLineColor&quot;:&quot;rgb(234, 236, 244)&quot;,&quot;drawBorder&quot;:false,&quot;drawTicks&quot;:false,&quot;borderDash&quot;:[&quot;2&quot;],&quot;zeroLineBorderDash&quot;:[&quot;2&quot;],&quot;drawOnChartArea&quot;:false},&quot;ticks&quot;:{&quot;fontColor&quot;:&quot;#858796&quot;,&quot;fontStyle&quot;:&quot;normal&quot;,&quot;padding&quot;:20}}],&quot;yAxes&quot;:[{&quot;gridLines&quot;:{&quot;color&quot;:&quot;rgb(234, 236, 244)&quot;,&quot;zeroLineColor&quot;:&quot;rgb(234, 236, 244)&quot;,&quot;drawBorder&quot;:false,&quot;drawTicks&quot;:false,&quot;borderDash&quot;:[&quot;2&quot;],&quot;zeroLineBorderDash&quot;:[&quot;2&quot;]},&quot;ticks&quot;:{&quot;fontColor&quot;:&quot;#858796&quot;,&quot;fontStyle&quot;:&quot;normal&quot;,&quot;padding&quot;:20}}]}}}"></canvas>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-5 col-xl-4">
                <div class="card shadow mb-4">
                    <div class="card-header d-flex justify-content-between align-items-center">
                        <h6 class="text-primary fw-bold m-0">Nguồn doanh thu</h6>
                        <div class="dropdown no-arrow">
                            <button class="btn btn-link btn-sm dropdown-toggle" aria-expanded="false"
                                    data-bs-toggle="dropdown" type="button"><i class="fas fa-ellipsis-v text-gray-400"></i>
                            </button>
                            <div class="dropdown-menu shadow dropdown-menu-end animated--fade-in">
                                <p class="text-center dropdown-header">Công cụ</p><a class="dropdown-item"
                                                                                     href="javascript:;">&nbsp;Xem chi tiết</a><a
                                                                                     class="dropdown-item" href="javascript:;">&nbsp;Tải hình ảnh</a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="javascript:;">&nbsp;Tùy chọn khác</a>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <div class="chart-area">
                                <canvas data-bss-chart="{&quot;type&quot;:&quot;doughnut&quot;,&quot;data&quot;:{&quot;labels&quot;:[<c:forEach items="${cateStats}" var="c">&quot;${c[1]}&quot;,</c:forEach>&quot;&quot;],&quot;datasets&quot;:[{&quot;label&quot;:&quot;&quot;,&quot;backgroundColor&quot;:[&quot;#4e73df&quot;,&quot;#1cc88a&quot;,&quot;#f6c23e&quot;,&quot;#e74a3b&quot;,&quot;#858796&quot;],&quot;borderColor&quot;:[&quot;#ffffff&quot;,&quot;#ffffff&quot;,&quot;#ffffff&quot;],&quot;data&quot;:[<c:forEach items="${cateStats}" var="c">&quot;${c[3]}&quot;,</c:forEach>&quot;&quot;]}]},&quot;options&quot;:{&quot;maintainAspectRatio&quot;:false,&quot;legend&quot;:{&quot;display&quot;:false,&quot;labels&quot;:{&quot;fontStyle&quot;:&quot;normal&quot;}},&quot;title&quot;:{&quot;fontStyle&quot;:&quot;normal&quot;}}}"></canvas>
                        </div>
                        <div class="text-center small mt-4">
                        <c:forEach items="${cateStats}" var="c" varStatus="loop">
                            <c:set var="cssClass" value=""/>
                            <c:choose>
                                <c:when test="${loop.index == 0}">
                                    <c:set var="cssClass" value="text-primary"/>
                                </c:when>
                                <c:when test="${loop.index == 1}">
                                    <c:set var="cssClass" value="text-success"/>
                                </c:when>
                                <c:when test="${loop.index == 2}">
                                    <c:set var="cssClass" value="text-warning"/>
                                </c:when>
                                <c:when test="${loop.index == 3}">
                                    <c:set var="cssClass" value="text-danger"/>
                                </c:when>
                                <c:when test="${loop.index == 2}">
                                    <c:set var="cssClass" value="text-secondary"/>
                                </c:when>
                            </c:choose>
                            <span class="me-2"><i class="fas fa-circle ${cssClass}"></i>&nbsp;${c[1]}</span>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    function numberWithCommas(x) {
        return parseFloat(x).toFixed(0).replace(/\B(?=(\d{3})+(?!\d))/g, ",").toString();
    }

    let currencyElements = document.querySelectorAll(".currency .money");
    currencyElements.forEach((element) => {
        const amountValue = parseFloat(element.textContent);
        element.textContent = numberWithCommas(amountValue);
    });
</script>