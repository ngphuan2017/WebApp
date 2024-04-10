/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.service.impl;

import com.annp.configs.MomoConfig;
import com.annp.dto.PaymentResponseDto;
import com.annp.enums.RequestType;
import com.annp.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 *
 * @author phuan
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private Environment env;

    @Override
    public PaymentResponseDto createPayment(MomoConfig environment, String orderId, String requestId, String amount,
            String orderInfo, String extraData, RequestType requestType, Boolean autoCapture) throws Exception {

        PaymentResponseDto captureWalletMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, amount,
                orderInfo, env.getProperty("momo.api.redirect.return"),
                env.getProperty("momo.api.redirect.notify"), extraData, RequestType.CAPTURE_WALLET, Boolean.TRUE);
        return captureWalletMoMoResponse;
    }

}
