/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.annp.service;

import com.annp.pojo.Status;
import java.util.List;

/**
 *
 * @author phuan
 */
public interface StatusService {
    Status getStatusById(int id);
    List<Status> getStatus();
    List<Status> getStatus(String columname);
}
