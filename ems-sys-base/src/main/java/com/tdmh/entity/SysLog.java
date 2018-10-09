package com.tdmh.entity;

import java.util.Date;

public class SysLog {
    private Long logId;

    private String logTitle;

    private String logType;

    private String logOperator;

    private String logOperatorIp;

    private String logExceptionInfo;

    private Date createTime;

    private Integer createBy;

    private Boolean usable;

    private String remarks;

    public SysLog(Long logId, String logTitle, String logType, String logOperator, String logOperatorIp, String logExceptionInfo, Date createTime, Integer createBy, Boolean usable, String remarks) {
        this.logId = logId;
        this.logTitle = logTitle;
        this.logType = logType;
        this.logOperator = logOperator;
        this.logOperatorIp = logOperatorIp;
        this.logExceptionInfo = logExceptionInfo;
        this.createTime = createTime;
        this.createBy = createBy;
        this.usable = usable;
        this.remarks = remarks;
    }

    public SysLog() {
        super();
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
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

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Boolean getUsable() {
        return usable;
    }

    public void setUsable(Boolean usable) {
        this.usable = usable;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }
}