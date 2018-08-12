package com.ems.service.impl;

import com.ems.common.BeanValidator;
import com.ems.common.Const;
import com.ems.common.JsonData;
import com.ems.entity.Meter;
import com.ems.entity.User;
import com.ems.entity.UserMeters;
import com.ems.entity.UserOrders;
import com.ems.entity.mapper.UserMapper;
import com.ems.entity.mapper.UserMetersMapper;
import com.ems.entity.mapper.UserOrdersMapper;
import com.ems.param.CreateAccountParam;
import com.ems.param.CreateArchiveParam;
import com.ems.param.InstallMeterParam;
import com.ems.service.IMeterService;
import com.ems.service.IUserService;
import com.ems.shiro.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户服务实现类
 *
 * @author litairan on 2018/8/9.
 */
@Service("iUserService")
@Transactional(readOnly = true)
public class UserServiceImpl implements IUserService {

    private static final int DEFAULT_ICCARD_ID = 10000000;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserMetersMapper userMetersMapper;

    @Autowired
    private UserOrdersMapper userOrdersMapper;

    @Autowired
    private IMeterService meterService;

    @Override
    @Transactional
    public JsonData createArchive(CreateArchiveParam param) {
        BeanValidator.check(param);
        //建档时默认将用户锁定
        param.setUserLocked(true);
        int resultCount = userMapper.createArchive(param);
        if (resultCount == 0) {
            return JsonData.fail("用户建档失败，用户信息：" + param.toString());
        }
        return JsonData.successMsg("用户建档成功");
    }

    @Override
    public JsonData installMeter(InstallMeterParam param) {
        BeanValidator.check(param);
        Integer meterId = meterService.getMeterIdByMeterCode(param.getMeterCode());
        //更新表具安装时间
        Meter meter = new Meter();
        meter.setMeterId(meterId);
        meter.setMeterInstallTime(new Date());
        int resultCount = meterService.updateMeter(meter);
        if (resultCount == 0) {
            return JsonData.fail("更新表具安装时间失败");
        }
        //更新用户表具信息
        UserMeters userMeters = new UserMeters();
        userMeters.setUserId(param.getUserId());
        userMeters.setMeterId(meterId);
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        userMeters.setCreateBy(currentEmpId);
        userMeters.setUpdateBy(currentEmpId);
        int resultCount2 = userMetersMapper.installMeter(userMeters);
        if (resultCount2 == 0) {
            return JsonData.fail("更新用户表具信息失败");
        }
        return JsonData.successMsg("更新挂表信息成功");
    }

    @Override
    @Transactional
    public JsonData createAccount(CreateAccountParam param) {
        BeanValidator.check(param);
        Integer iccardId = DEFAULT_ICCARD_ID + getAllCount();
        param.setIccardId(iccardId);
        // TODO: 2018/8/9 从接口中读取IC卡识别号
        param.setIccardPassword(Const.DEFAULT_ICCARD_PASSWORD);
        //开户时将用户解锁
        param.setUserLocked(false);
        if (param.getUserDeed() == null) {
            param.setUserDeed("");
        }
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        param.setUpdateBy(currentEmpId);
        int resultCount = userMapper.createAccount(param);
        if (resultCount == 0) {
            return JsonData.fail("用户开户失败");
        }
        //依据充值金额生成第一笔订单,表具的激活需要充值
        User user = getUserByIccardId(iccardId);
        UserOrders userOrders = new UserOrders();
        userOrders.setUserId(user.getUserId());
        userOrders.setEmployeeId(currentEmpId);
        // TODO: 2018/8/10 完善订单流程
        int resultCount2 = userOrdersMapper.insert(userOrders);
        if (resultCount2 == 0) {
            return JsonData.fail("初始订单生成失败");
        }
        BigDecimal payment = param.getOrderPayment();
        userOrders.setOrderPayment(payment);
        return JsonData.successMsg("用户开户成功");
    }


    /**
     * 获取用户总数量
     *
     * @return
     */
    private int getAllCount() {
        return userMapper.getAllCount();
    }


    /**
     * 依据IC卡卡号获取用户
     *
     * @param iccardId
     * @return
     */
    private User getUserByIccardId(Integer iccardId) {
        return userMapper.getUserByIccardId(iccardId);
    }
}
