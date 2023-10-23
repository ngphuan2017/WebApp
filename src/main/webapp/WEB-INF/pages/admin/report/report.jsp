<%-- 
    Document   : report
    Created on : Oct 12, 2023, 11:23:22 AM
    Author     : phuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="se" uri="http://www.springframework.org/security/tags"  %>

<c:url value="/admin/api/report-management/edited" var="edited" />
<c:url value="/admin/api/report-management/deleted" var="deleted" />

<div class="container-fluid">
    <h3 class="text-dark mb-4">Danh sách báo cáo vi phạm</h3>
    <div class="card shadow">
        <div class="card-header py-3">
            <p class="text-primary m-0 fw-bold">Thông tin báo cáo</p>
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
                            <th>Người bị báo cáo</th>
                            <th>Nội dụng bình luận</th>
                            <th>Số lượt bị báo cáo</th>
                            <th>Trạng thái xử lý</th>
                            <th>Ngày bị báo cáo gần nhất</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${reports}" var="r" varStatus="loop">
                            <tr id="report-${r.id}">
                                <td>${loop.index + 1}</td>
                                <td class="text-order-name">${r.userid.fullname}</td>
                                <td class="text-report-content">${r.content}</td>
                                <td>${r.reportCount}</td>
                                <td id="report-status${r.id}"><span class="text-customer-${r.reportstatus.id == 14 ? "danger" : r.reportstatus.id == 15 ? "primary" : "active"}">${r.reportstatus.statusname}</span></td>
                                <td class="create-date">${r.updatedDate != null ? r.updatedDate : r.createdDate}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${r.reportstatus.id == 14}">
                                            <a class="m-2" href="javascript:;" onclick="agreeReport('${edited}/${r.id}', ${r.id})"><i class="far fa-check-circle text-warning"></i></a>
                                            <a class="m-2" href="javascript:;" onclick="refuseReport('${edited}/${r.id}', ${r.id})"><i class="far fa-times-circle text-info"></i></a>
                                            </c:when>
                                            <c:otherwise>
                                            <a class="m-2" href="javascript:;" onclick="warningReport()"><i class="far fa-check-circle text-warning"></i></a>
                                            <a class="m-2" href="javascript:;" onclick="warningReport()"><i class="far fa-times-circle text-info"></i></a>
                                            </c:otherwise>
                                        </c:choose>
                                        <se:authorize access="hasRole('ROLE_ADMIN')">
                                        <a class="m-2" href="javascript:;" onclick="deletedItem('${deleted}/${r.id}', ${r.id})">
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
                    <p id="dataTable_info" class="dataTables_info" role="status" aria-live="polite">Showing 1 to 10 of 27</p>
                </div>
                <div class="col-md-6">
                    <nav class="d-lg-flex justify-content-lg-end dataTables_paginate paging_simple_numbers">
                        <ul class="pagination">
                            <li class="page-item disabled"><a class="page-link" aria-label="Previous" href="#"><span aria-hidden="true">«</span></a></li>
                            <li class="page-item active"><a class="page-link" href="#">1</a></li>
                            <li class="page-item"><a class="page-link" href="#">2</a></li>
                            <li class="page-item"><a class="page-link" href="#">3</a></li>
                            <li class="page-item"><a class="page-link" aria-label="Next" href="#"><span aria-hidden="true">»</span></a></li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</div>