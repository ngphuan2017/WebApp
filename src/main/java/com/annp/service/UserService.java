package com.annp.service;

import com.annp.pojo.Facebook;
import com.annp.pojo.Google;
import com.annp.pojo.Users;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    
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
    public boolean sendCodeToEmail(int userId, String email, String baseUrl);
    public boolean changePassword(String password, Users user);
    public Users getUserByTicket(String ticket);
    public Users getUserAccountById(int id);
}
