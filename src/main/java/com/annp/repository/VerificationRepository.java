/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.annp.repository;

import com.annp.pojo.Verification;

/**
 *
 * @author phuan
 */
public interface VerificationRepository {
    boolean addOrUpdateOtpCode(Verification verification);
    Verification getVerificationByEmail(String email);
}
