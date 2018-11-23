package com.tdmh.service.impl;

import com.google.common.collect.Lists;
import com.tdmh.common.BeanValidator;
import com.tdmh.common.JsonData;
import com.tdmh.entity.Eval;
import com.tdmh.entity.EvalItem;
import com.tdmh.entity.FixedEval;
import com.tdmh.entity.mapper.ApplyRepairMapper;
import com.tdmh.entity.mapper.EvalItemMapper;
import com.tdmh.param.EvalItemParam;
import com.tdmh.param.WxEvalParam;
import com.tdmh.service.IEvalItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Liuxia on 2018/11/14.
 */
@Service
@Transactional(readOnly = true)
public class EvalItemServiceImpl implements IEvalItemService {

    @Autowired
    private EvalItemMapper evalItemMapper;

    @Autowired
    private ApplyRepairMapper applyRepairMapper;

    @Override
    public JsonData getAllEvalItem() {
        List<EvalItemParam> list = evalItemMapper.getAllEvalItem();
        return list == null || list.size()==0 ? JsonData.successMsg("暂无评价项"):JsonData.successData(list);

    }

    @Override
    @Transactional
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
    @Transactional
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
    @Transactional
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

    @Override
    public JsonData getWXEvalItem() {
        List<EvalItemParam> list = evalItemMapper.getWXEvalItem();
        return list == null || list.size()==0 ? JsonData.successMsg("暂无评价项"):JsonData.successData(list);

    }

    @Override
    @Transactional
    public JsonData saveEval(WxEvalParam eval) {
        BeanValidator.check(eval);
        boolean hasEval = applyRepairMapper.checkRepairHasEval(eval.getApplyRepairId());
        if(hasEval){
            return JsonData.fail("该报修单已被评价");
        }
        List<Eval> evals = eval.getEvalList();
        if(evals!=null&&evals.size()>0){
            for(Eval e : evals){
                e.setApplyRepairId(eval.getApplyRepairId());
            }
          int resultCount = evalItemMapper.insertEvalBatch(evals);
          if(resultCount==0) return JsonData.fail("保存评价失败");
        }
        FixedEval fixedEval = new FixedEval();
        fixedEval.setApplyRepairId(eval.getApplyRepairId());
        fixedEval.setFixedEvalSelect(eval.getFixedEvalSelect());
        fixedEval.setFixedEvalContent(eval.getFixedEvalContent());
        int resultCount1 = evalItemMapper.insertFixedEval(fixedEval);
        if(resultCount1==0) return JsonData.fail("保存评价失败");
        int resultCount2 = applyRepairMapper.updateEvalStatus(eval.getApplyRepairId(),true);
        if(resultCount2==0) return JsonData.fail("保存评价失败");
        return JsonData.successMsg("保存评价成功");
    }
}
