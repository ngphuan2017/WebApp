/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.handlers;

import com.annp.dto.GeoIPDto;
import com.annp.pojo.ClientInfo;
import com.annp.pojo.Notification;
import com.annp.pojo.Users;
import com.annp.service.NotificationService;
import com.annp.service.UserService;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.annp.utils.GeoIPUtil;
import com.annp.utils.IPAddressUtil;
import com.annp.utils.UserAgentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 *
 * @author admin
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService notificationService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication a) throws IOException, ServletException {
        Users u = this.userService.getUserByUsername(a.getName());
        // Lấy địa chỉ IP
        String clientIp = IPAddressUtil.getClientIp(request);
        // Lấy thông tin vị trí và ISP
        GeoIPDto geoIP = GeoIPUtil.getGeoIP(clientIp);
        String location = geoIP.getLocation();
        String isp = geoIP.getIsp();
        // Lấy thông tin User-Agent
        String userAgentString = request.getHeader("User-Agent");
        String deviceInfo = UserAgentUtil.getDeviceInfo(userAgentString);
        // Lấy thời gian hiện tại
        Date currentDate = new Date();
        if (!isSameDay(u.getUpdatedDate(), currentDate)) {
            Notification n = new Notification();
            n.setId(0);
            n.setName("Thông báo từ hệ thống");
            n.setDescription("🎁Bạn nhận được 5 lượt vòng quay may mắn và 5 điểm kinh nghiệm");
            n.setUserId(u);
            u.setExp(u.getExp() + 5);
            u.setNotification(u.getNotification() + 1);
            u.setWheel(u.getWheel() + 5);
            this.notificationService.addNotification(n);
            ClientInfo c = new ClientInfo();
            c.setId(0);
            c.setUserId(u);
            c.setIp(clientIp);
            c.setIsp(isp);
            c.setDevice(deviceInfo);
            c.setLocation(location);
            c.setTimestamp(currentDate);
            this.userService.saveClientInfo(c);
        } else {
            ClientInfo checkClient = this.userService.getClientInfoByUserId(u.getId(), clientIp);
            if (checkClient != null) {
                checkClient.setTimestamp(new Date());
                this.userService.updateClientInfo(checkClient);
            } else {
                ClientInfo c = new ClientInfo();
                c.setId(0);
                c.setUserId(u);
                c.setIp(clientIp);
                c.setIsp(isp);
                c.setDevice(deviceInfo);
                c.setLocation(location);
                c.setTimestamp(currentDate);
                this.userService.saveClientInfo(c);
            }
        }
        u.setUpdatedDate(currentDate);
        this.userService.updateUser(u);
        request.getSession().setAttribute("currentUser", u);
        if (u.getUserRole().getId() == 1 || u.getUserRole().getId() == 2) {
            response.sendRedirect("admin");
        } else {
            response.sendRedirect("/");
        }
    }

    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

}
