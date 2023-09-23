/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.handlers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

/**
 *
 * @author admin
 */
@Component
public class LogoutSuccessHandler implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication a) {
        request.getSession().removeAttribute("currentUser");
        try {
            response.sendRedirect(String.format("%s/login", request.getContextPath()));
        } catch (IOException ex) {
            Logger.getLogger(LogoutSuccessHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
