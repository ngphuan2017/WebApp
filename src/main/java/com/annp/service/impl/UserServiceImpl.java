package com.annp.service.impl;

import com.annp.pojo.Facebook;
import com.annp.pojo.Frame;
import com.annp.pojo.Google;
import com.annp.pojo.Recaptcha;
import com.annp.pojo.Role;
import com.annp.pojo.Status;
import com.annp.pojo.Users;
import com.annp.pojo.Verification;
import com.annp.repository.UserRepository;
import com.annp.service.NotificationService;
import com.annp.service.UserService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Service("userDetailsService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private Environment env;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Không tồn tại!");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getUserRole().getPermission()));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public Users getUserByUsername(String username) {
        return this.userRepository.getUserByUsername(username);
    }

    @Override
    public boolean addOrUpdateUser(Users user) {
        try {
            String pass = user.getPassword().trim();
            user.setPassword(this.bCryptPasswordEncoder.encode(pass));
            user.setAddress(user.getWard() + " - " + user.getDistrict() + " - " + user.getCity());
            user.setUserstatus(new Status(1));
            String avatar = user.getAvatar();

            if (!user.getFile().isEmpty()) {
                Map res = null;
                try {
                    res = this.cloudinary.uploader().upload(user.getFile().getBytes(),
                            ObjectUtils.asMap("resource_type", "auto"));
                    user.setAvatar(res.get("secure_url").toString());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if (res != null) {
                    user.setAvatar((String) res.get("secure_url"));
                } else {
                    user.setAvatar(avatar);
                }
            }

            if (user.getId() == 0) {
                user.setExp(5);
                user.setWheel(5);
                user.setUserRole(new Role(3));
                user.setNotification(1);
                user.setCreatedDate(new Date());
                user.setUpdatedDate(new Date());
                user.setAvatarFrame(new Frame(1));
            }

            return this.userRepository.addOrUpdateUser(user);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean getByUsername(String username) {
        return this.userRepository.getByUsername(username);
    }

    @Override
    public boolean updateUser(Users user) {
        return this.userRepository.updateUser(user);
    }

    @Override
    public Users getUserById(int id) {
        return this.userRepository.getUserById(id);
    }

    @Override
    public boolean updateProfileUser(Users user) {
        try {
            Users u = getUserById(user.getId());

            if (user.getFile() != null && !user.getFile().isEmpty()) {
                String avatar = u.getAvatar();
                Map res = null;
                try {
                    res = this.cloudinary.uploader().upload(user.getFile().getBytes(),
                            ObjectUtils.asMap("resource_type", "auto"));
                    u.setAvatar(res.get("secure_url").toString());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if (res != null) {
                    u.setAvatar((String) res.get("secure_url"));
                } else {
                    u.setAvatar(avatar);
                }
            }

            if (user.getFacebook() != null && !user.getFacebook().isEmpty()) {
                u.setFacebook(user.getFacebook());
            }
            if (user.getInstagram() != null && !user.getInstagram().isEmpty()) {
                u.setInstagram(user.getInstagram());
            }
            if (user.getYoutube() != null && !user.getYoutube().isEmpty()) {
                u.setYoutube(user.getYoutube());
            }
            if (user.getTiktok() != null && !user.getTiktok().isEmpty()) {
                u.setTiktok(user.getTiktok());
            }

            if (user.getFullname() != null && !user.getFullname().isEmpty()) {
                u.setFullname(user.getFullname());
            }
            if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                u.setEmail(user.getEmail());
            }
            if (user.getPhone() != null && !user.getPhone().isEmpty()) {
                u.setPhone(user.getPhone());
            }
            if (user.getAddress() != null && !user.getAddress().isEmpty()) {
                u.setAddress(user.getAddress());
            }
            if (user.getGender() != null) {
                u.setGender(user.getGender());
            }
            if (user.getUserstatus() != null) {
                u.setUserstatus(user.getUserstatus());
            }
            if (user.getUserRole() != null) {
                u.setUserRole(user.getUserRole());
            }

            return this.userRepository.updateUser(u);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean addUserGoogle(Google google) {
        try {
            Users user = new Users();
            user.setId(0);
            user.setFullname(google.getName());
            user.setEmail(google.getEmail());
            user.setUsername(google.getId());
            user.setAvatar(google.getPicture());
            user.setCreatedDate(new Date());
            user.setUpdatedDate(new Date());
            user.setUserRole(new Role(3));
            user.setUserstatus(new Status(1));
            user.setExp(5);
            user.setGoogleID(google.getId());
            user.setTokenGoogle("");
            user.setNotification(1);
            user.setWheel(5);
            user.setAvatarFrame(new Frame(1));

            return this.userRepository.addOrUpdateUser(user);
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean addUserFacebook(Facebook facebook) {
        try {
            Users user = new Users();
            user.setId(0);
            user.setFullname(facebook.getName());
            user.setEmail(facebook.getEmail());
            user.setUsername(facebook.getId());
            user.setAvatar(facebook.getPicture() != null ? facebook.getPicture().getUrl() : null);
            user.setAddress(facebook.getLocation() != null ? facebook.getLocation().getName() : null);
            user.setCreatedDate(new Date());
            user.setUpdatedDate(new Date());
            user.setUserRole(new Role(3));
            user.setUserstatus(new Status(1));
            user.setExp(5);
            user.setFacebookID(facebook.getId());
            user.setTokenFacbook("");
            user.setNotification(1);
            user.setWheel(5);
            user.setAvatarFrame(new Frame(1));

            return this.userRepository.addOrUpdateUser(user);
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public Users getUserByGoogleId(String googleId) {
        return this.userRepository.getUserByGoogleId(googleId);
    }

    @Override
    public Users getUserByFacebookId(String facebookId) {
        return this.userRepository.getUserByFacebookId(facebookId);
    }

    @Override
    public boolean verifyRecaptcha(String captchaResponse) {
        RestTemplate restTemplate = new RestTemplate();
        String requestUrl = env.getProperty("recaptcha.url")
                + "?secret=" + env.getProperty("recaptcha.secret.key")
                + "&response=" + captchaResponse;
        Recaptcha recaptcha = restTemplate.postForObject(requestUrl, null, Recaptcha.class);

        return recaptcha != null && recaptcha.isSuccess();
    }

    @Override
    public List<Users> getUserByEmail(String email) {
        return this.userRepository.getUserByEmail(email);
    }

    @Override
    public boolean sendCodeToEmail(int userId, String email, String baseUrl) {
        try {
            Users user = this.userRepository.getUserById(userId);
            UUID token = UUID.randomUUID();
            String ticket = token.toString().replace("-", "");
            user.setOtp(ticket);
            user.setOtpGeneratedTime(new Date());
            this.userRepository.updateUser(user);
            HtmlEmail htmlEmail = new HtmlEmail();
            htmlEmail.setHostName(env.getProperty("spring.mail.host")); // SMTP server
            htmlEmail.setSmtpPort(Integer.parseInt(env.getProperty("spring.mail.port"))); // Port
            htmlEmail.setAuthenticator(new DefaultAuthenticator(env.getProperty("spring.mail.username"), env.getProperty("spring.mail.password"))); // Email và mật khẩu
            htmlEmail.setStartTLSEnabled(true); // Bật TLS

            htmlEmail.setFrom(env.getProperty("spring.mail.username"), "PhuAnShop");
            htmlEmail.setCharset("UTF-8");
            htmlEmail.setSubject("Phục hồi mật khẩu cho tài khoản tại PhuAnShop");
            String htmlMessage = "<html><body>";
            htmlMessage += "<p>Chào bạn,</p>";
            htmlMessage += "<p>Bạn vừa thực hiện yêu cầu phục hồi mật khẩu, để thay đổi mật khẩu, bạn vui lòng click vào đường link bên dưới:</p>";
            htmlMessage += "<p><a href=\"" + baseUrl + "?ticket=" + ticket + "\">Đường dẫn đến trang đổi mật khẩu</a></p>";
            htmlMessage += "<p>Lưu ý: Đường dẫn trên chỉ tồn tại trong vòng 1 giờ.</p>";
            htmlMessage += "<p>Trân trọng,</p>";
            htmlMessage += "<p>PhuAnShop</p>";
            htmlMessage += "<h5><span style='font-size: 13px;'>Đây là email tự động, vui lòng không phản hồi lại trên email này.</span></h5>";
            htmlMessage += "</body></html>";
            htmlEmail.setHtmlMsg(htmlMessage);
            htmlEmail.addTo(email);

            htmlEmail.send();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean sendOtpCodeToEmail(Verification verification) {
        try {
            HtmlEmail htmlEmail = new HtmlEmail();
            htmlEmail.setHostName(env.getProperty("spring.mail.host")); // SMTP server
            htmlEmail.setSmtpPort(Integer.parseInt(env.getProperty("spring.mail.port"))); // Port
            htmlEmail.setAuthenticator(new DefaultAuthenticator(env.getProperty("spring.mail.username"), env.getProperty("spring.mail.password"))); // Email và mật khẩu
            htmlEmail.setStartTLSEnabled(true); // Bật TLS

            htmlEmail.setFrom(env.getProperty("spring.mail.username"), "PhuAnShop");
            htmlEmail.setCharset("UTF-8");
            htmlEmail.setSubject(verification.getOtpCode() + " là mã xác nhận tài khoản của bạn trên PhuAnShop");
            String htmlMessage = "<html><body style='margin-right: auto; margin-left: auto; padding-left: 5px; padding-right: 5px; width: 90%; font-size:16px;'>";
            htmlMessage += "<p align='center'><img src='https://res.cloudinary.com/dkmug1913/image/upload/v1687075830/WebApp/logo_km2dfc.png' alt='Phú An Shop' /></p>";
            htmlMessage += "<h1 style='text-align: center;'>Xác minh qua Email</h1>";
            htmlMessage += "<p>Chào bạn,</p>";
            htmlMessage += "<p>Mã xác minh của bạn là: <strong><span style='color: #4ea4dc'>" + verification.getOtpCode() + "</span></strong></p>";
            htmlMessage += "<p>Lưu ý: Vui lòng hoàn thành xác nhận trong vòng 30 phút.</p>";
            htmlMessage += "<p>PhuAnShop</p>";
            htmlMessage += "<h5><span style='font-size: 13px; color: #777'>Đây là email tự động, vui lòng không phản hồi lại trên email này.</span></h5>";
            htmlMessage += "</body></html>";
            htmlEmail.setHtmlMsg(htmlMessage);
            htmlEmail.addTo(verification.getPropersion());

            htmlEmail.send();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean changePassword(String password, Users user) {
        String pass = password.trim();
        user.setPassword(this.bCryptPasswordEncoder.encode(pass));
        return this.userRepository.updateUser(user);
    }

    @Override
    public Users getUserByTicket(String ticket) {
        return this.userRepository.getUserByTicket(ticket);
    }

    @Override
    public Users getUserAccountById(int id) {
        return this.userRepository.getUserAccountById(id);
    }

    @Override
    public List<Users> getUsers(Map<String, String> params, int start, int limit) {
        return this.userRepository.getUsers(params, start, limit);
    }

    @Override
    public boolean deleteCustomer(int id) {
        return this.userRepository.deleteCustomer(id);
    }

    @Override
    public List<Object[]> getTopUsers(int limit) {
        return this.userRepository.getTopUsers(limit);
    }

    @Override
    public List<Users> getUsersLogin(int limit) {
        return this.userRepository.getUsersLogin(limit);
    }

}
