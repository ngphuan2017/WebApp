<%--
  Created by IntelliJ IDEA.
  User: phuan
  Date: 08/07/2023
  Time: 3:13 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="se" uri="http://www.springframework.org/security/tags"  %>

<c:url value="/register" var="register" />
<c:url value="/admin/customer-management" var="account" />
<c:url value="/admin/api/customer-management" var="customered" />
<c:url value="/admin/api/customer-management/deleted" var="deleted" />
<c:url value="/admin/api/customer-management/edited" var="edited" />
<c:url value="/admin/api/users/level" var="leveled"/>

<div class="container-fluid">
    <h3 class="text-dark mb-4">Quản lý tài khoản</h3>
    <div class="card shadow">
        <div class="card-header py-3">
            <div class="row">
                <div class="col-md-6">
                    <p class="text-primary m-0 fw-bold">Thông tin người dùng</p>
                </div>
                <div class="col-md-6">
                    <div class="text-md-end">
                        <a href="${register}" class="btn btn-outline-success">Thêm tài khoản</a>
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
                            <th></th>
                            <th>STT</th>
                            <th>Họ và tên</th>
                            <th>Loại tài khoản</th>
                            <th>Số điện thoại</th>
                            <th>Giới tính</th>
                            <th>Cấp độ</th>
                            <th>Rank</th>
                            <th>Trạng thái</th>
                            <th>Ngày tạo</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${users}" var="u" varStatus="loop">
                            <tr>
                                <td><a class="js-add-cart" href="javascript:;" onclick="accountView('${customered}/${u.id}', '${leveled}/${u.exp}')"><i class='fas fa-eye text-info'></i></a></td>
                                <td>${loop.index + 1}</td>
                                <td><img class="rounded-circle me-2" width="30" height="30" src="${u.avatar}">${u.fullname}</td>
                                <td>${u.userRole.id == 1 ? "<span class='text-danger'>Quản trị</span>" : u.userRole.id == 2 ? "<span class='text-success'>Quản lý</span>" : "Người dùng"}</td>
                                <td>${u.phone}</td>
                                <td>${u.gender == 1 ? "Nam" : u.gender == 2 ? "Nữ" : "Khác"}</td>
                                <td>
                                    <c:set var="foundLevel" value="false" />
                                    <c:forEach items="${listUserLevels}" var="exp" varStatus="status">
                                        <c:if test="${u.exp <= exp.requiredExp && status.index > 0 && foundLevel eq false}">
                                            <span class="level-${u.id}" style="color: ${listUserLevels[status.index - 1].rankColor}">
                                                ${listUserLevels[status.index - 1].levelName}
                                            </span>
                                            <c:set var="foundLevel" value="true" />
                                        </c:if>
                                    </c:forEach>
                                </td>
                                <td>
                                    <c:set var="foundLevel" value="false" />
                                    <c:forEach items="${listUserLevels}" var="exp" varStatus="status">
                                        <c:if test="${u.exp <= exp.requiredExp && status.index > 0 && foundLevel eq false}">
                                            <img class="rounded" src="${listUserLevels[status.index - 1].rankImg}" style="width: 30px; height: 30px;" alt="rank" />
                                            <c:set var="foundLevel" value="true" />
                                        </c:if>
                                    </c:forEach>
                                    <span class="d-none required-exp-${u.id}">${u.exp}</span>
                                </td>
                                <td class="text-order-name" id="customer-status${u.id}">
                                    <c:set var="cssClass" value=""/>
                                    <c:choose>
                                        <c:when test="${u.userstatus.id == 1}">
                                            <c:set var="cssClass" value="active"/>
                                        </c:when>
                                        <c:when test="${u.userstatus.id == 2}">
                                            <c:set var="cssClass" value="warning"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="cssClass" value="danger"/>
                                        </c:otherwise>
                                    </c:choose>
                                    <span class="text-customer-${cssClass}">${u.userstatus.statusname}</span>
                                </td>
                                <td class="create-date">${u.createdDate}</td>
                                <td>
                                    <se:authorize access="hasRole('ROLE_ADMIN')">
                                        <a class="m-2 js-add-cart-edit" href="javascript:;" onclick="editCustomer('${customered}/${u.id}', '${edited}/${u.id}')">
                                            <i class="fas fa-edit text-primary"></i>
                                        </a>
                                        <a class="m-2" href="javascript:;" onclick="deleteCustomer('${deleted}/${u.id}', ${u.id})">
                                            <i class='fas fa-trash text-danger'></i>
                                        </a>
                                    </se:authorize>
                                    <se:authorize access="!hasRole('ROLE_ADMIN')">
                                        <c:choose>
                                            <c:when test="${u.userRole.id == 3}">
                                                <a class="m-2 js-add-cart-edit" href="javascript:;" onclick="editCustomer('${customered}/${u.id}', '${edited}/${u.id}')">
                                                    <i class="fas fa-edit text-primary"></i>
                                                </a>
                                                <a class="m-2" href="javascript:;" onclick="deleteCustomer('${deleted}/${u.id}', ${u.id})">
                                                    <i class='fas fa-trash text-danger'></i>
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <a class="m-2" href="javascript:;" onclick="permissionAccount()">
                                                    <i class="fas fa-edit text-primary"></i>
                                                </a>
                                                <a class="m-2" href="javascript:;" onclick="permissionAccount()">
                                                    <i class='fas fa-trash text-danger'></i>
                                                </a>
                                            </c:otherwise>
                                        </c:choose>
                                    </se:authorize>

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
                                            <a class="page-link" href="${account}?page=${page.page - 1}">«</a>
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
                                        <a class="page-link" href="${account}?${pageParam}">${loop.index}</a>
                                    </li>
                                </c:forEach>
                                <c:choose>
                                    <c:when test="${page.page < page.totalPage}">
                                        <li class="page-item">
                                            <a class="page-link" href="${account}?page=${page.page + 1}">»</a>
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

<div class="js-modal-edit">
    <div class="modal-container-black js-modal-container-edit">
        <div class="js-modal-close-edit">x</div>
        <header class="modal-header-black">
            <span><i class="fas fa-user-edit"></i> Chỉnh sửa thông tin</span>
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
                    <se:authorize access="hasRole('ROLE_ADMIN')">
                        <div class="modal-content" id="modal-role-admin-edit">

                        </div>
                    </se:authorize>
                </div>
            </div>
        </div>
        <div class="m-3" id="change-profile-user">

        </div>
        <div class="footer-modal-black" id="modal-account-title-edit">

        </div>
    </div>
</div>
