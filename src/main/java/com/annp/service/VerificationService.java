/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.annp.service;

import com.annp.pojo.Verification;
import java.util.List;

/**
 *
 * @author phuan
 */
public interface VerificationService {
    String randomOtp();
    boolean addOrUpdateOtpCode(Verification verification);
    Verification getVerificationByEmail(String email);
    Verification getVerificationById(int id);
    List<Verification> getVerificationsByThanDay(int day);
    boolean deleteVerification(int id);
}
