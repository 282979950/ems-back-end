package com.tdmh.service;

import com.tdmh.common.JsonData;

/**
 * @author Liuxia on 2018/11/15.
 */
public interface IEvalService {
    JsonData listData();

    JsonData selectEval(String userName, String applyRepairFlowNumber);
}
