package com.tdmh.service.impl;

import com.google.common.collect.Lists;
import com.tdmh.common.BeanValidator;
import com.tdmh.common.JsonData;
import com.tdmh.entity.EvalItem;
import com.tdmh.entity.mapper.EvalItemMapper;
import com.tdmh.param.EvalItemParam;
import com.tdmh.service.IEvalItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Liuxia on 2018/11/14.
 */
@Service
public class EvalItemServiceImpl implements IEvalItemService {

    @Autowired
    private EvalItemMapper evalItemMapper;

    @Override
    public JsonData getAllEvalItem() {
        List<EvalItemParam> list = evalItemMapper.getAllEvalItem();
        return list == null || list.size()==0 ? JsonData.successMsg("暂无评价项"):JsonData.successData(list);

    }

    @Override
    public JsonData createEvalItem(EvalItemParam evalItemParam) {
        BeanValidator.check(evalItemParam);
        EvalItem evalItem = new EvalItem();
        evalItem.setEvalItemContent(evalItemParam.getEvalItemContent());
        evalItem.setCreateBy(evalItemParam.getCreateBy());
        evalItem.setUpdateBy(evalItemParam.getUpdateBy());
        evalItem.setRemarks(evalItemParam.getRemarks());
        evalItem.setUsable(true);
        int resultCount = evalItemMapper.insert(evalItem);
        if(resultCount >0) return JsonData.successMsg("新增评价项成功");
        return JsonData.fail("新增评价项失败");
    }

    @Override
    public JsonData deleteEvalItem(List<Integer> ids, Integer currentEmpId) {
        List<EvalItem> evalItemList = Lists.newArrayList();
        for(Integer eId : ids) {
            EvalItem evalItem = evalItemMapper.getEvalItemById(eId);
            evalItem.setUpdateBy(currentEmpId);
            evalItem.setUsable(false);
            evalItemList.add(evalItem);
        }
        int resultCount = evalItemMapper.deleteBatch(evalItemList);
        if (resultCount == 0) {
            return JsonData.fail("删除评价项失败");
        }
        return JsonData.successMsg("删除评价项成功");
    }

    @Override
    public JsonData updateEvalItem(EvalItemParam evalItemParam) {
        BeanValidator.check(evalItemParam);
        EvalItem evalItem = new EvalItem();
        evalItem.setEvalItemId(evalItemParam.getEvalItemId());
        evalItem.setEvalItemContent(evalItemParam.getEvalItemContent());
        evalItem.setUpdateBy(evalItemParam.getUpdateBy());
        evalItem.setRemarks(evalItemParam.getRemarks());
        evalItem.setUsable(true);
        int resultCount = evalItemMapper.update(evalItem);
        if(resultCount >0) return JsonData.successMsg("修改评价项成功");
        return JsonData.fail("修改评价项失败");
    }

    @Override
    public JsonData selectEvalItem(String evalItemContent) {
        List<EvalItemParam> list = evalItemMapper.selectEvalItem(evalItemContent);
        return list == null || list.size()==0 ? JsonData.successMsg("暂无评价项"):JsonData.success(list,"查询成功");

    }
}
