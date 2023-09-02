package com.annp.service.impl;

import com.annp.pojo.Facebook;
import com.annp.pojo.Google;
import com.annp.pojo.Recaptcha;
import com.annp.pojo.Role;
import com.annp.pojo.Status;
import com.annp.pojo.Users;
import com.annp.repository.UserRepository;
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
                user.setCreatedDate(new Date());
                user.setUpdatedDate(new Date());
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
    public boolean sendConfirmationCodeByEmail(String email) {
        return true;
    }
}
