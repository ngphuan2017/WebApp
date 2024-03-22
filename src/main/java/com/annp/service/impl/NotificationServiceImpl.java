/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.service.impl;

import com.annp.pojo.Notification;
import com.annp.pojo.Users;
import com.annp.repository.NotificationRepository;
import com.annp.service.NotificationService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author phuan
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public List<Notification> getNotificationsByUserId(Users userId) {
        return this.notificationRepository.getNotificationsByUserId(userId);
    }

    @Override
    public boolean addNotification(Notification n) {
        n.setId(0);
        n.setName("Thông báo từ hệ thống");
        n.setDescription("🎁Bạn nhận được 5 lượt vòng quay may mắn và 5 điểm kinh nghiệm");
        n.setCreatedDate(new Date());
        n.setUpdatedDate(new Date());
        return this.notificationRepository.addNotification(n);
    }

}
