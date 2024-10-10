package com.annp.service;

import com.annp.pojo.Facebook;
import com.annp.pojo.Google;
import com.annp.pojo.Users;
import com.annp.pojo.Verification;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    
    public List<Users> getUsers(Map<String, String> params, int start, int limit);
    public List<Object[]> getTopUsers(int limit);
    public List<Users> getUsersLogin(int limit);
    public Users getUserById(int id);
    public Users getUserByUsername(String username);
    public boolean addOrUpdateUser(Users user);
    public boolean getByUsername(String username);
    public boolean updateUser(Users user);
    public boolean updateProfileUser(Users user);
    public boolean addUserGoogle(Google google);
    public Users getUserByGoogleId(String googleId);
    public boolean addUserFacebook(Facebook facebook);
    public Users getUserByFacebookId(String facebookId);
    public boolean verifyRecaptcha(String captchaResponse);
    public List<Users> getUserByEmail(String email);
    public boolean sendTicketToEmail(int userId, String email, String baseUrl);
    public boolean sendOtpCodeToEmail(Verification verification);
    public boolean changePassword(String password, Users user);
    public Users getUserByTicket(String ticket);
    public Users getUserAccountById(int id);
    public boolean deleteCustomer(int id);
}
