/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.annp.repository;

import com.annp.pojo.Verification;
import java.util.List;

/**
 *
 * @author phuan
 */
public interface VerificationRepository {
    boolean addOrUpdateOtpCode(Verification verification);
    Verification getVerificationByEmail(String email);
    Verification getVerificationById(int id);
    List<Verification> getVerificationsByThanDay(int day); // Lấy danh sách có ngày tạo cách đây [day] hoặc lâu hơn.
    boolean deleteVerification(int id);
}
