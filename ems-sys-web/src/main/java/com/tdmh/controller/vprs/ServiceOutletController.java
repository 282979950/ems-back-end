package com.tdmh.controller.vprs;

import com.tdmh.service.impl.IServiceOutletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author Liuxia on 2018/11/12.
 */

@Controller
public class ServiceOutletController {

    @Autowired
    private IServiceOutletService serviceOutletService;
}
