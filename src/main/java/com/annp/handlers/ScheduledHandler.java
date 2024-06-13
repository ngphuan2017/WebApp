/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.handlers;

import com.annp.pojo.Notification;
import com.annp.pojo.Verification;
import com.annp.service.NotificationService;
import com.annp.service.VerificationService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author phuan
 */
@Component
public class ScheduledHandler {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private VerificationService verificationService;

    @Async
    @Scheduled(cron = "*/60 * * * * *")
    public void performAsyncScheduledTaskUsingCron() {
        int day = 30;    // Lấy danh sách được tạo cách đây [day] ngày trở về trước.
        List<Notification> notifications = this.notificationService.getNotificationsByThanDay(day);
        List<Verification> verifications = this.verificationService.getVerificationsByThanDay(day);
        for (Notification n : notifications) {
            this.notificationService.deleteNotification(n.getId());
        }
        for (Verification v : verifications) {
            this.verificationService.deleteVerification(v.getId());
        }
    }
}
