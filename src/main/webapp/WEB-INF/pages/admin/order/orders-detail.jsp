<%-- 
    Document   : orders-detail
    Created on : Apr 10, 2024, 5:00:18 PM
    Author     : phuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url value="/admin/orders-detail-management" var="order" />
<c:url value="/admin/api/customer-management" var="customered" />
<c:url value="/admin/api/users/level" var="leveled"/>
<c:url value="/admin/api/orders-detail-management" var="ordered" />
<c:url value="/admin/api/order-management/updated" var="updated" />
<c:url value="/admin/api/orders-detail-management/deleted" var="deleted" />

<div class="container-fluid">
    <h3 class="text-dark mb-4">Quản lý các kiện hàng</h3>
    <div class="card shadow">
        <div class="card-header py-3">
            <p class="text-primary m-0 fw-bold">Thông tin kiện hàng</p>
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
                            <th></th>
                            <th>STT</th>
                            <th>Sản phẩm</th>
                            <th>Đơn giá</th>
                            <th>Số lượng</th>
                            <th>Thành tiền</th>
                            <th>Ngày mua</th>
                            <th>Trạng thái</th>
                            <th>Ngày cập nhật</th>
                            <th>Người cập nhật</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${orderDetail}" var="o" varStatus="loop">
                            <tr>
                                <td>
                                    <span class="d-none required-exp-${o.orderId.userid.id}">${o.orderId.userid.exp}</span>
                                    <a class="m-1 js-add-user" href="javascript:;" onclick="accountView('${customered}/${o.orderId.userid.id}', '${leveled}/${o.orderId.userid.exp}')"><i class='fas fa-user-tag text-primary'></i></a>
                                    <a class="m-1 js-add-cart" href="javascript:;" onclick="productOrderView('${ordered}/${o.productId.id}')"><i class='fas fa-eye text-info'></i></a>
                                </td>
                                <td>${loop.index + 1}</td>
                                <td class="text-order-name"><img class="rounded-circle me-2" width="30" height="30" src="${o.productId.image}">${o.productId.name}</td>
                                <td class="currency"><span class="money">${o.price}</span></td>
                                <td>${o.number}</td>
                                <td class="currency"><span class="money">${o.price * o.number}</span></td>
                                <td class="create-date">${o.createdDate}</td>
                                <td class="text-order-name" id="order-status${o.id}">
                                    <span id="order-status-old${o.id}" class="text-customer-${o.orderstatus.id == 9 ? "warning" : o.orderstatus.id == 10 ? "primary" : o.orderstatus.id == 11 ? "active" : "danger"}">${o.orderstatus.statusname}</span>
                                    <select class="form-select d-none" id="order-status-new${o.id}" onchange="updateOrderStatus('${updated}/${o.id}', ${o.id})">
                                        <c:forEach items="${orderDetailStatus}" var="s">
                                            <option value="${s.id}" ${o.orderstatus.id == s.id ? "selected" : ""}>${s.statusname}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td class="create-date">${o.updatedDate}</td>
                                <td class="text-user-name">${o.updatedBy.fullname}</td>
                                <td>
                                    <a class="m-2" id="order-edit${o.id}" href="javascript:;" onclick="showStatus(${o.id})"><i class="fas fa-edit text-primary"></i></a>
                                    <a class="m-2 d-none" id="order-cancel${o.id}" href="javascript:;" onclick="cancelStatus(${o.id})"><i class="fas fa-times-circle text-danger"></i></a>
                                    <a class="m-2" href="javascript:;" onclick="deleteOrder('${deleted}/${o.id}', ${o.id})">
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
                                            <a class="page-link" href="${order}?page=${page.page - 1}">«</a>
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
                                        <a class="page-link" href="${order}?${pageParam}">${loop.index}</a>
                                    </li>
                                </c:forEach>
                                <c:choose>
                                    <c:when test="${page.page < page.totalPage}">
                                        <li class="page-item">
                                            <a class="page-link" href="${order}?page=${page.page + 1}">»</a>
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

<div class="js-modal">
    <div class="modal-container-black js-modal-container">
        <div class="js-modal-close">x</div>
        <header class="modal-header-black">
            <span><i class="fas fa-address-card"></i> Thông tin</span>
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
