/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.controllers;

import com.annp.handlers.FacebookHandler;
import com.annp.handlers.GoogleHandler;
import com.annp.pojo.City;
import com.annp.pojo.District;
import com.annp.pojo.Facebook;
import com.annp.pojo.Google;
import com.annp.pojo.Role;
import com.annp.pojo.Status;
import com.annp.pojo.UserLevels;
import com.annp.pojo.Users;
import com.annp.pojo.Ward;
import com.annp.service.CityService;
import com.annp.service.DistrictService;
import com.annp.service.UserLevelsService;
import com.annp.service.UserService;
import com.annp.service.WardService;
import com.annp.validator.UserValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author phuan
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CityService cityService;
    @Autowired
    private DistrictService districtService;
    @Autowired
    private WardService wardService;
    @Autowired
    private UserLevelsService userLevelsService;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private GoogleHandler googleHandler;
    @Autowired
    private FacebookHandler fbHandler;

    @ModelAttribute
    public void addAttributes(Model model, Authentication authentication) throws JsonProcessingException {
        if (authentication != null) {
            Users user = this.userService.getUserByUsername(authentication.getName());
            model.addAttribute("currentUser", user);
            List<UserLevels> listUserLevelses = this.userLevelsService.getUserLevels();
            model.addAttribute("listUserLevels", listUserLevelses);
        }
        List<City> listCitys = this.cityService.getCity();
        model.addAttribute("listCitys", listCitys);
        List<District> listDistricts = this.districtService.getDistrict();
        model.addAttribute("listDistricts", listDistricts);
        List<Ward> listWards = this.wardService.getWard();
        model.addAttribute("listWards", listWards);
    }

    @GetMapping(path = "/me/profile")
    public String userProfile(Model model) {
        Users user = new Users();
        user.setId(0);
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping(value = "/me/profile")
    public String changeAvatarProfile(Model model, @ModelAttribute(value = "user") @Valid Users user,
            BindingResult result, final RedirectAttributes redirectAttributes) {
//        userValidator.validate(user, result);

        if (this.userService.updateProfileUser(user)) {
            return "redirect:/me/profile";
        } else {
            return "maintenance";
        }
    }

    @RequestMapping("/login")
    public String index(Model model, Authentication authentication) {
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        return "login";
    }

    @RequestMapping("/login/login-google")
    public String loginGoogle(HttpServletRequest request) throws ClientProtocolException, IOException {
        String code = request.getParameter("code");

        if (code == null || code.isEmpty()) {
            return "redirect:/login?google=error";
        }
        String accessToken = googleHandler.getToken(code);

        Google google = googleHandler.getUserInfo(accessToken);
        Users u = this.userService.getUserByGoogleId(google.getId());
        if (u == null) {
            this.userService.addUserGoogle(google);
        } else {
            Date currentDate = new Date();
            if (!this.googleHandler.isSameDay(u.getUpdatedDate(), currentDate)) {
                u.setExp(u.getExp() + 5);
                u.setUpdatedDate(currentDate);
                this.userService.updateUser(u);
            }
        }
        UserDetails userDetail = googleHandler.buildUser(google);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null,
                userDetail.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "redirect:/";
    }

    @RequestMapping("/login/login-facebook")
    public String loginFacebook(HttpServletRequest request) {
        String code = request.getParameter("code");
        String accessToken = "";
        try {
            accessToken = fbHandler.getToken(code);
        } catch (IOException e) {
            return "redirect:/login?facebook=error";
        }
        com.restfb.types.User user = fbHandler.getUserInfo(accessToken);
        Users u = this.userService.getUserByFacebookId(user.getId());
        if (u == null) {
            Facebook facebook = new Facebook();
            facebook.setId(user.getId());
            facebook.setName(user.getName());
            facebook.setEmail(user.getEmail());
            facebook.setLocation(user.getLocation());
            facebook.setPicture(user.getPicture());
            this.userService.addUserFacebook(facebook);
        } else {
            Date currentDate = new Date();
            if (!this.fbHandler.isSameDay(u.getUpdatedDate(), currentDate)) {
                u.setExp(u.getExp() + 5);
                u.setUpdatedDate(currentDate);
                this.userService.updateUser(u);
            }
        }
        UserDetails userDetail = fbHandler.buildUser(user);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null,
                userDetail.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "redirect:/";
    }

    @GetMapping(value = "/register")
    public String registerView(Model model) {
        Users user = new Users();
        user.setId(0);
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping(value = "/register")
    public String registerProcess(Model model, @ModelAttribute(value = "user") @Valid Users user,
            BindingResult result, final RedirectAttributes redirectAttributes) {

        userValidator.validate(user, result);
        if (result.hasErrors()) {
            return "register";
        }

        user.setUserRole(new Role(3));
        user.setUserstatus(new Status(1));
        user.setExp(5);

        if (this.userService.addOrUpdateUser(user)) {
            return "redirect:/login";
        } else {
            return "register";
        }
    }

    @GetMapping("/forgot-password")
    public ResponseEntity<List<Users>> getUserByEmail(@RequestParam("email") String email) {
        List<Users> users = this.userService.getUserByEmail(email);
        if (users != null && !users.isEmpty()) {
            return ResponseEntity.ok(users);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/forgot-password")
    public ModelAndView forgotPassword(@RequestParam("g-recaptcha-response") String captchaResponse,
            @RequestParam("email") String email) {
        ModelAndView modelAndView = new ModelAndView();
        if (!userService.verifyRecaptcha(captchaResponse)) {
            modelAndView.addObject("notification", "reCaptcha verification failed");
            modelAndView.setViewName("notification");
            return modelAndView;
        }
        if (!userService.sendConfirmationCodeByEmail(email)) {
            modelAndView.addObject("notification", "Hệ thống đang có lỗi, vui lòng quay lại sau!");
            modelAndView.setViewName("notification");
            return modelAndView;
        }
        modelAndView.addObject("notification", "Vui lòng kiểm tra Email để xác nhận!");
        modelAndView.setViewName("notification");
        return modelAndView;
    }

    @RequestMapping("/me/profile/change-password")
    public String changePassword() {
        return "change-password";
    }
}
