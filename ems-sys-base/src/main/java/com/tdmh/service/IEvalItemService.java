package com.tdmh.service;

import com.tdmh.common.JsonData;
import com.tdmh.param.EvalItemParam;
import com.tdmh.param.WxEvalParam;

import java.util.List;

/**
 * @author Liuxia on 2018/11/14.
 */
public interface IEvalItemService {
    JsonData getAllEvalItem();

    JsonData createEvalItem(EvalItemParam evalItemParam);

    JsonData deleteEvalItem(List<Integer> ids, Integer currentEmpId);

    JsonData updateEvalItem(EvalItemParam evalItemParam);

    JsonData selectEvalItem(String evalItemContent);

    JsonData getWXEvalItem();

    JsonData saveEval(WxEvalParam eval);
}
