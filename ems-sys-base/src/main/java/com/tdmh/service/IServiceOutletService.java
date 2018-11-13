package com.tdmh.service;

import com.tdmh.common.JsonData;
import com.tdmh.entity.ServiceOutlet;

import java.util.List;

/**
 * @author Liuxia on 2018/11/12.
 */
public interface IServiceOutletService {
    JsonData getAllSOLet();

    JsonData createSOLet(ServiceOutlet serviceOutlet);

    JsonData deleteSOLet(List<Integer> ids, Integer currentEmpId);

    JsonData updateSOLet(ServiceOutlet serviceOutlet);

    JsonData selectSOLet(String serviceOutletName, String serviceOutletAddress);
}
