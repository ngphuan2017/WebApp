/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.annp.repository;

import com.annp.pojo.Users;
import java.util.List;

/**
 *
 * @author phuan
 */
public interface UserRepository {
    
    public Users getUserById(int id);
    public Users getUserByUsername(String username);
    boolean addOrUpdateUser(Users user);
    boolean getByUsername(String username);
    boolean updateUser(Users user);
    public Users getUserByGoogleId(String googleId);
    public Users getUserByFacebookId(String facebookId);
    public List<Users> getUserByEmail(String email);
}
