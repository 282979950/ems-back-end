package com.tdmh.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author litairan on 2018/10/11.
 */
@Getter
@Setter
public class RepairOrder extends BaseEntity {

    /**
     * ID
     */
    private Integer id;

    /**
     * 维修单号
     */
    private String repairOrderId;

    /**
     * 用户Id
     */
    private Integer userId;

    /**
     * 维修类型
     */
    private Integer repairType;

    /**
     * 燃气设备类型
     */
    private Integer gasEquipmentType;

    /**
     * 旧表编号
     */
    private Integer oldMeterId;

    /**
     * 新表编号
     */
    private Integer newMeterId;

    /**
     * 维修故障类型
     */
    private Integer repairFaultType;

    /**
     * 维修处理结果
     */
    private Integer repairResultType;

    /**
     * 员工Id
     */
    private Integer empId;

    /**
     * 维修开始时间
     */
    private Date repairStartTime;

    /**
     * 维修结束时间
     */
    private Date repairEndTime;

    /**
     * 新安全卡编号
     */
    private String newSafetyCode;

    /**
     * 旧安全卡编号
     */
    private String oldSafetyCode;
}
