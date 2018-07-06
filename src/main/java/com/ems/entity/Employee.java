package com.ems.entity;

import java.util.Date;

/**
 * 员工表实体
 */
public class Employee extends BaseEntity {

    /**
     * 员工ID
     */
    private Long empId;

    /**
     * 员工编号
     */
    private String empNumber;

    /**
     * 员工名称
     */
    private String empName;

    /**
     * 员工所属机构
     */
    private Long empOrgId;

    /**
     * 员工所属区域
     */
    private Long empDistrictId;

    /**
     * 登录名
     */
    private String empLoginName;

    /**
     * 密码
     */
    private String empPassword;

    /**
     * 员工邮箱
     */
    private String empEmail;

    /**
     * 员工电话
     */
    private String empPhone;

    /**
     * 员工手机号
     */
    private String empMobile;

    /**
     * 员工住址
     */
    private String empAddress;

    /**
     * 员工类型
     */
    private String empType;

    /**
     * 员工负责片区
     */
    private String empManagementDistId;

    /**
     * 员工角色
     */
    private String empRoleId;

    /**
     * 员工登录IP地址
     */
    private String empLoginIp;

    /**
     * 员工登录日期
     */
    private Date empLoginDate;

    /**
     * 员工登录标记
     */
    private Boolean empLoginFlag;

    public Employee(String id, Long empId, String empNumber, String empName, Long empOrgId, Long empDistrictId, String empLoginName, String empPassword, String empEmail, String empPhone, String empMobile, String empAddress, String empType, String empManagementDistId, String empRoleId, String empLoginIp, Date empLoginDate, Boolean empLoginFlag, Date createTime, Long createBy, Date updateTime, Long updateBy, Boolean useable, String remarks) {
        super(id, createTime, createBy, updateTime, updateBy, useable, remarks);
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
}