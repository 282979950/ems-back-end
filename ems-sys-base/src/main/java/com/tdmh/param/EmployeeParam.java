package com.tdmh.param;

import com.tdmh.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author litairan on 2018/8/29.
 */
@Getter
@Setter
public class EmployeeParam extends BaseEntity {

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
     * 员工所属机构
     */
    private String orgName;

    /**
     * 员工所属区域
     */
    private Integer empDistrictId;

    /**
     * 员工所属区域
     */
    private String distName;

    /**
     * 登录名
     */
    @NotNull
    @Length(max = 20, message = "员工登录名不能超过20个字")
    private String empLoginName;

    /**
     * 密码
     */
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
     * 员工所属角色
     */
    @NotNull
    private Integer roleId;

//    /**
//     * 员工角色名称
//     */
//    private String roleName;

    /**
     * 员工类型
     */
    @NotNull
    private Integer empType;

    /**
     * 员工类型名称
     */
    private String empTypeName;

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

    /**
     * 员工登录标记名称
     */
    private String empLoginFlagName;

    public EmployeeParam(Integer empId, String empNumber, String empName, Integer empOrgId, String orgName, Integer empDistrictId, String distName, String
            empLoginName, String empPassword, String empEmail, String empPhone, String empMobile, String empAddress, Integer roleId , Integer empType, String empTypeName,
                         String empManagementDistId, String empLoginIp, Date empLoginDate, Boolean empLoginFlag, String empLoginFlagName, Date createTime,
                         Integer createBy, Date updateTime, Integer updateBy, Boolean usable, String remarks) {
        super(createTime, createBy, updateTime, updateBy, usable, remarks);
        this.empId = empId;
        this.empNumber = empNumber;
        this.empName = empName;
        this.empOrgId = empOrgId;
        this.orgName = orgName;
        this.empDistrictId = empDistrictId;
        this.distName = distName;
        this.empLoginName = empLoginName;
        this.empPassword = empPassword;
        this.empEmail = empEmail;
        this.empPhone = empPhone;
        this.empMobile = empMobile;
        this.empAddress = empAddress;
        this.roleId = roleId;
        this.empType = empType;
        this.empTypeName = empTypeName;
        this.empManagementDistId = empManagementDistId;
        this.empLoginIp = empLoginIp;
        this.empLoginDate = empLoginDate;
        this.empLoginFlag = empLoginFlag;
        this.empLoginFlagName = empLoginFlagName;
    }

    public EmployeeParam() {
        super();
    }
}
