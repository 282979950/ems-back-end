package com.tdmh.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tdmh.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author litairan on 2018/10/11.
 */
@Getter
@Setter
public class RepairOrderParam extends BaseEntity {

    /**
     * ID
     */
    private Integer id;

    /**
     * 维修单号
     */
    @NotNull(message = "维修单号")
    private String repairOrderId;

    /**
     * 用户Id
     */
    @NotNull(message = "户号不能为空")
    private Integer userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户手机
     */
    private String userPhone;

    /**
     * 用户Id
     */
    @NotNull(message = "用户地址不能为空")
    private String userAddress;

    /**
     * 维修类型
     */
    @NotNull(message = "维修类型不能为空")
    private Integer repairType;

    /**
     * 维修类型名称
     */
    private String repairTypeName;

    /**
     * 燃气设备类型
     */
    @NotNull(message = "燃气设备类型不能为空")
    private Integer gasEquipmentType;

    /**
     * 燃气设备名称
     */
    private String gasEquipmentTypeName;

    /**
     * 旧表Id
     */
    private Integer oldMeterId;

    /**
     * 旧表编号
     */
    private String oldMeterCode;

    /**
     * 旧表类型Id
     */
    private Integer oldMeterTypeId;

    /**
     * 旧表类型名称
     */
    private String oldMeterTypeName;

    /**
     * 旧表表向
     */
    private Boolean oldMeterDirection;

    /**
     * 旧表表向名称
     */
    private String oldMeterDirectionName;

    /**
     * 旧表止码
     */
    @NotNull(message = "旧表止码不能为空")
    private BigDecimal oldMeterStopCode;

    /**
     * 新表Id
     */
    private Integer newMeterId;

    /**
     * 新表编号
     */
    private String newMeterCode;

    /**
     * 新表类型Id
     */
    private Integer newMeterTypeId;

    /**
     * 新表类型名称
     */
    private String newMeterTypeName;

    /**
     * 新表表向
     */
    private Boolean newMeterDirection;

    /**
     * 新表表向名称
     */
    private String newMeterDirectionName;

    /**
     * 新表止码
     */
    private BigDecimal newMeterStopCode;

    /**
     * 维修故障类型
     */
    @NotNull(message = "维修故障类型不能为空")
    private Integer repairFaultType;

    /**
     * 维修故障类型
     */
    private String repairFaultTypeName;

    /**
     * 维修处理结果
     */
    @NotNull(message = "维修处理结果不能为空")
    private Integer repairResultType;

    /**
     * 维修处理结果名称
     */
    private String repairResultTypeName;

    /**
     * 维修员Id
     */
    @NotNull(message = "维修员工号为空或不存在")
    private Integer empId;

    /**
     * 维修员工号
     */
    private String empNumber;

    /**
     * 维修员姓名
     */
    private String empName;

    /**
     * 维修开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date repairStartTime;

    /**
     * 维修结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date repairEndTime;

    /**
     * 新安全卡编号
     */
    private String newSafetyCode;

    /**
     * 旧安全卡编号
     */
    private String oldSafetyCode;

    public RepairOrderParam() {
        super();
    }

    public RepairOrderParam(Integer id, String repairOrderId, Integer userId, String userName, String userPhone, String userAddress, Integer repairType,
                            String repairTypeName, Integer gasEquipmentType, String gasEquipmentTypeName, Integer oldMeterId, String oldMeterCode,
                            Integer oldMeterTypeId, String oldMeterTypeName, Boolean oldMeterDirection, String oldMeterDirectionName,
                            BigDecimal oldMeterStopCode, Integer newMeterId, String newMeterCode, Integer newMeterTypeId, String newMeterTypeName,
                            Boolean newMeterDirection, String newMeterDirectionName, BigDecimal newMeterStopCode, Integer repairFaultType,
                            String repairFaultTypeName, Integer repairResultType, String repairResultTypeName, Integer empId, String empNumber,
                            String empName, Date repairStartTime, Date repairEndTime, String newSafetyCode, String oldSafetyCode, Date createTime,
                            Integer createBy, Date updateTime, Integer updateBy, Boolean usable, String remarks) {
        super(createTime, createBy, updateTime, updateBy, usable, remarks);
        this.id = id;
        this.repairOrderId = repairOrderId;
        this.userId = userId;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userAddress = userAddress;
        this.repairType = repairType;
        this.repairTypeName = repairTypeName;
        this.gasEquipmentType = gasEquipmentType;
        this.gasEquipmentTypeName = gasEquipmentTypeName;
        this.oldMeterId = oldMeterId;
        this.oldMeterCode = oldMeterCode;
        this.oldMeterTypeId = oldMeterTypeId;
        this.oldMeterTypeName = oldMeterTypeName;
        this.oldMeterDirection = oldMeterDirection;
        this.oldMeterDirectionName = oldMeterDirectionName;
        this.oldMeterStopCode = oldMeterStopCode;
        this.newMeterId = newMeterId;
        this.newMeterCode = newMeterCode;
        this.newMeterTypeId = newMeterTypeId;
        this.newMeterTypeName = newMeterTypeName;
        this.newMeterDirection = newMeterDirection;
        this.newMeterDirectionName = newMeterDirectionName;
        this.newMeterStopCode = newMeterStopCode;
        this.repairFaultType = repairFaultType;
        this.repairFaultTypeName = repairFaultTypeName;
        this.repairResultType = repairResultType;
        this.repairResultTypeName = repairResultTypeName;
        this.empId = empId;
        this.empNumber = empNumber;
        this.empName = empName;
        this.repairStartTime = repairStartTime;
        this.repairEndTime = repairEndTime;
        this.newSafetyCode = newSafetyCode;
        this.oldSafetyCode = oldSafetyCode;
    }
}
