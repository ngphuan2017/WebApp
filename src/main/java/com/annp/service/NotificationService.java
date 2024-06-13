/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.annp.service;

import com.annp.pojo.Notification;
import com.annp.pojo.Users;
import java.util.List;

/**
 *
 * @author phuan
 */
public interface NotificationService {
    List<Notification> getNotificationsByUserId(Users userId);
    boolean addNotification(Notification n);
    Notification getNotificationById(int id);
    List<Notification> getNotificationsByThanDay(int day);
    boolean deleteNotification(int id);
}
