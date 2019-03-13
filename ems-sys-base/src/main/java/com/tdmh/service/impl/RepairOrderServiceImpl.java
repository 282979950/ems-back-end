package com.tdmh.service.impl;

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
    public JsonData listData() {
        List<RepairOrderParam> orderParams = repairOrderMapper.listData();
        return orderParams == null || orderParams.size() == 0 ? JsonData.successMsg("查询结果为空") : JsonData.successData(orderParams);
    }

    @Override
    @Transactional
    public JsonData createRepairOrder(RepairOrderParam param) {
        BeanValidator.check(param);
        String repairOrderId = param.getRepairOrderId();
        if (checkRepairOrderExists(repairOrderId)){
            return JsonData.fail("维修单已存在，不能重复录入");
        }
        String newMeterCode = param.getNewMeterCode();
        if (newMeterCode != null && checkNewMeterInstalled(newMeterCode) && checkNewMeterScrapped(newMeterCode)) {
            return JsonData.fail("新表已经被其他用户使用");
        }
        Integer userId = param.getUserId();
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
                        return JsonData.successMsg("新增维修单成功，请前往IC卡初始化页面将卡片初始化");
                }
            }
        } else {
            int resultCount = repairOrderMapper.addRepairOrder(param);
            if (resultCount == 0) {
                return JsonData.fail("新增维修单失败");
            } else {
                return JsonData.successMsg("新增维修单成功");
            }
        }
    }

    @Override
    @Transactional
    public JsonData editRepairOrder(RepairOrderParam param) {
        BeanValidator.check(param);
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
    public JsonData searchRepairOrder(String repairOrderId, Integer userId, Integer repairType, String empName) {
        List<RepairOrderParam> orderParams = repairOrderMapper.searchRepairOrder(repairOrderId, userId, repairType, empName);
        return orderParams == null || orderParams.size() == 0 ? JsonData.successMsg("查询结果为空") : JsonData.success(orderParams, "查询成功");
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

        if(userId.intValue()==0){

            return JsonData.fail("未获取到相关数据，请刷新重试");
        }

        List<RepairOrderParam> list = repairOrderMapper.selectRepairOrderByuserQuery(userId);
        return list.size()==0?JsonData.fail("未查询到相关数据"):JsonData.success(list,"查询成功");
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
        return param.getRepairType().equals(5);
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
                    return 0;
            }
        }
    }
}
