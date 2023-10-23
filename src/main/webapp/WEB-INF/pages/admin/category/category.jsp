<%-- 
    Document   : category
    Created on : Oct 11, 2023, 2:17:33 PM
    Author     : phuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/admin/api/category-management/edited" var="edited" />
<c:url value="/admin/api/category-management/deleted" var="deleted" />

<div class="container-fluid">
    <h3 class="text-dark mb-4">Phân loại sản phẩm</h3>
    <div class="card shadow">
        <div class="card-header py-3">
            <div class="row">
                <div class="col-md-6">
                    <p class="text-primary m-0 fw-bold">Thông tin danh mục</p>
                </div>
                <div class="col-md-6">
                    <div class="text-md-end">
                        <button type="button" class="btn btn-outline-success">Thêm phân loại</button>
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
                            <th>Phân loại</th>
                            <th>Danh mục</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${categorys}" var="c" varStatus="loop">
                            <tr id="category-${c.id}">
                                <td>${loop.index + 1}</td>
                                <td>
                                    <span id="categorysub-name-old${c.id}">${c.name}</span>
                                    <input class="form-control d-none" id="categorysub-name-new${c.id}" type="text" value="${c.name}" />
                                </td>
                                <td>
                                    <span id="category-name-old${c.id}">${c.categoryId.name}</span>
                                    <select class="form-select d-none" id="category-name-new${c.id}">
                                        <c:forEach items="${listCategory}" var="cate">
                                            <option value="${cate.id}" ${cate.id == c.categoryId.id ? "selected" : ""}>${cate.name}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <a class="m-2" id="categorysub-edit${c.id}" href="javascript:;" onclick="editCategorySub(${c.id})"><i class="fas fa-edit text-primary"></i></a>
                                    <a class="m-2 d-none" id="categorysub-success${c.id}" href="javascript:;" onclick="saveCategorySub('${edited}/${c.id}', ${c.id})"><i class="fas fa-check-circle text-success"></i></a>
                                    <a class="m-2 d-none" id="categorysub-danger${c.id}" href="javascript:;" onclick="cancelCategorySub(${c.id})"><i class="fas fa-times-circle text-danger"></i></a>
                                    <a class="m-2" href="javascript:;" onclick="deletedItem('${deleted}/${c.id}', ${c.id})">
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