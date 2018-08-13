package com.ems.param;

import com.ems.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 用户开户参数
 *
 * @author litairan on 2018/8/9.
 */
@Getter
@Setter
public class CreateAccountParam extends BaseEntity {

    /**
     * 用户编号
     */
    @NotNull(message = "用户编号不能为空")
    private Integer userId;

    /**
     * 用户名称
     */
    @NotNull(message = "用户名称不能为空")
    @Length(max = 20, message = "用户名称不能超过20字")
    private String userName;

    /**
     * 用户手机号码
     */
    @NotNull(message = "用户手机号码不能为空")
    @Length(max = 20, message = "用户手机号码不能超过20字")
    private String userPhone;

    /**
     * 用户身份证号
     */
    @NotNull(message = "用户身份证不能为空")
    @Length(max = 20, message = "用户身份证不能超过20字")
    private String userIdcard;

    /**
     * 用户房产证号
     */
    private String userDeed;

    /**
     * IC卡卡号
     */
    private Integer iccardId;

    /**
     * IC卡识别号
     */
    private String iccardIdentifier;

    /**
     * IC卡密码
     */
    private String iccardPassword;

    /**
     * 用户是否锁定
     */
    private Boolean userLocked;

    /**
     * 支付金额
     */
    private BigDecimal orderPayment;
}
