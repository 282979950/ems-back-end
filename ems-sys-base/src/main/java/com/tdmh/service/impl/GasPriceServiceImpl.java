package com.tdmh.service.impl;

import com.tdmh.common.JsonData;
import com.tdmh.entity.GasPrice;
import com.tdmh.entity.mapper.GasPriceMapper;
import com.tdmh.param.GasPriceParam;
import com.tdmh.service.IGasPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author Administrator on 2018/10/11.
 */
@Service
public class GasPriceServiceImpl implements IGasPriceService {

    @Autowired
    private GasPriceMapper gasPriceMapper;

    @Override
    public JsonData listAllGasPrice() {
        List<GasPriceParam> list = gasPriceMapper.listAllGasPrice();
        return list == null || list.size() == 0 ? JsonData.successMsg("没有配置用户类型或者用气类型") : JsonData.successData(list);
    }

    @Override
    public GasPrice findGasPriceByType(Integer userType, Integer userGasType) {
        return gasPriceMapper.findGasPriceByType(userType , userGasType);
    }

    @Override
    public JsonData updateGasPrice(GasPriceParam param) {
        int resultCount = 0;
        if(param.getGasPriceId() == null){
            resultCount = gasPriceMapper.insert(param);
        }else{
            resultCount =gasPriceMapper.update(param);
        }
        if (resultCount == 0) {
            return JsonData.fail("设置阶梯气价失败");
        }
        return JsonData.successMsg("设置阶梯气价成功");
    }
}
