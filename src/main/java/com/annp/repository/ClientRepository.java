package com.annp.repository;

import com.annp.pojo.ClientInfo;

import java.util.List;

public interface ClientRepository {

    public void saveClientInfo(ClientInfo clientInfo);
    public ClientInfo getClientInfoByUserId(int userId, String clientIp);
    public void updateClientInfo(ClientInfo clientInfo);
    public List<ClientInfo> getAllClientInfoByUserId(int userId);
}