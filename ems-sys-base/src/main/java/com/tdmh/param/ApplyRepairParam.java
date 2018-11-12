package com.tdmh.param;

import lombok.Setter;
import lombok.Getter;

import java.util.Date;

/**
 * @author Administrator on 2018/11/9.
 */
@Setter
@Getter
public class ApplyRepairParam {

    /**
     * 报修单号
     */
    private String applyRepairFlowNumber;

    /**
     * 报修类型
     */
    private String applyRepairType;

    /**
     * 报修状态
     */
    private Integer applyRepairStatus;

    /**
     * 户号
     */
    private String userId;

    /**
     *户名
     */
    private String userName;

    /**
     * 用户地址（全地址）
     */
    private String userAddress;

    /**
     * 区域名(地址1)
     */
    private String distName;

    /**
     *联系电话
     */
    private String userPhone;

    /**
     * 主叫号码
     */
    private String telPhone;

    /**
     * 故障说明
     */
    private String applyRepairFaultDesc;

    /**
     * 表号
     */
    private String meterCode;

    /**
     * 表向
     */
    private String meterDirection;

    /**
     * 表具类型
     */
    private String meterTypeName;

    /**
     * 诉求内容
     */
    private String applyRepairAppealContent;

    /**
     * 报修时间
     */
    private Date applyRepairTime;

    /**
     * 购气量
     */
    private String currentOrderGasCount;

    /**
     * 预约开始时间
     */
    private Date startTime;

    /**
     * 预约截止时间
     */
    private Date endTime;
}
