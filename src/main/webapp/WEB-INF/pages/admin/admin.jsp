<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container-fluid">
    <div class="d-sm-flex justify-content-between align-items-center mb-4">
        <h3 class="text-dark mb-0">Thống kê & Báo cáo</h3><a class="btn btn-primary btn-sm d-none d-sm-inline-block"
                                                             role="button" href="<c:url value="/api/admin/download-pdf" />"><i
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
                            <div class="text-uppercase text-success fw-bold text-xs mb-1"><span>Thu nhập (hàng năm)</span>
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
            <div class="card shadow border-start-info py-2">
                <div class="card-body">
                    <div class="row align-items-center no-gutters">
                        <div class="col me-2">
                            <div class="text-uppercase text-info fw-bold text-xs mb-1"><span>Đơn hàng đang đợi</span></div>
                            <div class="text-dark fw-bold h5 mb-0"><span>${orderDetails}</span></div>
                        </div>
                        <div class="col-auto"><i class="fas fa-clipboard-list fa-2x text-gray-300"></i></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-6 col-xl-3 mb-4">
            <div class="card shadow border-start-warning py-2">
                <div class="card-body">
                    <div class="row align-items-center no-gutters">
                        <div class="col me-2">
                            <div class="text-uppercase text-warning fw-bold text-xs mb-1"><span>Yêu cầu đang đợi</span>
                            </div>
                            <div class="text-dark fw-bold h5 mb-0"><span>${reports}</span></div>
                        </div>
                        <div class="col-auto"><i class="fas fa-comments fa-2x text-gray-300"></i></div>
                    </div>
                </div>
            </div>
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
    <div class="row">
        <div class="col-lg-6 mb-4">
            <div class="card shadow mb-4">
                <div class="card-header py-3">
                    <h6 class="text-primary fw-bold m-0">Projects</h6>
                </div>
                <div class="card-body">
                    <h4 class="small fw-bold">Server migration<span class="float-end">20%</span></h4>
                    <div class="progress mb-4">
                        <div class="progress-bar bg-danger" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"
                             style="width: 20%;"><span class="visually-hidden">20%</span></div>
                    </div>
                    <h4 class="small fw-bold">Sales tracking<span class="float-end">40%</span></h4>
                    <div class="progress mb-4">
                        <div class="progress-bar bg-warning" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100"
                             style="width: 40%;"><span class="visually-hidden">40%</span></div>
                    </div>
                    <h4 class="small fw-bold">Customer Database<span class="float-end">60%</span></h4>
                    <div class="progress mb-4">
                        <div class="progress-bar bg-primary" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
                             style="width: 60%;"><span class="visually-hidden">60%</span></div>
                    </div>
                    <h4 class="small fw-bold">Payout Details<span class="float-end">80%</span></h4>
                    <div class="progress mb-4">
                        <div class="progress-bar bg-info" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100"
                             style="width: 80%;"><span class="visually-hidden">80%</span></div>
                    </div>
                    <h4 class="small fw-bold">Account setup<span class="float-end">Complete!</span></h4>
                    <div class="progress mb-4">
                        <div class="progress-bar bg-success" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100"
                             style="width: 100%;"><span class="visually-hidden">100%</span></div>
                    </div>
                </div>
            </div>
            <div class="card shadow mb-4">
                <div class="card-header py-3">
                    <h6 class="text-primary fw-bold m-0">Todo List</h6>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">
                        <div class="row align-items-center no-gutters">
                            <div class="col me-2">
                                <h6 class="mb-0"><strong>Lunch meeting</strong></h6><span
                                    class="text-xs">10:30 AM</span>
                            </div>
                            <div class="col-auto">
                                <div class="form-check"><input class="form-check-input" type="checkbox"
                                                               id="formCheck-1"><label class="form-check-label"
                                                               for="formCheck-1"></label></div>
                            </div>
                        </div>
                    </li>
                    <li class="list-group-item">
                        <div class="row align-items-center no-gutters">
                            <div class="col me-2">
                                <h6 class="mb-0"><strong>Lunch meeting</strong></h6><span
                                    class="text-xs">11:30 AM</span>
                            </div>
                            <div class="col-auto">
                                <div class="form-check"><input class="form-check-input" type="checkbox"
                                                               id="formCheck-2"><label class="form-check-label"
                                                               for="formCheck-2"></label></div>
                            </div>
                        </div>
                    </li>
                    <li class="list-group-item">
                        <div class="row align-items-center no-gutters">
                            <div class="col me-2">
                                <h6 class="mb-0"><strong>Lunch meeting</strong></h6><span
                                    class="text-xs">12:30 AM</span>
                            </div>
                            <div class="col-auto">
                                <div class="form-check"><input class="form-check-input" type="checkbox"
                                                               id="formCheck-3"><label class="form-check-label"
                                                               for="formCheck-3"></label></div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
        <div class="col">
            <div class="row">
                <div class="col-lg-6 mb-4">
                    <div class="card text-white bg-primary shadow">
                        <div class="card-body">
                            <p class="m-0">Primary</p>
                            <p class="text-white-50 small m-0">#4e73df</p>
                        </div>
                    </div>
                </div>
                <div class="col-lg-6 mb-4">
                    <div class="card text-white bg-success shadow">
                        <div class="card-body">
                            <p class="m-0">Success</p>
                            <p class="text-white-50 small m-0">#1cc88a</p>
                        </div>
                    </div>
                </div>
                <div class="col-lg-6 mb-4">
                    <div class="card text-white bg-info shadow">
                        <div class="card-body">
                            <p class="m-0">Info</p>
                            <p class="text-white-50 small m-0">#36b9cc</p>
                        </div>
                    </div>
                </div>
                <div class="col-lg-6 mb-4">
                    <div class="card text-white bg-warning shadow">
                        <div class="card-body">
                            <p class="m-0">Warning</p>
                            <p class="text-white-50 small m-0">#f6c23e</p>
                        </div>
                    </div>
                </div>
                <div class="col-lg-6 mb-4">
                    <div class="card text-white bg-danger shadow">
                        <div class="card-body">
                            <p class="m-0">Danger</p>
                            <p class="text-white-50 small m-0">#e74a3b</p>
                        </div>
                    </div>
                </div>
                <div class="col-lg-6 mb-4">
                    <div class="card text-white bg-secondary shadow">
                        <div class="card-body">
                            <p class="m-0">Secondary</p>
                            <p class="text-white-50 small m-0">#858796</p>
                        </div>
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