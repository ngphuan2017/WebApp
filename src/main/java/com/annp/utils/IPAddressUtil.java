package com.annp.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPAddressUtil {

    public static String getClientIp(HttpServletRequest request) {
        String ipAddress = null;
        String unknown = "unknown";

        // Danh sách các header cần kiểm tra
        String[] headerCandidates = {
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_X_FORWARDED_FOR",
                "HTTP_X_FORWARDED",
                "HTTP_X_CLUSTER_CLIENT_IP",
                "HTTP_CLIENT_IP",
                "HTTP_FORWARDED_FOR",
                "HTTP_FORWARDED"
        };

        for (String header : headerCandidates) {
            String ipList = request.getHeader(header);
            if (ipList != null && ipList.length() != 0 && !unknown.equalsIgnoreCase(ipList)) {
                // Trường hợp header chứa nhiều địa chỉ IP (phân tách bằng dấu phẩy)
                String[] ips = ipList.split(",");
                for (String ip : ips) {
                    ip = ip.trim();
                    if (isValidIPAddress(ip) && !unknown.equalsIgnoreCase(ip)) {
                        return ip;
                    }
                }
            }
        }

        // Nếu không tìm thấy IP trong các header, lấy từ request.getRemoteAddr()
        ipAddress = request.getRemoteAddr();
        if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
            ipAddress = "0.0.0.0"; // Giá trị mặc định
        }

        return ipAddress;
    }

    private static boolean isValidIPAddress(String ip) {
        try {
            if (ip == null || ip.isEmpty()) {
                return false;
            }
            ip = ip.trim();

            // Kiểm tra địa chỉ IPv6
            if (ip.contains(":")) {
                InetAddress inetAddress = InetAddress.getByName(ip);
                return inetAddress instanceof Inet6Address;
            } else {
                // Kiểm tra địa chỉ IPv4
                String ipv4Pattern =
                        "^((25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.|$)){4}$";
                Pattern pattern = Pattern.compile(ipv4Pattern);
                Matcher matcher = pattern.matcher(ip);
                return matcher.matches();
            }
        } catch (Exception ex) {
            return false;
        }
    }
}
