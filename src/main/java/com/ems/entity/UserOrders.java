package com.ems.entity;

import java.math.BigDecimal;
import java.util.Date;

public class UserOrders {
    private String id;//机构ID

    private Long orderId;//订单ID

    private Long userId;//用户ID

    private Long employeeId;//员工ID

    private BigDecimal orderPayment;//充值金额

    private Integer orderStatus;//订单状态

    private Date orderCreateTime;//订单创建时间

    private Date orderCloseTime;//订单关闭时间

    private Date createTime;//创建时间

    private Long createBy;//创建者

    private Date updateTime;//更新时间

    private Long updateBy;//更新者

    private Boolean useable;//是否可用

    private String remarks;//备注

    public UserOrders(String id, Long orderId, Long userId, Long employeeId, BigDecimal orderPayment, Integer orderStatus, Date orderCreateTime, Date orderCloseTime, Date createTime, Long createBy, Date updateTime, Long updateBy, Boolean useable, String remarks) {
        this.id = id;
        this.orderId = orderId;
        this.userId = userId;
        this.employeeId = employeeId;
        this.orderPayment = orderPayment;
        this.orderStatus = orderStatus;
        this.orderCreateTime = orderCreateTime;
        this.orderCloseTime = orderCloseTime;
        this.createTime = createTime;
        this.createBy = createBy;
        this.updateTime = updateTime;
        this.updateBy = updateBy;
        this.useable = useable;
        this.remarks = remarks;
    }

    public UserOrders() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public BigDecimal getOrderPayment() {
        return orderPayment;
    }

    public void setOrderPayment(BigDecimal orderPayment) {
        this.orderPayment = orderPayment;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public Date getOrderCloseTime() {
        return orderCloseTime;
    }

    public void setOrderCloseTime(Date orderCloseTime) {
        this.orderCloseTime = orderCloseTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Boolean getUseable() {
        return useable;
    }

    public void setUseable(Boolean useable) {
        this.useable = useable;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }
}