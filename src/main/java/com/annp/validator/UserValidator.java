/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.validator;

import com.annp.pojo.Users;
import com.annp.service.UserService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author trant
 */
@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Users.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Users user = (Users) target;
        int usernameMinLength = 6;
        int passwordMinLength = 6;

        if (user.getUsername().isEmpty()) {
            errors.rejectValue("username", "", "Tên đăng nhập không được bỏ trống");
        } else if (userService.getByUsername(user.getUsername())) {
            errors.rejectValue("username", "", "Tên đăng nhập đã tồn tại");
        } else if (user.getUsername().contains(" ")) {
            errors.rejectValue("username", "", "Tên đăng nhập không chứa khoảng trắng");
        } else if (user.getUsername().length() < usernameMinLength) {
            errors.rejectValue("username", "", "Tên đăng nhập tối thiểu " + usernameMinLength + " ký tự");
        }
        if (user.getPassword().isEmpty()) {
            errors.rejectValue("password", "", "Mật khẩu không được bỏ trống");
        } else if (user.getPassword().contains(" ")) {
            errors.rejectValue("password", "", "Mật khẩu không chứa khoảng trắng");
        } else if (user.getPassword().length() < passwordMinLength) {
            errors.rejectValue("password", "", "Mật khẩu chứa tối thiểu " + passwordMinLength + " ký tự");
        } else if (!user.getPassword().equals(user.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "", "Mật khẩu không trùng khớp");
        }
        if (user.getUsername().isEmpty()) {
            errors.rejectValue("fullname", "", "Họ và tên không được bỏ trống");
        }
        if (user.getEmail().isEmpty()) {
            errors.rejectValue("email", "", "Email không được bỏ trống");
        } else if (!isValidEmail(user.getEmail())) {
            errors.rejectValue("email", "", "Email chưa đúng định dạng");
        }
        if (user.getPhone().isEmpty()) {
            errors.rejectValue("phone", "", "Số điện thoại không được bỏ trống");
        } else if (!isValidMobile(user.getPhone())) {
            errors.rejectValue("phone", "", "Số điện thoại không hợp lệ");
        }
    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean isValidEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public static boolean isValidMobile(String phoneStr) {
        Pattern pattern = Pattern.compile("^[0-9]{10}$");
        Matcher matcher = pattern.matcher(phoneStr);
        return matcher.matches();
    }
}
