/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.controllers;

import com.annp.pojo.District;
import com.annp.pojo.Frame;
import com.annp.pojo.UserLevels;
import com.annp.pojo.Users;
import com.annp.pojo.Verification;
import com.annp.pojo.Ward;
import com.annp.service.DistrictService;
import com.annp.service.FrameService;
import com.annp.service.UserLevelsService;
import com.annp.service.UserService;
import com.annp.service.VerificationService;
import com.annp.service.WardService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author phuan
 */
@RestController
@RequestMapping("/api")
public class ApiUserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserLevelsService userLevelsService;
    @Autowired
    private FrameService frameService;
    @Autowired
    private VerificationService verificationService;
    @Autowired
    private DistrictService districtService;
    @Autowired
    private WardService wardService;

    @GetMapping("/users/level/{userExp}")
    public ResponseEntity<Object> aboutProductView(@PathVariable(value = "userExp") int exp) {
        UserLevels level = this.userLevelsService.getUserLevelByExp(exp);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("level", level);
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Users> aboutAccountView(@PathVariable(value = "userId") int id) {
        Users user = this.userService.getUserAccountById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/users/avatar/frame/{userId}")
    public ResponseEntity changeFrameAvatar(@PathVariable(value = "userId") int id, @RequestBody Map<String, Integer> params) {
        try {
            Users user = this.userService.getUserById(id);
            Frame frame = this.frameService.getFrameById(params.get("frameId"));
            user.setAvatarFrame(frame);
            this.userService.updateUser(user);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/register/city/{cityId}")
    public ResponseEntity<Object> getListDistrict(@PathVariable(value = "cityId") int id) {
        List<District> districts = this.districtService.getDistrictsByCityId(id);
        return new ResponseEntity<>(districts, HttpStatus.OK);
    }

    @GetMapping("/users/register/district/{districtId}")
    public ResponseEntity<Object> getListWard(@PathVariable(value = "districtId") int id) {
        List<Ward> wards = this.wardService.getWardsByDistrictId(id);
        return new ResponseEntity<>(wards, HttpStatus.OK);
    }

    @PutMapping("/users/wheel/{userId}")
    public ResponseEntity setWheelValue(@PathVariable(value = "userId") int id) {
        try {
            Users user = this.userService.getUserById(id);
            if (user.getWheel() != 0) {
                user.setWheel(user.getWheel() - 1);
            }
            this.userService.updateUser(user);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/users/verification")
    public ResponseEntity checkVerification(@RequestBody Map<String, String> params) {
        try {
            String email = params.get("propersion");
            Verification v = this.verificationService.getVerificationByEmail(email);
            if (v == null) {
                v = new Verification();
                v.setId(0);
                v.setOtpCode(this.verificationService.randomOtp()); // 6 số ngẫu nhiên
                v.setPropersion(email);
                v.setGeneratedTime(new Date());
            } else {
                v.setOtpCode(this.verificationService.randomOtp());
                v.setGeneratedTime(new Date());
            }
            if (this.userService.sendOtpCodeToEmail(v)) {
                this.verificationService.addOrUpdateOtpCode(v);
            }
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/verification/checked")
    public ResponseEntity checkedVerification(@RequestBody Map<String, String> params) {
        try {
            String email = params.get("propersion");
            String otpCode = params.get("otpCode");
            Date generatedTime = new Date();

            Verification v = this.verificationService.getVerificationByEmail(email);
            if (v == null) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            } else {
                if (otpCode.equals(v.getOtpCode()) && generatedTime.getTime() - v.getGeneratedTime().getTime() <= 30 * 60 * 1000) {
                    return new ResponseEntity(HttpStatus.OK);
                } else {
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

}
