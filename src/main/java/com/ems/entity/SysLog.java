package com.ems.entity;

import java.util.Date;
/*
 * 日志实体
 */
public class SysLog {
    private String id;//普通id

    private String logId;//日志ID

    private String logTitle;//日志标题

    private String logType;//日志类型

    private String logOperator;//操作人员

    private String logOperatorIp;//操作人员IP地址

    private String logExceptionInfo;//异常信息

    private Date createTime;//创建时间

    private Long createBy;//创建者

    private Boolean useable;//是否可用

    private String remarks;//注释

    public SysLog(String id, String logId, String logTitle, String logType, String logOperator, String logOperatorIp, String logExceptionInfo, Date createTime, Long createBy, Boolean useable, String remarks) {
        this.id = id;
        this.logId = logId;
        this.logTitle = logTitle;
        this.logType = logType;
        this.logOperator = logOperator;
        this.logOperatorIp = logOperatorIp;
        this.logExceptionInfo = logExceptionInfo;
        this.createTime = createTime;
        this.createBy = createBy;
        this.useable = useable;
        this.remarks = remarks;
    }

    public SysLog() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId == null ? null : logId.trim();
    }

    public String getLogTitle() {
        return logTitle;
    }

    public void setLogTitle(String logTitle) {
        this.logTitle = logTitle == null ? null : logTitle.trim();
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType == null ? null : logType.trim();
    }

    public String getLogOperator() {
        return logOperator;
    }

    public void setLogOperator(String logOperator) {
        this.logOperator = logOperator == null ? null : logOperator.trim();
    }

    public String getLogOperatorIp() {
        return logOperatorIp;
    }

    public void setLogOperatorIp(String logOperatorIp) {
        this.logOperatorIp = logOperatorIp == null ? null : logOperatorIp.trim();
    }

    public String getLogExceptionInfo() {
        return logExceptionInfo;
    }

    public void setLogExceptionInfo(String logExceptionInfo) {
        this.logExceptionInfo = logExceptionInfo == null ? null : logExceptionInfo.trim();
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