package com.tdmh.service.impl;

import com.tdmh.common.JsonData;
import com.tdmh.entity.GasPrice;
import com.tdmh.entity.User;
import com.tdmh.entity.mapper.GasPriceMapper;
import com.tdmh.entity.mapper.UserMapper;
import com.tdmh.entity.mapper.UserOrdersMapper;
import com.tdmh.param.GasPriceParam;
import com.tdmh.util.CalculateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


/**
 * @author Administrator on 2018/10/11.
 */
@Service
public class GasPriceServiceImpl implements IGasPriceService {

    @Autowired
    private GasPriceMapper gasPriceMapper;

    @Autowired
    private UserOrdersMapper userOrdersMapper;

    @Autowired
    private UserMapper userMapper;

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

    @Override
    public BigDecimal findHasUsedGasInYear(Integer userId) {
        return userOrdersMapper.findHasUsedGasInYear(userId);
    }

    @Override
    public JsonData calAmount(Integer userId, BigDecimal orderGas) {
        User user = userMapper.getUserById(userId);
        GasPrice gasPrice = gasPriceMapper.findGasPriceByType(user.getUserType() ,user.getUserGasType());
        BigDecimal hasUsedGasNum = userOrdersMapper.findHasUsedGasInYear(userId);
        if(gasPrice != null){
            if(hasUsedGasNum == null) hasUsedGasNum = new BigDecimal(0);
            BigDecimal orderPayment = CalculateUtil.gasToPayment(orderGas.add(hasUsedGasNum), gasPrice);
            BigDecimal hasOrderPayment = CalculateUtil.gasToPayment(hasUsedGasNum, gasPrice);
            String msg = CalculateUtil.showGasPrice(orderGas,hasUsedGasNum,gasPrice);
            return JsonData.success(orderPayment.subtract(hasOrderPayment),msg);
        }
        return JsonData.successMsg("暂未配置天然气区间价格");
    }
}
