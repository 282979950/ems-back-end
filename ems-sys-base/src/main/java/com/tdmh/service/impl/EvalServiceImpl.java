package com.tdmh.service.impl;

import com.tdmh.common.JsonData;
import com.tdmh.entity.Eval;
import com.tdmh.entity.mapper.EvalMapper;
import com.tdmh.param.EvalParam;
import com.tdmh.service.IEvalService;
import com.tdmh.utils.BeanMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Liuxia on 2018/11/15.
 */
@Service
public class EvalServiceImpl implements IEvalService {

    @Autowired
    private EvalMapper evalMapper;

    @Override
    public JsonData listData() {
        List<EvalParam> list = evalMapper.getAllEvals(null,null);
        List<Map<String, Object>> mapList = objectsToMaps(list);
        return mapList==null||mapList.size()==0 ? JsonData.successMsg("暂无评价") : JsonData.successData(mapList);
    }

    @Override
    public JsonData selectEval(String userName, String applyRepairFlowNumber) {
        List<EvalParam> list = evalMapper.getAllEvals(userName,applyRepairFlowNumber);
        List<Map<String, Object>> mapList = objectsToMaps(list);
        return mapList==null||mapList.size()==0 ? JsonData.successMsg("查询结果为空") : JsonData.success(mapList,"查询成功");
    }

    private List<Map<String, Object>> objectsToMaps(List<EvalParam> list){
        List<Map<String, Object>> mapList = BeanMapUtil.objectsToMaps(list);
        for(Map<String, Object> map : mapList){
            if(map.get("evalList")!=null){
                List<Eval> evalList = (List<Eval>)map.get("evalList");
                for(Eval e : evalList){
                    map.put("评价项"+e.getEvalItemId(),e.getEvalContent());
                }
            }
        }
        return mapList;
    }
}
