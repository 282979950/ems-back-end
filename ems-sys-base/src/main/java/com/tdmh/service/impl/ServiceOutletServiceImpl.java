package com.tdmh.service.impl;

import com.tdmh.entity.mapper.ServiceOutletMapper;
import com.tdmh.service.IServiceOutletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Liuxia on 2018/11/12.
 */
@Service
public class ServiceOutletServiceImpl implements IServiceOutletService {

    @Autowired
    private ServiceOutletMapper serviceOutletMapper;
}
