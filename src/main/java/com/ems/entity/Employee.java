package com.ems.entity;

import java.util.Date;

public class Employee {
    private String id;

    private Long empId;

    private String empNumber;

    private String empName;

    private Long empOrgId;

    private Long empDistrictId;

    private String empLoginName;

    private String empPassword;

    private String empEmail;

    private String empPhone;

    private String empMobile;

    private String empAddress;

    private String empType;

    private String empManagementDistId;

    private String empRoleId;

    private String empLoginIp;

    private Date empLoginDate;

    private Boolean empLoginFlag;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;

    private Boolean useable;

    private String remarks;

    public Employee(String id, Long empId, String empNumber, String empName, Long empOrgId, Long empDistrictId, String empLoginName, String empPassword, String empEmail, String empPhone, String empMobile, String empAddress, String empType, String empManagementDistId, String empRoleId, String empLoginIp, Date empLoginDate, Boolean empLoginFlag, Date createTime, Long createBy, Date updateTime, Long updateBy, Boolean useable, String remarks) {
        this.id = id;
        this.empId = empId;
        this.empNumber = empNumber;
        this.empName = empName;
        this.empOrgId = empOrgId;
        this.empDistrictId = empDistrictId;
        this.empLoginName = empLoginName;
        this.empPassword = empPassword;
        this.empEmail = empEmail;
        this.empPhone = empPhone;
        this.empMobile = empMobile;
        this.empAddress = empAddress;
        this.empType = empType;
        this.empManagementDistId = empManagementDistId;
        this.empRoleId = empRoleId;
        this.empLoginIp = empLoginIp;
        this.empLoginDate = empLoginDate;
        this.empLoginFlag = empLoginFlag;
        this.createTime = createTime;
        this.createBy = createBy;
        this.updateTime = updateTime;
        this.updateBy = updateBy;
        this.useable = useable;
        this.remarks = remarks;
    }

    public Employee() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getEmpNumber() {
        return empNumber;
    }

    public void setEmpNumber(String empNumber) {
        this.empNumber = empNumber == null ? null : empNumber.trim();
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName == null ? null : empName.trim();
    }

    public Long getEmpOrgId() {
        return empOrgId;
    }

    public void setEmpOrgId(Long empOrgId) {
        this.empOrgId = empOrgId;
    }

    public Long getEmpDistrictId() {
        return empDistrictId;
    }

    public void setEmpDistrictId(Long empDistrictId) {
        this.empDistrictId = empDistrictId;
    }

    public String getEmpLoginName() {
        return empLoginName;
    }

    public void setEmpLoginName(String empLoginName) {
        this.empLoginName = empLoginName == null ? null : empLoginName.trim();
    }

    public String getEmpPassword() {
        return empPassword;
    }

    public void setEmpPassword(String empPassword) {
        this.empPassword = empPassword == null ? null : empPassword.trim();
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail == null ? null : empEmail.trim();
    }

    public String getEmpPhone() {
        return empPhone;
    }

    public void setEmpPhone(String empPhone) {
        this.empPhone = empPhone == null ? null : empPhone.trim();
    }

    public String getEmpMobile() {
        return empMobile;
    }

    public void setEmpMobile(String empMobile) {
        this.empMobile = empMobile == null ? null : empMobile.trim();
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress == null ? null : empAddress.trim();
    }

    public String getEmpType() {
        return empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType == null ? null : empType.trim();
    }

    public String getEmpManagementDistId() {
        return empManagementDistId;
    }

    public void setEmpManagementDistId(String empManagementDistId) {
        this.empManagementDistId = empManagementDistId == null ? null : empManagementDistId.trim();
    }

    public String getEmpRoleId() {
        return empRoleId;
    }

    public void setEmpRoleId(String empRoleId) {
        this.empRoleId = empRoleId == null ? null : empRoleId.trim();
    }

    public String getEmpLoginIp() {
        return empLoginIp;
    }

    public void setEmpLoginIp(String empLoginIp) {
        this.empLoginIp = empLoginIp == null ? null : empLoginIp.trim();
    }

    public Date getEmpLoginDate() {
        return empLoginDate;
    }

    public void setEmpLoginDate(Date empLoginDate) {
        this.empLoginDate = empLoginDate;
    }

    public Boolean getEmpLoginFlag() {
        return empLoginFlag;
    }

    public void setEmpLoginFlag(Boolean empLoginFlag) {
        this.empLoginFlag = empLoginFlag;
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