package com.ems.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
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
    @NotNull
    @Length(max = 50, message = "员工工号不能超过50个字")
    private String empNumber;

    /**
     * 员工名称
     */
    @NotNull
    @Length(max = 50, message = "员工名字不能超过50个字")
    private String empName;

    /**
     * 员工所属机构
     */
    @NotNull
    private Integer empOrgId;

    /**
     * 员工所属区域
     */
    private Integer empDistrictId;

    /**
     * 登录名
     */
    @NotNull
    @Length(max = 20, message = "员工登录名不能超过20个字")
    private String empLoginName;

    /**
     * 密码
     */
    @NotNull
    @Length(min = 4, max = 12, message = "员工登录密码需要4-12位")
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
    @NotNull
    private String empType;

    /**
     * 员工负责片区
     */
    @NotNull
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
                    Boolean empLoginFlag, Date createTime, Integer createBy, Date updateTime, Integer updateBy, Boolean usable, String remarks) {
        super(createTime, createBy, updateTime, updateBy, usable, remarks);
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