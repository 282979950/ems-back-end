package com.tdmh.util;

import com.tdmh.entity.GasPrice;
import java.math.BigDecimal;

/**
 * @author Administrator on 2018/8/29.
 */
public class CalculateUtil {
    public static BigDecimal gasToPayment(BigDecimal gas, GasPrice gasPrice) {
        BigDecimal sum = new BigDecimal(0);
        if ( (gas.compareTo(gasPrice.getGasRangeOne()) >= 0 && gasPrice.getGasRangeTwo() == null) || (gas.compareTo(gasPrice.getGasRangeOne()) >= 0 && gas.compareTo(gasPrice.getGasRangeTwo()) < 0)){
            sum = gas.multiply(gasPrice.getGasPriceOne());
        } else if ( (gas.compareTo(gasPrice.getGasRangeTwo()) >= 0 && gasPrice.getGasRangeThree() == null) || (gas.compareTo(gasPrice.getGasRangeTwo()) >= 0 && gas.compareTo(gasPrice.getGasRangeThree()) < 0) ){
            sum = sum.add(gasPrice.getGasPriceOne().multiply(gasPrice.getGasRangeTwo()));
            sum = sum.add(gas.subtract(gasPrice.getGasRangeTwo()).multiply(gasPrice.getGasPriceTwo()));
        } else if ( (gas.compareTo(gasPrice.getGasRangeThree()) >= 0 && gasPrice.getGasRangeFour() == null) || (gas.compareTo(gasPrice.getGasRangeThree()) >= 0 && gas.compareTo(gasPrice.getGasRangeFour()) < 0) ){
            sum = sum.add(gasPrice.getGasPriceOne().multiply(gasPrice.getGasRangeTwo()));
            sum = sum.add(gasPrice.getGasPriceTwo().multiply(gasPrice.getGasRangeThree().subtract(gasPrice.getGasRangeTwo())));
            sum = sum.add(gas.subtract(gasPrice.getGasRangeThree()).multiply(gasPrice.getGasPriceThree()));
        } else if (gas.compareTo(gasPrice.getGasRangeFour()) >= 0) {
            sum = sum.add(gasPrice.getGasPriceOne().multiply(gasPrice.getGasRangeTwo()));
            sum = sum.add(gasPrice.getGasPriceTwo().multiply(gasPrice.getGasRangeThree().subtract(gasPrice.getGasRangeTwo())));
            sum = sum.add(gasPrice.getGasPriceThree().multiply(gasPrice.getGasRangeFour().subtract(gasPrice.getGasRangeThree())));
            sum = sum.add(gas.subtract(gasPrice.getGasRangeFour()).multiply(gasPrice.getGasPriceFour()));
        }
        return sum;
    }
    //燃气公司需要退钱剩余部分通用类
    public static BigDecimal gasSurplusToPayment(BigDecimal gas, GasPrice gasPrice) {
        BigDecimal sum = new BigDecimal(0);
        if ( (gas.compareTo(gasPrice.getGasRangeOne()) >= 0 ) ){
            sum = gas.multiply(gasPrice.getGasPriceOne());
        }
        return sum;
    }
}
