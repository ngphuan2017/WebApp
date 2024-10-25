package com.annp.service.impl;

import com.annp.pojo.ClientInfo;
import com.annp.repository.ClientRepository;
import com.annp.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void saveClientInfo(ClientInfo clientInfo) {
        this.clientRepository.saveClientInfo(clientInfo);
    }

    @Override
    public ClientInfo getClientInfoByUserId(int userId, String clientIp) {
        return this.clientRepository.getClientInfoByUserId(userId, clientIp);
    }

    @Override
    public void updateClientInfo(ClientInfo clientInfo) {
        this.clientRepository.updateClientInfo(clientInfo);
    }

    @Override
    public List<ClientInfo> getAllClientInfoByUserId(int userId) {
        return this.clientRepository.getAllClientInfoByUserId(userId);
    }
}
