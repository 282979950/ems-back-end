package com.tdmh.param;

import com.tdmh.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author litairan on 2018/10/30.
 */
@Getter
@Setter
public class BindNewCardParam extends BaseEntity {

    private Integer userCardId;
    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * IC卡卡号
     */
    private Integer cardId;

    /**
     * IC卡识别号
     */
    private String oldCardIdentifier;

    /**
     * IC卡密码
     */
    private String cardPassword;

    /**
     * 初始化卡标记
     */
    private Boolean cardInitialization;

    /**
     * 补卡收费
     */
    private BigDecimal cardCost;

    /**
     * 新IC卡识别号
     */
    @NotNull(message = "新IC卡识别号不能为空")
    private String newCardIdentifier;

    public BindNewCardParam() {
        super();
    }

    public BindNewCardParam(Integer userCardId, Integer userId, Integer cardId, String oldCardIdentifier, String cardPassword, Boolean cardInitialization,
                            BigDecimal cardCost, String newCardIdentifier, Date createTime, Integer createBy, Date updateTime, Integer updateBy,
                            Boolean usable) {
        super(createTime, createBy, updateTime, updateBy, usable, null);
        this.userCardId = userCardId;
        this.userId = userId;
        this.cardId = cardId;
        this.oldCardIdentifier = oldCardIdentifier;
        this.cardPassword = cardPassword;
        this.cardInitialization = cardInitialization;
        this.cardCost = cardCost;
        this.newCardIdentifier = newCardIdentifier;
    }
}
