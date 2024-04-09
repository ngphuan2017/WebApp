<%-- 
    Document   : momo-return
    Created on : Apr 9, 2024, 4:51:24 PM
    Author     : phuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="container text-center p-3">
    <div class="header clearfix">
        <h3 class="text-muted">KẾT QUẢ THANH TOÁN</h3>
    </div>
    <div class="table-responsive">
        <table class="table table-hover">
            <tbody>
                <tr>
                    <td>Mã đơn hàng:</td>
                    <td>${orderId}</td>
                </tr>
                <tr>
                    <td>Mô tả giao dịch:</td>
                    <td>${orderInfo}</td>
                </tr>
                <tr>
                    <td>Số tiền:</td>
                    <td>${amount} VNĐ</td>
                </tr>
                <tr>
                    <td>Mã lỗi thanh toán:</td>
                    <td>${resultCode}</td>
                </tr>
                <tr>
                    <td>Loại thanh toán:</td>
                    <td>${payType}</td>
                </tr>
                <tr>
                    <td>Thời gian thanh toán:</td>
                    <td>${responseTime}</td>
                </tr>
                <tr>
                    <td>Tình trạng giao dịch:</td>
                    <td>${message}</td>
                </tr>
            </tbody>
        </table>
    </div>
    <p>
        &nbsp;
    </p>
</div> 
