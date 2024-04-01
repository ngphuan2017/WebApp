/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.service.impl;

import com.annp.pojo.Verification;
import com.annp.repository.VerificationRepository;
import com.annp.service.VerificationService;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author phuan
 */
@Service
public class VerificationServiceImpl implements VerificationService {
    
    @Autowired
    private VerificationRepository verificationRepository;

    @Override
    public String randomOtp() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }

    @Override
    public boolean addOrUpdateOtpCode(Verification verification) {
        return this.verificationRepository.addOrUpdateOtpCode(verification);
    }

    @Override
    public Verification getVerificationByEmail(String email) {
        return this.verificationRepository.getVerificationByEmail(email);
    }

}
