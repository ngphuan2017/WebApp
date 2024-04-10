/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.annp.service;

import com.annp.configs.MomoConfig;
import com.annp.dto.PaymentResponseDto;
import com.annp.enums.RequestType;

/**
 *
 * @author phuan
 */
public interface PaymentService {
    
    public PaymentResponseDto createPayment(MomoConfig environment, String orderId, String requestId, String amount, 
            String orderInfo, String extraData, RequestType requestType, Boolean autoCapture) throws Exception;
    
}
