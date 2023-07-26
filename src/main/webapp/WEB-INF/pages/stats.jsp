<%-- 
    Document   : stats
    Created on : Apr 15, 2023, 1:16:38 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<h1 class="text-center text-success">THỐNG KÊ BÁO CÁO</h1>

<div class="row">
    <div class="col-md-7">
        <table class="table table-hover">
            <tr>
                <th>Id</th>
                <th>Tên danh mục</th>
                <th>Số lượng sản phẩm</th>
            </tr>
            <c:forEach items="${cateStats}" var="c">
                <tr>
                    <td>${c[0]}</td>
                    <td>${c[1]}</td>
                    <td>${c[2]}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div class="col-md-5">
         <canvas id="myCateChart"></canvas>
    </div>
</div>

<hr />

<div class="row">
    <div class="col-md-7">
        <table class="table table-hover">
            <tr>
                <th>Id</th>
                <th>Tên sản phẩm</th>
                <th>Doanh thu</th>
            </tr>
            <c:forEach items="${revenues}" var="r">
                <tr>
                    <td>${r[0]}</td>
                    <td>${r[1]}</td>
                    <td>${r[2]} VNĐ</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div class="col-md-5">
         <canvas id="myRevenueChart"></canvas>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="<c:url value="/js/stats.js" />"></script>
<script>
    let data1 = [], label1 = [];
    <c:forEach items="${cateStats}" var="c">
        label1.push('${c[1]}')
        data1.push(${c[2]});
    </c:forEach>
        
    let data2 = [], label2 = [];
    <c:forEach items="${revenues}" var="c">
        label2.push('${c[1]}')
        data2.push(${c[2]});
    </c:forEach>
    
    window.onload = function() {
        drawChart(data1, label1, "Số lượng");
        drawChart(data2, label2, "Doanh thu", "bar", "myRevenueChart");
    }
</script>