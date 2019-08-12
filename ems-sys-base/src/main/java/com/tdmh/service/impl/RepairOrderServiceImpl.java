package com.tdmh.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tdmh.common.BeanValidator;
import com.tdmh.common.JsonData;
import com.tdmh.entity.*;
import com.tdmh.entity.mapper.RepairOrderMapper;
import com.tdmh.entity.mapper.UserCardMapper;
import com.tdmh.entity.mapper.UserMetersMapper;
import com.tdmh.param.BindNewCardParam;
import com.tdmh.param.FillGasOrderParam;
import com.tdmh.param.RepairOrderParam;
import com.tdmh.param.RepairOrderUserParam;
import com.tdmh.service.*;
import com.tdmh.util.CalculateUtil;
import com.tdmh.utils.DateUtils;
import com.tdmh.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author litairan on 2018/10/12.
 */
@Service("iRepairOrderService")
@Transactional(readOnly = true)
public class RepairOrderServiceImpl implements IRepairOrderService {

    @Autowired
    private RepairOrderMapper repairOrderMapper;

    @Autowired
    private UserMetersMapper userMetersMapper;

    @Autowired
    private UserCardMapper userCardMapper;

    @Autowired
    private IUserService userService;

    @Autowired
    private IMeterService meterService;

    @Autowired
    private IFillGasService fillGasService;

    @Autowired
    private IGasPriceService gasPriceService;

    @Override
    public JsonData listData(Integer pageNum, Integer pageSize, String isAdmin, Integer userType, Integer currentEmpId) {
        List<RepairOrderParam> orderParams = null;
        Integer createBy = null;
        /*
         *判断是否为admin或者用户类型为管理员，不是查询个人产生的记录
         */
        if(StringUtils.isNotBlank(isAdmin) && "admin".equals(isAdmin) ||userType.intValue()== 2){
            PageHelper.startPage(pageNum, pageSize);
            orderParams = repairOrderMapper.listData(createBy);
        }else{
            //若不是管理员则查询个人产生的数据
            createBy = currentEmpId;
            PageHelper.startPage(pageNum, pageSize);
            orderParams = repairOrderMapper.listData(createBy);
        }
        PageInfo<RepairOrderParam> info = new PageInfo<>(orderParams);
        return JsonData.successData(info);
    }

    @Override
    @Transactional
    public JsonData createRepairOrder(RepairOrderParam param) {
        BeanValidator.check(param);
        // 当存在待处理的维修单时需要先撤销旧的维修单
        if (hasPendingRepairOrder(param.getUserId())) {
            return JsonData.fail("当前用户存在待处理的维修单，需要先撤销旧的维修单才能新建维修单");
        }
        if (hasProcessingRepairOrder(param.getUserId())) {
            return JsonData.fail("当前用户存在处理中的维修单，需要处理完维修单后才能新建维修单");
        }
        String repairOrderId = param.getRepairOrderId();
        int number = DateUtils.temporalComparison(param.getRepairStartTime(),param.getRepairEndTime(),"yyyy-MM-dd HH:mm");
        if (number == 1) {
            return JsonData.fail("操作有误!维修开始时间需比维修结束时间早");
        }
        if (checkRepairOrderExists(repairOrderId)){
            return JsonData.fail("维修单已存在，不能重复录入");
        }
        String userStatus = userService.getUserStatusNameByUserId(param.getUserId());
        if(StringUtils.isNotBlank(userStatus) && "已挂表待开户".equals(userStatus)){
            return JsonData.fail("该户号尚未开户，请先开户");
        }
        String newMeterCode = param.getNewMeterCode();
        if (newMeterCode != null && checkNewMeterInstalled(newMeterCode) && checkNewMeterScrapped(newMeterCode)) {
            return JsonData.fail("新表已经被其他用户使用");
        }

        Integer userId = param.getUserId();
        int count = userCardMapper.countAccountCancellation(userId);
        if(count>0){
            return JsonData.fail("已销户的【"+userId+"】无法录入维修单");
        }
        //若是报停拆表处理结果必须为拆表，否则提示
        if(param.getRepairType().equals(5) && !param.getRepairResultType().equals(33)){
            return JsonData.fail("请选择处理结果【拆表】");
        }
        if (checkNeedFillGas(param)) {
            userService.updateServiceTimesByUserId(param.getUserId());
            String oldMeterCode = param.getOldMeterCode();
            Integer oldMeterId = meterService.getMeterIdByMeterCode(oldMeterCode);
            Meter oldMeter = meterService.getMeterByMeterId(oldMeterId);
            oldMeter.setMeterStopCode(param.getOldMeterStopCode());
            // 判断是否为换表
            if (param.getRepairType().equals(0) || param.getRepairType().equals(6) || param.getRepairType().equals(7)) {
                oldMeter.setMeterScrapTime(new Date());
                oldMeter.setMeterStatus(3);
                oldMeter.setUsable(false);
                meterService.updateMeter(oldMeter);
                // 更新新表止码
                Integer newMeterId = meterService.getMeterIdByMeterCode(newMeterCode);
                Meter newMeter = meterService.getMeterByMeterId(newMeterId);
                newMeter.setMeterInitialStopCode(param.getNewMeterStopCode());
                newMeter.setMeterStopCode(param.getNewMeterStopCode());
                newMeter.setMeterInstallTime(new Date());
                newMeter.setMeterStatus(2);
                meterService.updateMeter(newMeter);
                //更新表具绑定关系
                deleteOldMeter(userId);
                installNewMeter(userId, newMeterId, param.getCreateBy());
            } else {
                oldMeter.setUsable(true);
                meterService.updateMeter(oldMeter);
            }
            // 锁定历史订单
//            repairOrderMapper.lockRepairOrderByUserId(userId);
            param.setRepairOrderStatus(1);
            int resultCount = repairOrderMapper.addRepairOrder(param);
            if (resultCount == 0) {
                return JsonData.fail("新增维修单失败");
            } else {
                switch (createFillGasOrder(param)) {
                    case 1:
                        return JsonData.successMsg("新增维修单成功，该用户需要补气");
                    case -1:
                        return JsonData.successMsg("新增维修单成功，该用户需要补缴");
                    case 2:
                        return JsonData.successMsg("新增维修单成功，请前往\"账户\"->\"账户变更\"->\"账户销户\"进行销户处理");
                    default:
                        // 无需补气补缴的单子将订单状态处理为已处理
                        repairOrderMapper.updateRepairOrderStatus(param.getRepairOrderId(), 4);
                        return JsonData.successMsg("新增维修单成功，请前往IC卡初始化页面将卡片初始化");
                }
            }
        } else {
            if (fillGasService.hasUnfinishedFillGasOrder(userId)) {
                return JsonData.fail("该用户有未处理的补气单不能提交普通维修单");
            }
            // 销户单更新表止码
            if (checkCloseAccount(param)) {
                String oldMeterCode = param.getOldMeterCode();
                Integer oldMeterId = meterService.getMeterIdByMeterCode(oldMeterCode);
                Meter oldMeter = meterService.getMeterByMeterId(oldMeterId);
                oldMeter.setMeterStopCode(param.getOldMeterStopCode());
                oldMeter.setMeterScrapTime(new Date());
                oldMeter.setMeterStatus(3);
                oldMeter.setUsable(false);
                meterService.updateMeter(oldMeter);
                deleteOldMeter(userId);
            }
            // 锁定历史订单
//            repairOrderMapper.lockRepairOrderByUserId(userId);
            // 无需处理的订单
            param.setRepairOrderStatus(3);
            int resultCount = repairOrderMapper.addRepairOrder(param);
            if (resultCount == 0) {
                return JsonData.fail("新增维修单失败");
            } else {
                return JsonData.successMsg("新增维修单成功");
            }
        }
    }

    private boolean hasPendingRepairOrder(Integer userId) {
        return repairOrderMapper.countPendingRepairOrder(userId) > 0;
    }

    private boolean hasProcessingRepairOrder(Integer userId) {
        return repairOrderMapper.countProcessingRepairOrder(userId) > 0;
    }

    @Override
    @Transactional
    public JsonData editRepairOrder(RepairOrderParam param) {
        BeanValidator.check(param);
        int number = DateUtils.temporalComparison(param.getRepairStartTime(),param.getRepairEndTime(),"yyyy-MM-dd HH:mm");
        if (number == 1) {
            return JsonData.fail("操作有误!维修开始时间需比维修结束时间早");
        }
        RepairOrderParam old = repairOrderMapper.getRepairOrderById(param.getId());
        Integer repairType = param.getRepairType();
        if (repairType.equals(0) || repairType.equals(6) || repairType.equals(7)) {
            String newMeterCode = param.getNewMeterCode();
            if (newMeterCode != null && checkNewMeterInstalled(param.getNewMeterCode()) && checkNewMeterScrapped(param.getNewMeterCode())) {
                return JsonData.fail("新表已经被其他用户使用");
            }
            recoverOldRepairOrder(param.getId(), param.getUpdateBy());
            //更新旧表信息
            String oldMeterCode = param.getOldMeterCode();
            Integer oldMeterId = meterService.getMeterIdByMeterCode(oldMeterCode);
            Meter oldMeter = new Meter();
            oldMeter.setMeterId(oldMeterId);
            oldMeter.setMeterStopCode(param.getOldMeterStopCode());
            oldMeter.setMeterStatus(3);
            oldMeter.setMeterScrapTime(new Date());
            meterService.updateMeter(oldMeter);
            //更新新表
            if (newMeterCode != null) {
                Integer newMeterId = meterService.getMeterIdByMeterCode(newMeterCode);
                Meter newMeter = meterService.getMeterByMeterId(newMeterId);
                newMeter.setMeterStopCode(param.getNewMeterStopCode());
                newMeter.setMeterInstallTime(new Date());
                newMeter.setMeterStatus(2);
                meterService.updateMeter(newMeter);
                installNewMeter(param.getUserId(),newMeterId, param.getCreateBy());
            }
            int resultCount = repairOrderMapper.editRepairOrder(param);
            if (resultCount == 0) {
                return JsonData.fail("编辑维修单失败");
            }
            switch (createFillGasOrder(param)) {
                case 1:
                    return JsonData.successMsg("编辑维修单成功，该用户需要补气");
                case -1:
                    return JsonData.successMsg("编辑维修单成功，该用户需要补缴");
                case 2:
                    return JsonData.successMsg("编辑维修单成功，请前往\"账户\"->\"账户变更\"->\"账户销户\"进行销户处理");
                default:
                    return JsonData.successMsg("编辑维修单成功，请前往IC卡初始化页面将卡片初始化");
            }
        } else {
            repairOrderMapper.editRepairOrder(param);
            String oldMeterCode = param.getOldMeterCode();
            Integer oldMeterId = meterService.getMeterIdByMeterCode(oldMeterCode);
            Meter oldMeter = new Meter();
            oldMeter.setMeterId(oldMeterId);
            oldMeter.setMeterStopCode(param.getOldMeterStopCode());
            meterService.updateMeter(oldMeter);
            // 判断是否为补气单
            if (checkNeedFillGas(param)) {
                // 判断是否需要增加维修次数
                if (!checkNeedFillGas(old)) {
                    userService.updateServiceTimesByUserId(param.getUserId());
                }
                // 补气单
                switch (createFillGasOrder(param)) {
                    case -1:
                        return JsonData.successMsg("编辑维修单成功，该用户需要补缴");
                    case 1:
                        return JsonData.successMsg("编辑维修单成功，该用户需要补气");
                    case 2:
                        return JsonData.successMsg("编辑维修单成功，请前往\"账户\"->\"账户变更\"->\"账户销户\"进行销户处理");
                    default:
                        return JsonData.successMsg("编辑维修单成功，请前往IC卡初始化页面将卡片初始化");
                }
            } else {
                return JsonData.successMsg("编辑维修单成功");
            }
        }
    }

    @Override
    @Transactional
    public JsonData cancelRepairOrder(RepairOrderParam param) {
        Integer status = param.getRepairOrderStatus();
        if (status.equals(1)) {
            // 判断是否为换表
            Integer repairType = param.getRepairType();
            if (repairType.equals(0) || repairType.equals(6) || repairType.equals(7)) {
                String oldMeterCode = param.getOldMeterCode();
                String newMeterCode = param.getNewMeterCode();
                // 还原表具关联关系
                restoreMeter(param.getUserId(), oldMeterCode, newMeterCode);
                // 撤销补气单
                fillGasService.cancelFillGasByUserId(param.getUserId());
                // 撤销维修单
                repairOrderMapper.cancelRepairOrder(param);
                return JsonData.successMsg("撤销维修单成功");
            } else {
                // 撤销维修单
                repairOrderMapper.cancelRepairOrder(param);
                // 撤销补气单
                fillGasService.cancelFillGasByUserId(param.getUserId());
                return JsonData.successMsg("撤销维修单成功");
            }
        } else {
            // 将订单状态设置为4
            return JsonData.success(repairOrderMapper.cancelRepairOrder(param), "撤销维修单成功");
        }
    }

    @Override
    public void updateRepairOrderStatus(String repairOrderId, int i) {
        repairOrderMapper.updateRepairOrderStatus(repairOrderId, i);
    }

    private void restoreMeter(Integer userId,String oldMeterCode, String newMeterCode) {
        // 将旧表设置为可用
        Meter oldMeter = meterService.getMeterByMeterId(meterService.getMeterIdByMeterCode(oldMeterCode));
        oldMeter.setUsable(true);
        oldMeter.setMeterStatus(2);
        oldMeter.setMeterScrapTime(null);
        meterService.updateMeter(oldMeter);
        // 将新表设置为入库状态
        Meter newMeter = meterService.getMeterByMeterId(meterService.getMeterIdByMeterCode(newMeterCode));
        newMeter.setMeterStatus(1);
        meterService.updateMeter(newMeter);
        //删除表具关联关系
        UserMeters oldUserMeter = userMetersMapper.getUserMeterByUserIdAndMeterId(userId, oldMeter.getMeterId());
        oldUserMeter.setUsable(true);
        oldUserMeter.setUpdateTime(new Date());
        userMetersMapper.updateMeter(oldUserMeter);
        userMetersMapper.deleteUserMeterByUserIdAndMeterId(userId, oldMeter.getMeterId());
    }

    @Override
    public JsonData searchRepairOrder(String repairOrderId, Integer userId, Integer repairType, String empName, Integer pageNum, Integer pageSize, Integer currentEmpId, String isAdmin, Integer userType) {
        List<RepairOrderParam> orderParams = null;
        Integer createBy = null;
        /*
         *判断是否为admin或者用户类型为管理员，不是查询个人产生的记录
         */
        if(StringUtils.isNotBlank(isAdmin) && "admin".equals(isAdmin) ||userType.intValue()== 2){
            PageHelper.startPage(pageNum, pageSize);
            orderParams = repairOrderMapper.searchQueryRepairOrder(repairOrderId, userId, repairType, empName, createBy);
        }else{
            //若不是管理员则查询个人产生的数据
            createBy = currentEmpId;
            PageHelper.startPage(pageNum, pageSize);
            orderParams = repairOrderMapper.searchQueryRepairOrder(repairOrderId, userId, repairType, empName, createBy);
        }
        PageInfo<RepairOrderParam> info = new PageInfo<>(orderParams);
        return JsonData.success(info, "查询成功");
    }

    @Override
    public JsonData getRepairOrderUserById(Integer userId) {
        RepairOrderUserParam user = repairOrderMapper.getRepairOrderUserById(userId);
        return user == null ? JsonData.successMsg("查询结果为空"): JsonData.successData(user);
    }

    @Override
    public JsonData hasFillGasOrderResolved(Integer userId, String repairOrderId) {
        return JsonData.successData(fillGasService.hasFillGasOrderResolved(userId, repairOrderId));
    }

    @Override
    public JsonData isLatestFillGasOrder(Integer id, Integer userId) {
        List<RepairOrderParam> orderParams = repairOrderMapper.searchRepairOrder(null, userId, null, null);
        for (RepairOrderParam order : orderParams) {
            if (order.getId().compareTo(id) > 0) {
                return JsonData.successData(false);
            }
        }
        return JsonData.successData(true);
    }

    @Override
    public JsonData getBindNewCardParamByUserId(Integer userId) {
        return userService.getBindNewCardParamByUserId(userId);
    }

    @Override
    @Transactional
    public JsonData bindNewCard(BindNewCardParam param) {
        BeanValidator.check(param);
        String newCardIdentifier = param.getNewCardIdentifier();
        if (checkNewCardIdentifier(newCardIdentifier)) {
            return JsonData.fail("该卡已被其他用户使用");
        }
        UserCard oldCard = new UserCard();
        oldCard.setUserCardId(param.getUserCardId());
        oldCard.setUserId(param.getUserId());
        oldCard.setCardId(param.getCardId());
        oldCard.setCardIdentifier(param.getOldCardIdentifier());
        oldCard.setCardPassword(param.getCardPassword());
        oldCard.setUsable(false);
        oldCard.setUpdateBy(param.getUpdateBy());
        userCardMapper.update(oldCard);
        UserCard newCard = new UserCard();
        newCard.setUserId(param.getUserId());
        newCard.setCardId(param.getCardId());
        newCard.setCardIdentifier(param.getNewCardIdentifier());
        newCard.setCardPassword(param.getCardPassword());
        newCard.setCardInitialization(true);
        newCard.setCardCost(param.getCardCost());
        newCard.setCreateBy(param.getUpdateBy());
        newCard.setUpdateBy(param.getUpdateBy());
        newCard.setUsable(true);
        int resultCount = userCardMapper.insert(newCard);
        if (resultCount == 0) {
            return JsonData.fail("绑定新卡失败");
        }
        return JsonData.successMsg("绑定新卡成功 ");
    }

    /**
     * 判断卡片是否被使用过
     * @param newCardIdentifier
     * @return
     */
    private boolean checkNewCardIdentifier(String newCardIdentifier) {
        return userCardMapper.checkNewCardIdentifier(newCardIdentifier);
    }

    @Override
    public JsonData selectHistoryRepairOrderQueryService(Integer userId) {
        List<RepairOrderParam> list = repairOrderMapper.selectRepairOrderByuserQuery(userId);
        return list.size()==0?JsonData.successMsg("未查询到相关数据"):JsonData.success(list,"查询成功");
    }

    @Override
    public List<UserCard> selectUserCardByUserIdService(Integer userId) {
        return userCardMapper.selectUserCardByUserId(userId,false);
    }

    private boolean checkRepairOrderExists(String repairOrderId) {
        return repairOrderMapper.checkRepairOrderExists(repairOrderId);
    }

    /**
     * 校验新表是否被安装
     *
     * @param meterCode
     * @return
     */
    private boolean checkNewMeterInstalled(String meterCode) {
        Integer meterId = meterService.getMeterIdByMeterCode(meterCode);
        Meter meter = meterService.getMeterByMeterId(meterId);
        return meter.getMeterStatus().equals(2);
    }
    /**
     * 校验新表是否报废
     *
     * @param meterCode
     * @return
     */
    private boolean checkNewMeterScrapped(String meterCode) {
        Integer meterId = meterService.getMeterIdByMeterCode(meterCode);
        Meter meter = meterService.getMeterByMeterId(meterId);
        return meter.getMeterStatus().equals(3);
    }

    /**
     * 将之前新表恢复到未安装状态
     */
    private void recoverOldRepairOrder(Integer id, Integer updateBy) {
        RepairOrderParam repairOrder = repairOrderMapper.getRepairOrderById(id);
        Integer newMeterId = repairOrder.getNewMeterId();
        if (newMeterId != null) {
            Meter newMeter = meterService.getMeterByMeterId(newMeterId);
            meterService.clearInstallInfo(newMeter);
            deleteOldMeter(repairOrder.getUserId());
        }
    }

    private void deleteOldMeter(Integer userId) {
        UserMeters userMeter = userMetersMapper.getUserMeterById(userId);
        List<UserMeters> userMetersList = new ArrayList<>();
        userMetersList.add(userMeter);
        userMetersMapper.deleteInstallMeter(userMetersList);
    }

    private void installNewMeter(Integer userId, Integer newMeterId, Integer createBy) {
        UserMeters newUserMeter = new UserMeters();
        newUserMeter.setMeterId(newMeterId);
        newUserMeter.setUserId(userId);
        newUserMeter.setCreateBy(createBy);
        newUserMeter.setUpdateBy(createBy);
        userMetersMapper.installMeter(newUserMeter);
    }

    /**
     * 维修类型为换表，维修处理结果为换测控版或清零开票时需要补气
     * @param param
     * @return
     */
    private boolean checkNeedFillGas(RepairOrderParam param) {
        return param.getRepairType().equals(0) || param.getRepairType().equals(6) || param.getRepairType().equals(7) || param.getRepairResultType().equals(4)
                || param.getRepairResultType().equals(9);
    }

    private boolean checkCloseAccount(RepairOrderParam param) {
        return param.getRepairType().equals(5) && param.getRepairResultType().intValue()==33;
    }

    /**
     * 新增维修补气单
     */
    private int createFillGasOrder(RepairOrderParam param) {
        Integer userId = param.getUserId();
        String repairOrderId = param.getRepairOrderId();
        BigDecimal sumOrderGas = fillGasService.getSumOrderGasByUserId(userId);
        BigDecimal sumMeterStopCode = fillGasService.getSumMeterStopCodeByUserId(userId);
        // 当用户为销户维修单时，走销户流程
        if (checkCloseAccount(param)) {
            return 2;
        } else {
            switch (sumOrderGas.compareTo(sumMeterStopCode)) {
                case 1:
                    // 补气
                    if(checkNeedFillGas(param)) {
                        FillGasOrderParam fillGasOrder = new FillGasOrderParam();
                        fillGasOrder.setUserId(userId);
                        fillGasOrder.setRepairOrderId(repairOrderId);
                        fillGasOrder.setGasCount(sumOrderGas);
                        fillGasOrder.setStopCodeCount(sumMeterStopCode);
                        BigDecimal needFillGas = sumOrderGas.subtract(sumMeterStopCode);
                        fillGasService.setFillGasOrderProps(fillGasOrder, needFillGas);
                        fillGasOrder.setFillGasOrderStatus(0);
                        fillGasOrder.setCreateBy(param.getCreateBy());
                        fillGasOrder.setUpdateBy(param.getCreateBy());
                        fillGasService.createFillGasOrder(fillGasOrder);
                        return 1;
                    } else {
                        return 0;
                    }
                case -1:
                    // 超用补缴
                    if (checkNeedFillGas(param)) {
                        FillGasOrderParam fillGasOrder = new FillGasOrderParam();
                        fillGasOrder.setUserId(userId);
                        fillGasOrder.setRepairOrderId(repairOrderId);
                        fillGasOrder.setGasCount(sumOrderGas);
                        fillGasOrder.setStopCodeCount(sumMeterStopCode);
                        BigDecimal overusedGas = sumMeterStopCode.subtract(sumOrderGas);
                        BigDecimal gasInYear = gasPriceService.findHasUsedGasInYear(userId);
                        User user = userService.getUserById(userId);
                        GasPrice gasPrice = gasPriceService.findGasPriceByType(user.getUserType(), user.getUserGasType());
                        BigDecimal needFillMoney = CalculateUtil.gasToPayment(gasInYear.add(overusedGas), gasPrice).subtract(CalculateUtil.gasToPayment(gasInYear,
                                gasPrice));
                        fillGasService.setOveruseOrderProps(fillGasOrder, overusedGas, needFillMoney);
                        fillGasOrder.setFillGasOrderStatus(0);
                        fillGasService.createFillGasOrder(fillGasOrder);
                        return -1;
                    } else {
                        return 0;
                    }
                default:
                    // 不需要补气或超用结算
                    if (fillGasService.hasUnfinishedFillGasOrder(userId)) {
                        fillGasService.cancelFillGasByUserId(userId);
                    }
                    //不需要补气时修改维修单为已处理
                    repairOrderMapper.updateRepairOrderStatus(repairOrderId,4);
                    return 0;
            }
        }
    }
}
