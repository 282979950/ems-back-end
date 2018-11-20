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
 * @author Administrator on 2018/11/9.
 */
@Setter
@Getter
public class ApplyRepairParam extends BaseEntity {

    /**
     * 报修单ID
     */
    private Integer applyRepairId;

    /**
     * 报修单号
     */
    private String applyRepairFlowNumber;

    /**
     * 户号
     */
    @NotNull(message = "户号不能为空")
    private Integer userId;

    /**
     *户名
     */
    private String userName;

    /**
     * 区域ID
     */

    private Integer distId;

    /**
     * 区域名(地址1)
     */
    private String distName;

    /**
     * 区域编码
     */
    private String distCode;

    /**
     * 用户地址（全地址）
     */
    private String userAddress;

    /**
     * 用户电话
     */
    private String userPhone;

    /**
     * 报修类型
     */
    private Integer applyRepairType;

    /**
     * 报修类型名称
     */
    private String applyRepairTypeName;

    /**
     * 故障说明
     */
    @NotNull(message = "故障说明不能为空")
    private String applyRepairFaultDesc;

    /**
     * 诉求内容
     */
    private String applyRepairAppealContent;

    /**
     * 报修时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date applyRepairTime;

    /**
     *
     */
    private Integer meterId;

    /**
     * 表号
     */
    private String meterCode;

    /**
     * 表向
     */
    private Boolean meterDirection;

    /**
     * 表向名称
     */
    private String meterDirectionName;

    /**
     * 表具型号ID
     */
    private Integer meterTypeId;

    /**
     * 表具类型
     */
    private String meterType;

    /**
     * 购气量
     */
    private BigDecimal currentOrderGasCount;

    /**
     * 报修状态
     */
    private Integer applyRepairStatus;

    /**
     * 报修状态名称
     */
    private String applyRepairStatusName;

    /**
     * 预约开始时间
     */
    @NotNull(message = "预约开始时间不能为空")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date startTime;

    /**
     * 预约截止时间
     */
    @NotNull(message = "预约结束时间不能为空")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date endTime;

    /**
     * 主叫号码
     */
    private String userTelPhone;

    /**
     *撤销小程序表单ID
     */
    private String formId;

    public ApplyRepairParam() {
        super();
    }

    public ApplyRepairParam(Integer applyRepairId, String applyRepairFlowNumber, Integer userId, String userName, Integer distId, String distName,
                            String distCode, String userAddress, String userPhone, Integer applyRepairType, String applyRepairTypeName,
                            String applyRepairFaultDesc, String applyRepairAppealContent, Date applyRepairTime, Integer meterId, String meterCode,
                            Boolean meterDirection, String meterDirectionName, Integer meterTypeId, String meterType, BigDecimal currentOrderGasCount,
                            Integer applyRepairStatus, String applyRepairStatusName, Date startTime, Date endTime, String userTelPhone, Date createTime,
                            Integer createBy, Date updateTime, Integer updateBy, Boolean usable, String remarks) {
        super(createTime, createBy, updateTime, updateBy, usable, remarks);
        this.applyRepairId = applyRepairId;
        this.applyRepairFlowNumber = applyRepairFlowNumber;
        this.userId = userId;
        this.userName = userName;
        this.distId = distId;
        this.distName = distName;
        this.distCode = distCode;
        this.userAddress = userAddress;
        this.userPhone = userPhone;
        this.applyRepairType = applyRepairType;
        this.applyRepairTypeName = applyRepairTypeName;
        this.applyRepairFaultDesc = applyRepairFaultDesc;
        this.applyRepairAppealContent = applyRepairAppealContent;
        this.applyRepairTime = applyRepairTime;
        this.meterId = meterId;
        this.meterCode = meterCode;
        this.meterDirection = meterDirection;
        this.meterDirectionName = meterDirectionName;
        this.meterTypeId = meterTypeId;
        this.meterType = meterType;
        this.currentOrderGasCount = currentOrderGasCount;
        this.applyRepairStatus = applyRepairStatus;
        this.applyRepairStatusName = applyRepairStatusName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userTelPhone = userTelPhone;
    }

    @Override
    public String toString() {
        return "applyRepairFlowNumber=" + applyRepairFlowNumber +
                "&applyRepairType=" + applyRepairTypeName +
                "&userId=" + userId +
                "&userName=" + userName +
                "&distName=" + distName +
                "&distCode=" + distCode +
                "&userAddress=" + userAddress +
                "&userPhone=" + userPhone +
                "&applyRepairFaultDesc=" + applyRepairFaultDesc +
                "&applyRepairAppealContent=" + applyRepairAppealContent +
                "&applyRepairTime=" + applyRepairTime +
                "&meterCode=" + meterCode +
                "&meterDirection=" + meterDirectionName +
                "&meterType=" + meterType +
                "&currentOrderGasCount=" + currentOrderGasCount +
                "&startTime=" + startTime +
                "&endTime=" + endTime +
                "&userTelPhone=" + userTelPhone +
                "&remarks=" + getRemarks();
    }
}
