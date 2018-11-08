package com.tdmh.param;

import com.tdmh.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author Administrator on 2018/10/11.
 */
@Setter
@Getter
public class GasPriceParam extends BaseEntity{
    private Integer gasPriceId;
    private String userType;
    private String userTypeName;
    private String userGasType;
    private String userGasTypeName;
    private BigDecimal gasRangeOne;
    private BigDecimal gasPriceOne;
    private BigDecimal gasRangeTwo;
    private BigDecimal gasPriceTwo;
    private BigDecimal gasRangeThree;
    private BigDecimal gasPriceThree;
    private BigDecimal gasRangeFour;
    private BigDecimal gasPriceFour;

    public GasPriceParam(Integer gasPriceId, String userType,String userTypeName, String userGasType,String userGasTypeName, BigDecimal gasRangeOne, BigDecimal gasPriceOne, BigDecimal gasRangeTwo, BigDecimal gasPriceTwo, BigDecimal gasRangeThree, BigDecimal gasPriceThree, BigDecimal gasRangeFour, BigDecimal gasPriceFour) {
        this.gasPriceId = gasPriceId;
        this.userType = userType;
        this.userTypeName = userTypeName;
        this.userGasType = userGasType;
        this.userGasTypeName = userGasTypeName;
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
