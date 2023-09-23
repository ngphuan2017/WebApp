/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.annp.service;

import com.annp.dto.PaginatesDto;

/**
 *
 * @author phuan
 */
public interface PaginatesService {
    public PaginatesDto getInfoPaginates(int page, int limit, int totalData);
}
