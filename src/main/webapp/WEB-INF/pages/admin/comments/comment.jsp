<%-- 
    Document   : comment
    Created on : May 7, 2024, 2:47:28 PM
    Author     : phuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="se" uri="http://www.springframework.org/security/tags"  %>

<c:url value="/admin/comment-management" var="comment" />
<c:url value="/admin/api/comment-management/deleted" var="deleted" />

<div class="container-fluid">
    <h3 class="text-dark mb-4">Danh sách bình luận / đánh giá</h3>
    <div class="card shadow">
        <div class="card-header py-3">
            <p class="text-primary m-0 fw-bold">Thông tin bình luận</p>
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
                            <th>Người bình luận</th>
                            <th>Nội dụng bình luận</th>
                            <th>Bình luận sản phẩm</th>
                            <th>Ngày bình luận</th>
                            <th>Ngày chỉnh sửa</th>
                            <th>Số lượt yêu thích</th>
                            <th>Số lượt không thích</th>
                            <th>[Reply]</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${comments}" var="c" varStatus="loop">
                            <tr id="comment-${c.id}">
                                <td>${loop.index + 1}</td>
                                <td class="text-user-name"><img class="rounded-circle me-2" width="30" height="30" src="${c.userid.avatar}">${c.userid.fullname}</td>
                                <td class="text-report-content">${c.content}</td>
                                <td class="text-order-name">${c.productid.name}</td>
                                <td class="create-date">${c.createdDate}</td>
                                <td class="create-date">${c.updatedDate != null ? c.updatedDate : c.createdDate}</td>
                                <td>${c.likeCount}</td>
                                <td>${c.dislike}</td>
                                <td>0</td>
                                <td>
                                    <se:authorize access="hasRole('ROLE_ADMIN')">
                                        <a class="m-2" href="javascript:;" onclick="deletedItem('${deleted}/${c.id}', ${c.id})">
                                            <i class='fas fa-trash text-danger'></i>
                                        </a>
                                    </se:authorize>
                                    <se:authorize access="!hasRole('ROLE_ADMIN')">
                                        <a class="m-2" href="javascript:;" onclick="permissionAccount()">
                                            <i class='fas fa-trash text-danger'></i>
                                        </a>
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
                                            <a class="page-link" href="${comment}?page=${page.page - 1}">«</a>
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
                                        <a class="page-link" href="${comment}?${pageParam}">${loop.index}</a>
                                    </li>
                                </c:forEach>
                                <c:choose>
                                    <c:when test="${page.page < page.totalPage}">
                                        <li class="page-item">
                                            <a class="page-link" href="${comment}?page=${page.page + 1}">»</a>
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