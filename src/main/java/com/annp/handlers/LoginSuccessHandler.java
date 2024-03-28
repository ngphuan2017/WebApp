/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.handlers;

import com.annp.pojo.Notification;
import com.annp.pojo.Users;
import com.annp.service.NotificationService;
import com.annp.service.UserService;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        Date currentDate = new Date(); // Lấy ngày hiện tại
        if (!isSameDay(u.getUpdatedDate(), currentDate)) {
            Notification n = new Notification();
            n.setUserId(u);
            u.setExp(u.getExp() + 5);
            u.setNotification(u.getNotification() + 1);
            u.setWheel(u.getWheel() + 5);
            this.notificationService.addNotification(n);
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
