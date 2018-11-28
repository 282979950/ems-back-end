package com.tdmh.service;

import com.tdmh.common.JsonData;
import com.tdmh.param.WXNoticeParam;

import java.util.List;

/**
 * @author litairan on 2018/11/16.
 */
public interface IWXNoticeService {
    JsonData listData();

    JsonData listDataWithPagination(Integer pageNum, Integer pageSize);

    JsonData create(WXNoticeParam param);

    JsonData delete(List<Integer> ids, Integer currentEmpId);

    JsonData search(String wxNoticeTitle, Integer pageNum, Integer pageSize);
}
