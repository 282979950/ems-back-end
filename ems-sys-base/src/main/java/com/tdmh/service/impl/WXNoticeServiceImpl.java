package com.tdmh.service.impl;

import com.tdmh.common.BeanValidator;
import com.tdmh.common.JsonData;
import com.tdmh.entity.mapper.WXNoticeMapper;
import com.tdmh.param.WXNoticeParam;
import com.tdmh.service.IWXNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author litairan on 2018/11/16.
 */
@Service("iWXNoticeService")
@Transactional(readOnly = true)
public class WXNoticeServiceImpl implements IWXNoticeService {

    @Autowired
    private WXNoticeMapper wxNoticeMapper;

    @Override
    public JsonData listData() {
        List<WXNoticeParam> wxNotices = wxNoticeMapper.listData();
        return wxNotices == null || wxNotices.size() == 0 ? JsonData.successMsg("查询结果为空"): JsonData.successData(wxNotices);
    }

    @Override
    @Transactional
    public JsonData create(WXNoticeParam param) {
        BeanValidator.check(param);
        int resultCount = wxNoticeMapper.create(param);
        if (resultCount == 0) {
            return JsonData.fail("新建公告失败");
        }
        return JsonData.successMsg("新建公告成功");
    }

    @Override
    @Transactional
    public JsonData delete(List<Integer> ids, Integer currentEmpId) {
        List<WXNoticeParam> wxNotices = new ArrayList<>();
        for (Integer id : ids) {
            WXNoticeParam wxNotice = wxNoticeMapper.getWXNoticeById(id);
            if (wxNotice == null) {
                return JsonData.fail("微信公告ID不存在");
            } else {
                wxNotice.setUpdateBy(currentEmpId);
                wxNotices.add(wxNotice);
            }
        }
        int resultCount = wxNoticeMapper.delete(wxNotices);
        if (resultCount == ids.size()) {
            return JsonData.successMsg("删除微信公告成功");
        } else {
            return JsonData.fail("删除微信公告失败");
        }
    }

    @Override
    public JsonData search(String wxNoticeTitle) {
        List<WXNoticeParam> wxNotices = wxNoticeMapper.search(wxNoticeTitle);
        return wxNotices == null || wxNotices.size() == 0 ? JsonData.successMsg("查询结果为空") : JsonData.success(wxNotices, "查询成功");
    }

    public WXNoticeParam getWXNoticeById(Integer id) {
        return wxNoticeMapper.getWXNoticeById(id);
    }
}
