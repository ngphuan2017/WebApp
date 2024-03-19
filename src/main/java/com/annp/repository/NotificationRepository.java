/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.annp.repository;

import com.annp.pojo.Notification;
import com.annp.pojo.Users;
import java.util.List;

/**
 *
 * @author phuan
 */
public interface NotificationRepository {
    List<Notification> getNotificationsByUserId(Users userId);
    boolean addNotification(Notification n);
}
