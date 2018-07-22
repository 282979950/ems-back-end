package com.ems.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 员工表实体
 */
@Getter
@Setter
public class Employee extends BaseEntity {

    /**
     * 员工ID
     */
    private Integer empId;

    /**
     * 员工工号
     */
    private String empNumber;

    /**
     * 员工名称
     */
    private String empName;

    /**
     * 员工所属机构
     */
    private Integer empOrgId;

    /**
     * 员工所属区域
     */
    private Integer empDistrictId;

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

    public Employee(Integer empId, String empNumber, String empName, Integer empOrgId, Integer empDistrictId, String empLoginName, String empPassword, String
            empEmail, String empPhone, String empMobile, String empAddress, String empType, String empManagementDistId, String empLoginIp, Date empLoginDate,
                    Boolean empLoginFlag, Date createTime, Integer createBy, Date updateTime, Integer updateBy, Boolean useable, String remarks) {
        super(createTime, createBy, updateTime, updateBy, useable, remarks);
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
        this.empLoginIp = empLoginIp;
        this.empLoginDate = empLoginDate;
        this.empLoginFlag = empLoginFlag;
    }

    public Employee() {
        super();
    }
}