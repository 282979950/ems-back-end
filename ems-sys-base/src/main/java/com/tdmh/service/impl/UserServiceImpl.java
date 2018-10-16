package com.tdmh.service.impl;


import com.tdmh.common.BeanValidator;
import com.tdmh.common.JsonData;
import com.tdmh.entity.*;
import com.tdmh.entity.mapper.UserCardMapper;
import com.tdmh.entity.mapper.UserMapper;
import com.tdmh.entity.mapper.UserMetersMapper;
import com.tdmh.entity.mapper.UserOrdersMapper;
import com.tdmh.param.CreateAccountParam;
import com.tdmh.param.CreateArchiveParam;
import com.tdmh.param.InstallMeterParam;
import com.tdmh.param.LockAccountParam;
import com.tdmh.service.IMeterService;
import com.tdmh.service.IUserService;
import com.tdmh.utils.RandomUtils;
import com.tdmh.utils.StringUtils;
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
    private UserCardMapper userCardMapper;

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
    public JsonData searchArchive(Integer userId, Integer userDistId, String userAddress, Integer userType, Integer userGasType, Integer userStatus) {
        List<CreateArchiveParam> archives = userMapper.searchArchive(userId, userDistId, userAddress, userType, userGasType, userStatus);
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
        Integer meterId = meterService.getMeterIdByMeterCode(param.getMeterCode());
        //更新表具安装时间
        Meter meter = new Meter();
        meter.setMeterId(meterId);
        meter.setMeterInstallTime(new Date());
        meter.setUpdateBy(param.getUpdateBy());
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
            user.setUpdateBy(param.getUpdateBy());
            user.setUserStatus(2);
            List<User> users = new ArrayList<>();
            users.add(user);
            int resultCount2 = userMapper.updateUserStatus(users);
            if (resultCount2 == 0) {
                return JsonData.fail("更新用户表具信息失败");
            }
            userMeters.setUserId(param.getUserId());
            userMeters.setMeterId(meterId);
            userMeters.setCreateBy(param.getUpdateBy());
            userMeters.setUpdateBy(param.getUpdateBy());
            int resultCount3 = userMetersMapper.installMeter(userMeters);
            if (resultCount3 == 0) {
                return JsonData.fail("更新用户表具信息失败");
            }
            return JsonData.successMsg("更新挂表信息成功");
        } else {
            userMeters.setUserId(param.getUserId());
            userMeters.setMeterId(meterId);
            userMeters.setUpdateBy(param.getUpdateBy());
            int resultCount4 =userMetersMapper.updateMeter(userMeters);
            if (resultCount4 == 0) {
                return JsonData.fail("更新用户表具信息失败");
            }
            return JsonData.successMsg("更新挂表信息成功");
        }
    }

    @Override
    @Transactional
    public JsonData deleteInstallMeter(List<Integer> ids,Integer currentEmpId) {
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
    public JsonData searchInstallMeter(Integer userId, Integer userDistId, String userAddress) {
        List<InstallMeterParam> meters = userMapper.searchInstallMeter(userId, userDistId, userAddress);
        return meters == null || meters.size() == 0 ? JsonData.successMsg("搜索结果为空") : JsonData.success(meters, "查询成功");
    }

    @Override
    @Transactional
    public JsonData createAccount(CreateAccountParam param) {
        BeanValidator.check(param);
        Integer iccardId = Integer.valueOf(param.getUserId().toString().substring(2))+10000000;
        param.setIccardId(iccardId);
        // TODO: 2018/8/9 从接口中读取IC卡识别号
        param.setIccardPassword(RandomUtils.generateHexString(6));
        //开户时将用户解锁
        param.setUserLocked(false);
        if (param.getUserDeed() == null) {
            param.setUserDeed("");
        }
        param.setUserStatus(3);
        int resultCount = userMapper.createAccount(param);
        if (resultCount == 0) {
            return JsonData.fail("用户开户失败");
        }
        //依据充值金额生成第一笔订单,表具的激活需要充值
        UserCard userCard = userCardMapper.getUserCardByUserIdAndCardId(param.getUserId(),param.getIccardId());
        if(userCard == null ) userCard = new UserCard();
        userCard.setUserId(param.getUserId());
        userCard.setCardId(param.getIccardId());
        userCard.setCardIdentifier(param.getIccardIdentifier());
        userCard.setCardPassword(param.getIccardPassword());
        userCard.setCardInitialization(true);
        userCard.setUsable(true);
        userCard.setCreateBy(param.getUpdateBy());
        userCard.setUpdateBy(param.getUpdateBy());
        int resultCount1;
        if(userCard.getUserCardId() == null) {
            resultCount1 = userCardMapper.insert(userCard);
        }else{
            resultCount1 = userCardMapper.update(userCard);
        }
        if (resultCount1 == 0) {
            return JsonData.fail("用户初始化卡失败");
        }
        UserOrders userOrders = new UserOrders();
        userOrders.setUserId(param.getUserId());
        userOrders.setEmployeeId(param.getUpdateBy());
        BigDecimal gas = param.getOrderGas();
        userOrders.setOrderGas(gas);
        BigDecimal payment = param.getOrderPayment();
        userOrders.setOrderPayment(payment);
        userOrders.setCreateBy(param.getUpdateBy());
        userOrders.setUpdateBy(param.getUpdateBy());
        userOrders.setOrderStatus(2);
        userOrders.setUsable(true);
        // TODO: 2018/8/10 完善订单流程
        int resultCount2 = userOrdersMapper.insert(userOrders);
        if (resultCount2 == 0) {
            return JsonData.fail("初始订单生成失败");
        }
        return JsonData.success(param,"用户开户成功");
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
    public JsonData searchAllNotAccountArchive(Integer userId, Integer userDistId, String userAddress, Integer userType, Integer userGasType) {
        List<CreateArchiveParam> archives = userMapper.searchArchive(userId, userDistId, userAddress, userType, userGasType,2);
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
    @Transactional
    public JsonData updateLockStatus(LockAccountParam param) {
        BeanValidator.check(param);
        UserLock userLock = new UserLock();
        userLock.setUserId(param.getUserId());
        userLock.setIsLock(!param.getIsLock());
        userLock.setLockReason(param.getLockReason());
        userLock.setCreateBy(param.getCreateBy());
        userLock.setCreateTime(new Date());
        userLock.setUpdateBy(param.getUpdateBy());
        userLock.setUpdateTime(new Date());
        int resultCount = userMapper.insertUserLock(userLock);
        if (resultCount == 0) {
            return JsonData.fail("锁定/解锁失败");
        }
        return JsonData.successMsg("锁定/解锁成功");

    }

    @Override
    public JsonData searchLockList(Integer userId) {
        List<UserLock> userLocks= userMapper.searchLockList(userId);
        return  userLocks == null || userLocks.size() == 0 ? JsonData.successMsg("搜索结果为空") : JsonData.success(userLocks, "查询成功");
    }
    @Override
   public  JsonData cardService(Integer cardId){
        UserCard card=new UserCard();
        if(cardId.intValue()==0){
            return  JsonData.fail("卡号为0，请确认卡或该卡已初始化");
        }
        //查询是否存在该条数据
        String pwd=null;
        int resultCount = userCardMapper.countUserCardBycardId(cardId);
        if(resultCount>0){
            //查看密码(一般情况下用户在开卡时会生成卡密码)
        pwd = userCardMapper.userCardPwdBycardId(cardId);

        if(StringUtils.isBlank(pwd)){
            return  JsonData.fail("未查询到卡相关信息");
        }else{
            card.setCardPassword(pwd);
        }
    }else{
        return  JsonData.fail("未查询到相关数据");
    }

        return JsonData.success(card,"查询成功");

    }
    @Override
    @Transactional
    public JsonData cardInitService( Integer cardId,String result){
        UserCard card=new UserCard();

        if(StringUtils.isBlank(result)){
            return  JsonData.fail("未获取到初始化结果");

        }
        if( cardId ==null || cardId.intValue()==0){

            JsonData.fail("未获取到卡号码");
        }else{
            card.setCardId(cardId);
            card.setCardInitialization(false);
        }
        userCardMapper.initCardPwdBycardId(card);
        return null;
    }

    @Override
    public JsonData searchAccountById(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        return user == null ? JsonData.successMsg("搜索结果为空") : JsonData.success(user, "查询成功");
    }
}
