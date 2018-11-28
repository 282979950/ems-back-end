package com.tdmh.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.tdmh.common.BeanValidator;
import com.tdmh.common.JsonData;
import com.tdmh.entity.ServiceOutlet;
import com.tdmh.entity.mapper.ServiceOutletMapper;
import com.tdmh.service.IServiceOutletService;
import com.tdmh.utils.map.BaiDuMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author Liuxia on 2018/11/12.
 */
@Service
@Transactional(readOnly = true)
public class ServiceOutletServiceImpl implements IServiceOutletService {

    @Autowired
    private ServiceOutletMapper serviceOutletMapper;

    @Override
    public JsonData getAllSOLet() {
        List<ServiceOutlet> list = serviceOutletMapper.getAllSOLet();
        return list == null || list.size()==0 ? JsonData.successMsg("暂无营业网点"):JsonData.successData(list);
    }

    @Override
    public JsonData getAllSOLetWithPagination(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ServiceOutlet> list = serviceOutletMapper.getAllSOLet();
        if (list == null || list.size() == 0) {
            return JsonData.successMsg("暂无营业网点");
        }
        PageInfo<ServiceOutlet> page = new PageInfo<>(list);
        return JsonData.successData(page);
    }

    @Override
    @Transactional
    public JsonData createSOLet(ServiceOutlet serviceOutlet) {
        BeanValidator.check(serviceOutlet);
        getBdAndTxMap(serviceOutlet);
        serviceOutlet.setUsable(true);
        int resultCount = serviceOutletMapper.insert(serviceOutlet);
        if(resultCount >0) return JsonData.successMsg("新增营业网点成功");
        return JsonData.fail("新增营业网点失败");
    }

    @Override
    @Transactional
    public JsonData deleteSOLet(List<Integer> soletids, Integer currentEmpId) {
        List<ServiceOutlet> soletList = Lists.newArrayList();
        for(Integer sId : soletids) {
            ServiceOutlet solet = serviceOutletMapper.getSOLetById(sId);
            solet.setUpdateBy(currentEmpId);
            solet.setUsable(false);
            soletList.add(solet);
        }
        int resultCount = serviceOutletMapper.deleteBatch(soletList);
        if (resultCount == 0) {
            return JsonData.fail("删除营业网点失败");
        }
        return JsonData.successMsg("删除营业网点成功");
    }

    @Override
    @Transactional
    public JsonData updateSOLet(ServiceOutlet serviceOutlet) {
        BeanValidator.check(serviceOutlet);
        getBdAndTxMap(serviceOutlet);
        serviceOutlet.setUsable(true);
        int resultCount = serviceOutletMapper.update(serviceOutlet);
        if(resultCount >0) return JsonData.successMsg("修改营业网点成功");
        return JsonData.fail("修改营业网点失败");
    }

    private ServiceOutlet getBdAndTxMap(ServiceOutlet serviceOutlet){
        if(serviceOutlet.getServiceOutletAddress()!=""){
            Map<String,String> bdMap = BaiDuMap.getCoordinate(serviceOutlet.getServiceOutletAddress());
            if(bdMap != null && bdMap.size()>0){
                serviceOutlet.setBdLongitude(bdMap.get("lng"));
                serviceOutlet.setBdLatitude(bdMap.get("lat"));
                Map<String,String> txMap = BaiDuMap.geoConveter(bdMap);
                if(txMap != null && txMap.size()>0){
                    serviceOutlet.setTxLongitude(txMap.get("lng"));
                    serviceOutlet.setTxLatitude(txMap.get("lat"));
                }
            }
        }
        return serviceOutlet;
    }

    @Override
    public JsonData selectSOLet(String serviceOutletName, String serviceOutletAddress, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ServiceOutlet> list = serviceOutletMapper.selectSOLet(serviceOutletName, serviceOutletAddress);
        if (list == null || list.size() == 0) {
            return JsonData.successMsg("暂无营业网点");
        }
        PageInfo<ServiceOutlet> page = new PageInfo<>(list);
        return JsonData.success(page, "查询成功");
    }
}
