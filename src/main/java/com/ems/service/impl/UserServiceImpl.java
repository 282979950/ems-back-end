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
import com.ems.param.LockAccountParam;
import com.ems.service.IMeterService;
import com.ems.service.IUserService;
import com.ems.shiro.utils.ShiroUtils;
import com.ems.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户服务实现类
 *
 * @author litairan on 2018/8/9.
 */
@Service("iUserService")
@Transactional(readOnly = true)
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserMetersMapper userMetersMapper;

    @Autowired
    private UserOrdersMapper userOrdersMapper;

    @Autowired
    private IMeterService meterService;

    @Override
    public JsonData getAllArchives() {
        List<CreateArchiveParam> archives = userMapper.getAllArchives();
        return archives == null || archives.size() == 0 ? JsonData.successMsg("搜索结果为空") : JsonData.success(archives, "查询成功");
    }

    @Override
    @Transactional
    public JsonData addArchive(CreateArchiveParam param) {
        BeanValidator.check(param);
        //建档时默认将用户锁定
        param.setUserLocked(true);
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        param.setCreateBy(currentEmpId);
        param.setUpdateBy(currentEmpId);
        int resultCount = userMapper.addArchive(param);
        if (resultCount == 0) {
            return JsonData.fail("建档失败");
        }
        return JsonData.successMsg("建档成功");
    }

    @Override
    @Transactional
    public JsonData editArchive(CreateArchiveParam param) {
        BeanValidator.check(param);
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        param.setUpdateBy(currentEmpId);
        int resultCount = userMapper.editArchive(param);
        if (resultCount == 0) {
            return JsonData.fail("更新失败");
        }
        return JsonData.successMsg("更新成功");
    }

    @Override
    @Transactional
    public JsonData deleteArchive(List<Integer> ids) {
        List<User> users = new ArrayList<>();
        for (Integer id : ids) {
            users.add(userMapper.getUserById(id));
        }
        // TODO: 2018/8/27 已开户的档案不允许删除
        int resultCount = userMapper.deleteArchive(users);
        if (resultCount < users.size()) {
            return JsonData.fail("删除失败");
        }
        return JsonData.successMsg("删除成功");
    }

    @Override
    public JsonData searchArchive(Integer userId, String distName, String userAddress, Integer userType, Integer userGasType, Integer userStatus) {
        List<CreateArchiveParam> archives = userMapper.searchArchive(userId, distName, userAddress, userType, userGasType, userStatus);
        return archives == null || archives.size() == 0 ? JsonData.successMsg("搜索结果为空") : JsonData.success(archives, "查询成功");
    }

    @Override
    public JsonData getAllInstallMeters() {
        List<InstallMeterParam> meters = userMapper.getAllInstallMeters();
        return meters == null || meters.size() == 0 ? JsonData.successMsg("搜索结果为空") : JsonData.success(meters, "查询成功");
    }

    @Override
    public JsonData addInstallMeter(InstallMeterParam param) {
        return null;
    }

    @Override
    @Transactional
    public JsonData editInstallMeter(InstallMeterParam param) {
        BeanValidator.check(param);
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        Integer meterId = meterService.getMeterIdByMeterCode(param.getMeterCode());
        //更新表具安装时间
        Meter meter = new Meter();
        meter.setMeterId(meterId);
        meter.setMeterInstallTime(new Date());
        meter.setUpdateBy(currentEmpId);
        int resultCount = meterService.updateMeter(meter);
        if (resultCount == 0) {
            return JsonData.fail("更新表具安装时间失败");
        }
        //更新用户表具信息
        UserMeters userMeters = new UserMeters();
        // 判断当前用户的状态
        Integer userStatus = param.getUserStatus();
        if (userStatus.equals(1)) {
            Integer userId = param.getUserId();
            User user = getUserById(userId);
            user.setUpdateBy(currentEmpId);
            user.setUserStatus(2);
            List<User> users = new ArrayList<>();
            users.add(user);
            int resultCount2 = userMapper.updateUserStatus(users);
            if (resultCount2 == 0) {
                return JsonData.fail("更新用户表具信息失败");
            }
            userMeters.setUserId(param.getUserId());
            userMeters.setMeterId(meterId);
            userMeters.setCreateBy(currentEmpId);
            userMeters.setUpdateBy(currentEmpId);
            int resultCount3 = userMetersMapper.installMeter(userMeters);
            if (resultCount3 == 0) {
                return JsonData.fail("更新用户表具信息失败");
            }
            return JsonData.successMsg("更新挂表信息成功");
        } else {
            userMeters.setUserId(param.getUserId());
            userMeters.setMeterId(meterId);
            userMeters.setUpdateBy(currentEmpId);
            int resultCount4 =userMetersMapper.updateMeter(userMeters);
            if (resultCount4 == 0) {
                return JsonData.fail("更新用户表具信息失败");
            }
            return JsonData.successMsg("更新挂表信息成功");
        }
    }

    @Override
    @Transactional
    public JsonData deleteInstallMeter(List<Integer> ids) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        List<User> users = new ArrayList<>();
        for (Integer id : ids) {
            User user = getUserById(id);
            if (user.getUserStatus() == 1) {
                return JsonData.fail("存在未挂表的用户");
            }
            user.setUpdateBy(currentEmpId);
            users.add(getUserById(id));
        }
        int resultCount = userMapper.updateUserStatus(users);
        if (resultCount < users.size()) {
            return JsonData.fail("删除用户表具信息失败");
        }
        List<UserMeters> userMeters = new ArrayList<>();
        for (Integer id : ids) {
            UserMeters userMeter = userMetersMapper.getUserMeterById(id);
            userMeter.setUpdateBy(currentEmpId);
            userMeters.add(userMeter);
        }
        int resultCount2 = userMetersMapper.deleteInstallMeter(userMeters);
        if (resultCount2 < users.size()) {
            return JsonData.fail("删除用户表具信息失败");
        }
        return JsonData.successMsg("删除用户表具信息成功");
    }

    @Override
    public JsonData searchInstallMeter(Integer userId, String distName, String userAddress) {
        List<InstallMeterParam> meters = userMapper.searchInstallMeter(userId, distName, userAddress);
        return meters == null || meters.size() == 0 ? JsonData.successMsg("搜索结果为空") : JsonData.success(meters, "查询成功");
    }

    @Override
    @Transactional
    public JsonData createAccount(CreateAccountParam param) {
        BeanValidator.check(param);
        Integer iccardId = param.getUserId();
        param.setIccardId(iccardId);
        // TODO: 2018/8/9 从接口中读取IC卡识别号
        param.setIccardPassword(RandomUtils.generateHexString(6));
        //开户时将用户解锁
        param.setUserLocked(false);
        if (param.getUserDeed() == null) {
            param.setUserDeed("");
        }
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        param.setUpdateBy(currentEmpId);
        param.setUserStatus(3);
        int resultCount = userMapper.createAccount(param);
        if (resultCount == 0) {
            return JsonData.fail("用户开户失败");
        }
        //依据充值金额生成第一笔订单,表具的激活需要充值
        User user = getUserByIccardId(iccardId);
        UserOrders userOrders = new UserOrders();
        userOrders.setUserId(user.getUserId());
        userOrders.setEmployeeId(currentEmpId);
        BigDecimal gas = param.getOrderGas();
        userOrders.setOrderGas(gas);
        BigDecimal payment = param.getOrderPayment();
        userOrders.setOrderPayment(payment);
        userOrders.setCreateBy(currentEmpId);
        userOrders.setUpdateBy(currentEmpId);
        userOrders.setUsable(true);
        // TODO: 2018/8/10 完善订单流程
        int resultCount2 = userOrdersMapper.insert(userOrders);
        if (resultCount2 == 0) {
            return JsonData.fail("初始订单生成失败");
        }
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
     *
     */
    private User getUserById(Integer userId) {
        return userMapper.getUserById(userId);
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

    @Override
    public JsonData getAllNotAccountArchive() {
        List<CreateArchiveParam> archives = userMapper.searchArchive(null,null,null,null,null,2);
        return archives == null || archives.size() == 0 ? JsonData.successMsg("搜索结果为空") : JsonData.success(archives, "查询成功");

    }

    @Override
    public JsonData searchAllNotAccountArchive(Integer userId, String distName, String userAddress, Integer userType, Integer userGasType) {
        List<CreateArchiveParam> archives = userMapper.searchArchive(userId, distName, userAddress, userType, userGasType,2);
        return archives == null || archives.size() == 0 ? JsonData.successMsg("搜索结果为空") : JsonData.success(archives, "查询成功");
    }

    @Override
    public JsonData getAllAccountArchive() {
        List<LockAccountParam> accounts = userMapper.searchAccountArchive(null,null,null,3);
        return accounts == null || accounts.size() == 0 ? JsonData.successMsg("搜索结果为空") : JsonData.success(accounts, "查询成功");

    }

    @Override
    public JsonData searchAllAccountArchive(Integer userId,String userName,Integer iccardId) {
        List<LockAccountParam> accounts = userMapper.searchAccountArchive(userId, userName, iccardId , 3);
        return accounts == null || accounts.size() == 0 ? JsonData.successMsg("搜索结果为空") : JsonData.success(accounts, "查询成功");
    }

    @Override
    public JsonData updateLockStatus(LockAccountParam param) {
        BeanValidator.check(param);

        return null;

    }

    @Override
    public JsonData searchLockList(Integer userId) {
        return null;
    }


}
