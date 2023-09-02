package com.annp.service;

import com.annp.pojo.Facebook;
import com.annp.pojo.Google;
import com.annp.pojo.Users;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    
    public Users getUserById(int id);
    public Users getUserByUsername(String username);
    boolean addOrUpdateUser(Users user);
    public boolean getByUsername(String username);
    boolean updateUser(Users user);
    boolean updateProfileUser(Users user);
    boolean addUserGoogle(Google google);
    public Users getUserByGoogleId(String googleId);
    boolean addUserFacebook(Facebook facebook);
    public Users getUserByFacebookId(String facebookId);
    public boolean verifyRecaptcha(String captchaResponse);
    public List<Users> getUserByEmail(String email);
    public boolean sendConfirmationCodeByEmail(String email);
}
