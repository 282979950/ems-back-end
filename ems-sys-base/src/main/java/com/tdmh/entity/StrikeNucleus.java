package com.tdmh.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author qh on 2018/10/21冲账记录表实体
 */
@Getter
@Setter

public class StrikeNucleus extends BaseEntity {
    /**
     * id与业务无关
     */
    private String id;

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 审核状态
     */
    private Integer nucleusStatus;

    /**
     * 审核意见(审核描述)
     */
    private String nucleusOpinion;

    /**
     * 充值气量单位(方)
     */
    private BigDecimal nucleusGas;

    /**
     * 充值金额
     */
    private BigDecimal nucleusPayment;

    /**
     * 发起人名称
     */
    private String nucleusLaunchingPerson;

    /**
     * 充值时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date rechargeTime;

    public StrikeNucleus( String id, Integer orderId, String userName, Integer nucleusStatus, String nucleusOpinion, BigDecimal nucleusGas, BigDecimal nucleusPayment, String nucleusLaunchingPerson, Date rechargeTime,Date createTime, Integer createBy, Date updateTime, Integer updateBy, Boolean usable, @Length(max = 255, message = "备注长度不能超过255个字") String remarks) {
        super(createTime, createBy, updateTime, updateBy, usable, remarks);
        this.id = id;
        this.orderId = orderId;
        this.userName = userName;
        this.nucleusStatus = nucleusStatus;
        this.nucleusOpinion = nucleusOpinion;
        this.nucleusGas = nucleusGas;
        this.nucleusPayment = nucleusPayment;
        this.nucleusLaunchingPerson = nucleusLaunchingPerson;
        this.rechargeTime = rechargeTime;
    }

    public StrikeNucleus() {
        super();
    }
}
