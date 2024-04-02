<%-- 
    Document   : promotion
    Created on : Oct 12, 2023, 1:38:10 AM
    Author     : phuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/admin/promotion-management" var="promotion" />
<c:url value="/admin/api/promotion-management/added" var="added" />
<c:url value="/admin/api/promotion-management/edited" var="edited" />
<c:url value="/admin/api/promotion-management/deleted" var="deleted" />

<div class="container-fluid">
    <h3 class="text-dark mb-4">Chương trình khuyến mãi</h3>
    <div class="card shadow">
        <div class="card-header py-3">
            <div class="row">
                <div class="col-md-6">
                    <p class="text-primary m-0 fw-bold">Thông tin khuyến mãi</p>
                </div>
                <div class="col-md-6">
                    <div class="text-md-end">
                        <button type="button" class="btn btn-outline-success js-add-cart-edit" onclick="addPromotion('${added}')">Thêm khuyến mãi</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="card-body">
            <div class="row">
                <div class="col-md-6 text-nowrap">
                    <div id="dataTable_length" class="dataTables_length" aria-controls="dataTable"><label class="form-label">Show&nbsp;<select class="d-inline-block form-select form-select-sm">
                                <option value="10">10</option>
                                <option value="25" selected="">25</option>
                                <option value="50">50</option>
                                <option value="100">100</option>
                            </select>&nbsp;</label></div>
                </div>
                <div class="col-md-6">
                    <div class="text-md-end dataTables_filter" id="dataTable_filter"><label class="form-label"><input type="search" class="form-control form-control-sm" aria-controls="dataTable" placeholder="Search"></label></div>
                </div>
            </div>
            <div class="table-responsive table mt-2" id="dataTable" role="grid" aria-describedby="dataTable_info">
                <table class="table table-hover my-0" id="dataTable">
                    <thead>
                        <tr>
                            <th>STT</th>
                            <th>Chương trình khuyến mãi</th>
                            <th>CODE</th>
                            <th>Khuyến mãi</th>
                            <th>Bắt đầu ngày</th>
                            <th>Kết thúc ngày</th>
                            <th>Loại áp dụng</th>
                            <th>Số lượng</th>
                            <th>Tỉ lệ</th>
                            <th>Level</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${promotions}" var="p" varStatus="loop">
                            <tr id="promotion-${p.id}">
                                <td>${loop.index + 1}</td>
                                <td class="text-order-name">
                                    <span id="promotion-name-old${p.id}">${p.note}</span>
                                    <input class="form-control d-none" id="promotion-name-new${p.id}" type="text" value="${p.note}" />
                                </td>
                                <td class="text-order-name text-uppercase text-danger">
                                    <span id="promotion-code-old${p.id}">${p.code}</span>
                                    <input class="form-control d-none" id="promotion-code-new${p.id}" type="text" value="${p.code}" />
                                </td>
                                <td class="text-order-name">
                                    <span id="promotion-discount-old${p.id}">${p.discount}${p.type.id == 20 ? "%" : "K"}</span>
                                    <input class="form-control d-none" id="promotion-discount-new${p.id}" type="number" value="${p.discount}" />
                                </td>
                                <td>
                                    <span class="create-date" id="promotion-begin-old${p.id}">${p.beginDate}</span>
                                    <input class="form-control d-none" id="promotion-begin-new${p.id}" type="date" value="${p.beginDate}" />
                                </td>
                                <td>
                                    <span class="create-date" id="promotion-end-old${p.id}">${p.endDate}</span>
                                    <input class="form-control d-none" id="promotion-end-new${p.id}" type="date" value="${p.endDate}" />
                                </td>
                                <td>
                                    <span id="promotion-type-old${p.id}">${p.type.statusname}</span>
                                    <select class="form-select d-none" id="promotion-type-new${p.id}">
                                        <option value="19" ${p.type.id == 19 ? "selected" : ""}>Thanh toán</option>
                                        <option value="20" ${p.type.id == 20 ? "selected" : ""}>Sản phẩm</option>
                                        <option value="21" ${p.type.id == 21 ? "selected" : ""}>Giải thưởng</option>
                                    </select>
                                </td>
                                <td>
                                    <span id="promotion-quantity-old${p.id}">${p.quantity}</span>
                                    <input class="form-control d-none" id="promotion-quantity-new${p.id}" type="number" value="${p.quantity}" />
                                </td>
                                <td>
                                    <span id="promotion-percentpage-old${p.id}">${p.percentpage}</span>
                                    <input class="form-control d-none" id="promotion-percentpage-new${p.id}" type="number" value="${p.percentpage}" />
                                </td>
                                <td>
                                    <span id="promotion-vip-old${p.id}">${p.levelVip.levelName}</span>
                                    <select class="form-select d-none" id="promotion-vip-new${p.id}">
                                        <c:forEach items="${listUserLevels}" var="l">
                                            <option value="${l.id}" ${p.levelVip.id == l.id ? "selected" : ""}>${l.levelName}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td class="d-flex">
                                    <a class="m-2" id="promotion-edit${p.id}" href="javascript:;" onclick="editPromotion(${p.id})"><i class="fas fa-edit text-primary"></i></a>
                                    <a class="m-2 d-none" id="promotion-success${p.id}" href="javascript:;" onclick="savePromotion('${edited}/${p.id}', ${p.id})"><i class="fas fa-check-circle text-success"></i></a>
                                    <a class="m-2 d-none" id="promotion-danger${p.id}" href="javascript:;" onclick="cancelPromotion(${p.id})"><i class="fas fa-times-circle text-danger"></i></a>
                                    <a class="m-2" href="javascript:;" onclick="deletedItem('${deleted}/${p.id}', ${p.id})">
                                        <i class='fas fa-trash text-danger'></i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="row">
                <div class="col-md-6 align-self-center">
                    <p id="dataTable_info" class="dataTables_info" role="status" aria-live="polite"></p>
                </div>
                <div class="col-md-6">
                    <nav class="d-lg-flex justify-content-lg-end dataTables_paginate paging_simple_numbers">
                        <ul class="pagination">
                            <c:if test="${page.totalPage > 1}">
                                <c:choose>
                                    <c:when test="${page.page > 1}">
                                        <li class="page-item">
                                            <a class="page-link" href="${promotion}?page=${page.page - 1}">«</a>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="page-item disabled">
                                            <span class="page-link">«</span>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                                <c:forEach begin="1" end="${page.totalPage}" var="item" varStatus="loop">
                                    <c:set var="pageParam" value="page=${loop.index}" />
                                    <li class="page-item${loop.index == page.page ? ' active' : ''}">
                                        <a class="page-link" href="${promotion}?${pageParam}">${loop.index}</a>
                                    </li>
                                </c:forEach>
                                <c:choose>
                                    <c:when test="${page.page < page.totalPage}">
                                        <li class="page-item">
                                            <a class="page-link" href="${promotion}?page=${page.page + 1}">»</a>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="page-item disabled">
                                            <span class="page-link">»</span>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="js-modal-edit">
    <div class="modal-container-black js-modal-container-edit">
        <div class="js-modal-close-edit">x</div>
        <header class="modal-header-black">
            <span><i class="fas fa-edit"></i> Thêm khuyến mãi</span>
        </header>
        <div class="modal-body">
            <div class="row">
                <div class="col-lg-4 col-md-4 col-sm-6 col-12">
                    <div class="modal-img-black" id="modal-account-img-edit">

                    </div>
                </div>
                <div class="col-lg-7 col-md-7 col-sm-6 col-12">
                    <div class="modal-content" id="modal-account-about-edit">

                    </div>
                    <div class="modal-content">
                        <div class="input-group" style="margin: 5px 0;"><span class="input-group-text">Level: </span>
                            <select id="add-vip-promotion" class="form-select">
                                <c:forEach items="${listUserLevels}" var="l">
                                    <option value="${l.id}">${l.levelName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="m-3" id="change-profile-user">

        </div>
    </div>
</div>