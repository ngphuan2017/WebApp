/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.controllers;

import com.annp.handlers.FacebookHandler;
import com.annp.handlers.GoogleHandler;
import com.annp.pojo.City;
import com.annp.pojo.ClientInfo;
import com.annp.pojo.Facebook;
import com.annp.pojo.Frame;
import com.annp.pojo.Google;
import com.annp.pojo.Notification;
import com.annp.pojo.OrderDetail;
import com.annp.pojo.Orders;
import com.annp.pojo.Promotion;
import com.annp.pojo.Status;
import com.annp.pojo.UserLevels;
import com.annp.pojo.Users;
import com.annp.service.CityService;
import com.annp.service.ClientService;
import com.annp.service.FrameService;
import com.annp.service.NotificationService;
import com.annp.service.OrdersService;
import com.annp.service.PromotionService;
import com.annp.service.UserLevelsService;
import com.annp.service.UserService;
import com.annp.validator.UserValidator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    private UserLevelsService userLevelsService;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private FrameService frameService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private GoogleHandler googleHandler;
    @Autowired
    private FacebookHandler fbHandler;

    @ModelAttribute
    public void addAttributes(Model model, Authentication authentication) {
        if (authentication != null) {
            Users user = this.userService.getUserByUsername(authentication.getName());
            model.addAttribute("currentUser", user);
            List<UserLevels> listUserLevelses = this.userLevelsService.getUserLevels();
            model.addAttribute("listUserLevels", listUserLevelses);
        }
        List<City> listCitys = this.cityService.getCity();
        model.addAttribute("listCitys", listCitys);
    }

    @GetMapping(path = "/me/profile")
    public String userProfile(Model model, Authentication authentication) {
        Users user = new Users();
        user.setId(0);
        model.addAttribute("user", user);
        Users userid = this.userService.getUserByUsername(authentication.getName());
        List<Orders> orders = this.ordersService.getOrderByUserId(userid.getId());
        List<ClientInfo> clients = this.clientService.getAllClientInfoByUserId(userid.getId());
        List<Frame> frames = this.frameService.getFrames();
        model.addAttribute("orders", orders);
        model.addAttribute("clients", clients);
        model.addAttribute("frames", frames);
        return "profile";
    }

    @PostMapping(value = "/me/profile")
    public String changeAvatarProfile(Model model, HttpServletRequest request, @ModelAttribute(value = "user") @Valid Users user,
            BindingResult result, final RedirectAttributes redirectAttributes) {
//        userValidator.validate(user, result);

        if (this.userService.updateProfileUser(user)) {
            Users u = this.userService.getUserById(user.getId());
            request.getSession().setAttribute("currentUser", u);
            model.addAttribute("currentUser", u);
            return "redirect:/me/profile";
        } else {
            return "maintenance";
        }
    }

    @GetMapping(path = "/me/notification")
    public String userNotification(Model model, HttpServletRequest request, Authentication authentication) {
        try {
            Users user = this.userService.getUserByUsername(authentication.getName());
            user.setNotification(0);
            this.userService.updateUser(user);
            List<Orders> orders = this.ordersService.getOrderByUserId(user.getId());
            List<OrderDetail> orderDetails = new ArrayList<>();
            for (Orders od : orders) {
                orderDetails.addAll(this.ordersService.getOrderDetailByOrderId(od.getId()));
            }
            List<Promotion> promotions = this.promotionService.getPromotions(new Status(19));
            List<Notification> notifications = this.notificationService.getNotificationsByUserId(user);

            List<Object> combinedList = new ArrayList<>();
            combinedList.addAll(orderDetails);
            combinedList.addAll(promotions);
            combinedList.addAll(notifications);

            Collections.sort(combinedList, (obj1, obj2) -> {
                Date date1 = getDateFromObject(obj1);
                Date date2 = getDateFromObject(obj2);
                return date2.compareTo(date1);
            });

            List<String> itemTypes = new ArrayList<>();
            for (Object item : combinedList) {
                itemTypes.add(item.getClass().getSimpleName());
            }

            Users u = this.userService.getUserById(user.getId());
            request.getSession().setAttribute("currentUser", u);
            model.addAttribute("currentUser", u);
            model.addAttribute("itemTypes", itemTypes);
            model.addAttribute("responseList", combinedList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "notification";
    }

    @RequestMapping("/login")
    public String index(Authentication authentication) {
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
            u = this.userService.getUserByGoogleId(google.getId());
        } else {
            Date currentDate = new Date();
            if (!this.googleHandler.isSameDay(u.getUpdatedDate(), currentDate)) {
                u.setExp(u.getExp() + 5);
                u.setNotification(u.getNotification() + 1);
                u.setWheel(u.getWheel() + 5);
                Notification n = new Notification();
                n.setId(0);
                n.setName("Thông báo từ hệ thống");
                n.setDescription("🎁Bạn nhận được 5 lượt vòng quay may mắn và 5 điểm kinh nghiệm");
                n.setUserId(u);
                this.notificationService.addNotification(n);
            }
            u.setUpdatedDate(currentDate);
            this.userService.updateUser(u);
        }
        UserDetails userDetail = googleHandler.buildUser(google);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null,
                userDetail.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        HttpSession session = request.getSession();
        session.setAttribute("currentUser", u);
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
            u = this.userService.getUserByFacebookId(user.getId());
        } else {
            Date currentDate = new Date();
            if (!this.fbHandler.isSameDay(u.getUpdatedDate(), currentDate)) {
                u.setExp(u.getExp() + 5);
                u.setNotification(u.getNotification() + 1);
                u.setWheel(u.getWheel() + 5);
                Notification n = new Notification();
                n.setId(0);
                n.setName("Thông báo từ hệ thống");
                n.setDescription("🎁Bạn nhận được 5 lượt vòng quay may mắn và 5 điểm kinh nghiệm");
                n.setUserId(u);
                this.notificationService.addNotification(n);
            }
            u.setUpdatedDate(currentDate);
            this.userService.updateUser(u);
        }
        UserDetails userDetail = fbHandler.buildUser(user);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null,
                userDetail.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        HttpSession session = request.getSession();
        session.setAttribute("currentUser", u);
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

        if (this.userService.addOrUpdateUser(user)) {
            return "redirect:/login";
        } else {
            return "register";
        }
    }

    @PostMapping("/forgot-password")
    public ModelAndView forgotPassword(HttpServletRequest request, @RequestParam("email") String email, @RequestParam("selectedUserId") Integer userId) {
        ModelAndView modelAndView = new ModelAndView();

        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        baseUrl += "/forgot-password/change-password";
        if (!this.userService.sendTicketToEmail(userId, email, baseUrl)) {
            modelAndView.addObject("information", "Hệ thống đang có lỗi, vui lòng quay lại sau!");
            modelAndView.setViewName("information");
            return modelAndView;
        }
        modelAndView.addObject("information", "Xác nhận đã được gửi đến " + email + ".<br/>Vui lòng kiểm tra hộp thư để tiếp tục bước tiếp theo!");
        modelAndView.addObject("isHtml", true);
        modelAndView.setViewName("information");
        return modelAndView;
    }

    @GetMapping("/forgot-password/change-password")
    public String getChangePassword(Model model, HttpServletRequest request, Authentication authentication) {

        Users user = new Users();
        String ticket = request.getParameter("ticket");
        if (authentication != null) {
            user = this.userService.getUserByUsername(authentication.getName());
        } else if (ticket != null && !ticket.isEmpty()){
            user = this.userService.getUserByTicket(ticket);
        }
        model.addAttribute("currentUser", user);
        model.addAttribute("ticket", ticket);
        return "change-password";
    }

    @PostMapping("/forgot-password/change-password")
    public String changePassword(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam("password") String password, Authentication authentication) {

        String ticket = request.getParameter("ticket");
        if (authentication != null) {
            Users user = this.userService.getUserByUsername(authentication.getName());
            if (this.userService.changePassword(password, user)) {
                new SecurityContextLogoutHandler().logout(request, response, authentication);
                return "redirect:/login";
            }
        } else if (ticket != null && !ticket.isEmpty()) {
            Users user = this.userService.getUserByTicket(ticket);
            Date currentDate = new Date();
            if (this.userValidator.isOneHourApart(user.getTicketGeneratedTime(), currentDate)) {
                if (this.userService.changePassword(password, user)) {
                    return "login";
                }
            }
        }
        model.addAttribute("information", "Lỗi: Yêu cầu của bạn đã quá hạn!");
        return "information";
    }

    @GetMapping("/me/orders/{orderId}")
    public String details(Model model, Authentication authentication, @PathVariable(value = "orderId") int id) {
        Users user = this.userService.getUserByUsername(authentication.getName());
        Orders orders = this.ordersService.getOrderById(id);
        if (orders != null && user.getId() == orders.getUserid().getId() || user.getUserRole().getId() < 3) {
            List<OrderDetail> orderdetails = this.ordersService.getOrderDetailByOrderId(id);
            model.addAttribute("orders", orderdetails);
            return "order-detail";
        } else {
            return "access-denied";
        }
    }

    private Date getDateFromObject(Object obj) {
        if (obj instanceof OrderDetail) {
            return ((OrderDetail) obj).getCreatedDate();
        } else if (obj instanceof Promotion) {
            return ((Promotion) obj).getCreatedDate();
        } else if (obj instanceof Notification) {
            return ((Notification) obj).getCreatedDate();
        }
        return null;
    }

}
