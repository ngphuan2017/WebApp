/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.annp.repository;

import com.annp.pojo.ClientInfo;
import com.annp.pojo.Users;
import java.util.List;
import java.util.Map;

/**
 *
 * @author phuan
 */
public interface UserRepository {
    
    public List<Users> getUsers(Map<String, String> params, int start, int limit);
    public List<Object[]> getTopUsers(int limit);
    public List<Users> getUsersLogin(int limit);
    public Users getUserById(int id);
    public Users getUserByUsername(String username);
    public boolean addOrUpdateUser(Users user);
    public boolean getByUsername(String username);
    public boolean updateUser(Users user);
    public Users getUserByGoogleId(String googleId);
    public Users getUserByFacebookId(String facebookId);
    public List<Users> getUserByEmail(String email);
    public Users getUserByTicket(String ticket);
    public Users getUserAccountById(int id);
    public boolean deleteCustomer(int id);
}
