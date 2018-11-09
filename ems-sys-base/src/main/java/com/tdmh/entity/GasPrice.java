package com.tdmh.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Administrator on 2018/10/11.
 */
@Setter
@Getter
public class GasPrice extends BaseEntity{
    private Integer gasPriceId;
    private Integer userType;
    private Integer userGasType;
    private BigDecimal gasRangeOne;
    private BigDecimal gasPriceOne;
    private BigDecimal gasRangeTwo;
    private BigDecimal gasPriceTwo;
    private BigDecimal gasRangeThree;
    private BigDecimal gasPriceThree;
    private BigDecimal gasRangeFour;
    private BigDecimal gasPriceFour;

    public GasPrice(Integer gasPriceId, Integer userType, Integer userGasType, BigDecimal gasRangeOne, BigDecimal gasPriceOne, BigDecimal gasRangeTwo, BigDecimal gasPriceTwo, BigDecimal gasRangeThree, BigDecimal gasPriceThree, BigDecimal gasRangeFour, BigDecimal gasPriceFour, Date createTime, Integer createBy, Date updateTime, Integer updateBy, Boolean usable,String remarks) {
        super(createTime, createBy, updateTime, updateBy, usable, remarks);
        this.gasPriceId = gasPriceId;
        this.userType = userType;
        this.userGasType = userGasType;
        this.gasRangeOne = gasRangeOne;
        this.gasPriceOne = gasPriceOne;
        this.gasRangeTwo = gasRangeTwo;
        this.gasPriceTwo = gasPriceTwo;
        this.gasRangeThree = gasRangeThree;
        this.gasPriceThree = gasPriceThree;
        this.gasRangeFour = gasRangeFour;
        this.gasPriceFour = gasPriceFour;
    }
}
