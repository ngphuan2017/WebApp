<%-- 
    Document   : vnpay-return
    Created on : Jan 9, 2024, 11:08:56 AM
    Author     : phuan
--%>

<%@page import="com.annp.configs.VNPayConfig"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.nio.charset.StandardCharsets"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%
    //Begin process return from VNPAY
    Map fields = new HashMap();
    for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
        String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
        String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
        if ((fieldValue != null) && (fieldValue.length() > 0)) {
            fields.put(fieldName, fieldValue);
        }
    }
    String vnp_SecureHash = request.getParameter("vnp_SecureHash");
    if (fields.containsKey("vnp_SecureHashType")) {
        fields.remove("vnp_SecureHashType");
    }
    if (fields.containsKey("vnp_SecureHash")) {
        fields.remove("vnp_SecureHash");
    }
    String signValue = VNPayConfig.hashAllFields(fields);

%>

<div class="container text-center p-3">
    <div class="header clearfix">
        <h3 class="text-muted">KẾT QUẢ THANH TOÁN</h3>
    </div>
    <div class="table-responsive">
        <table class="table table-hover">
            <tbody>
                <tr>
                    <td>Mã giao dịch thanh toán:</td>
                    <td>${vnp_TxnRef}</td>
                </tr>
                <tr>
                    <td>Mô tả giao dịch:</td>
                    <td>${vnp_OrderInfo}</td>
                </tr>
                <tr>
                    <td>Số tiền:</td>
                    <td>${vnp_Amount} VNĐ</td>
                </tr>
                <tr>
                    <td>Mã lỗi thanh toán:</td>
                    <td>${vnp_ResponseCode}</td>
                </tr>
                <tr>
                    <td>Mã giao dịch tại CTT VNPAY-QR:</td>
                    <td>${vnp_TransactionNo}</td>
                </tr>
                <tr>
                    <td>Mã ngân hàng thanh toán:</td>
                    <td>${vnp_BankCode}</td>
                </tr>
                <tr>
                    <td>Thời gian thanh toán:</td>
                    <td>${vnp_PayDate}</td>
                </tr>
                <tr>
                    <td>Tình trạng giao dịch:</td>
                    <td><% if (signValue.equals(vnp_SecureHash)) {
                            if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
                                out.print("Thành công");
                            } else {
                                out.print("Không thành công");
                            }

                        } else {
                            out.print("Chữ ký không hợp lệ");
                        }
                        %>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <p>
        &nbsp;
    </p>
</div> 