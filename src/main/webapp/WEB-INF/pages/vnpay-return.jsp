<%-- 
    Document   : vnpay-return
    Created on : Jan 9, 2024, 11:08:56 AM
    Author     : phuan
--%>

<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="com.annp.configs.VNPayConfig"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.nio.charset.StandardCharsets"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Enumeration"%>
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

    String vnpAmountStr = request.getParameter("vnp_Amount");
    double vnpAmount = Double.parseDouble(vnpAmountStr)/100;
    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
    String formattedAmount = numberFormat.format(vnpAmount);
    
    String vnp_PayDate = request.getParameter("vnp_PayDate");
    SimpleDateFormat sdfInput = new SimpleDateFormat("yyyyMMddHHmmss");
    Date paymentDate = sdfInput.parse(vnp_PayDate);
    SimpleDateFormat sdfOutput = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
    String formattedDate = sdfOutput.format(paymentDate);

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
                    <td><%=request.getParameter("vnp_TxnRef")%></td>
                </tr>
                <tr>
                    <td>Mô tả giao dịch:</td>
                    <td><%=request.getParameter("vnp_OrderInfo")%></td>
                </tr>
                <tr>
                    <td>Số tiền:</td>
                    <td><%= formattedAmount%> VNĐ</td>
                </tr>
                <tr>
                    <td>Mã lỗi thanh toán:</td>
                    <td><%=request.getParameter("vnp_ResponseCode")%></td>
                </tr>
                <tr>
                    <td>Mã giao dịch tại CTT VNPAY-QR:</td>
                    <td><%=request.getParameter("vnp_TransactionNo")%></td>
                </tr>
                <tr>
                    <td>Mã ngân hàng thanh toán:</td>
                    <td><%=request.getParameter("vnp_BankCode")%></td>
                </tr>
                <tr>
                    <td>Thời gian thanh toán:</td>
                    <td><%= formattedDate %></td>
                </tr>
                <tr>
                    <td>Tình trạng giao dịch:</td>
                    <td><%
                        if (signValue.equals(vnp_SecureHash)) {
                            if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
                                out.print("Thành công");
                            } else {
                                out.print("Không thành công");
                            }

                        } else {
                            out.print("invalid signature");
                        }
                        %></td>
                </tr>
            </tbody>
        </table>
    </div>
    <p>
        &nbsp;
    </p>
</div> 