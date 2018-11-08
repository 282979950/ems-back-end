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

    public static String showGasPrice(BigDecimal orderGas, BigDecimal hasUsedGasNum, GasPrice gasPrice) {
        StringBuilder sb = new StringBuilder();
        BigDecimal totalGas = hasUsedGasNum.add(orderGas);
        if ( (hasUsedGasNum.compareTo(gasPrice.getGasRangeOne()) >= 0 && gasPrice.getGasRangeTwo() == null) || (hasUsedGasNum.compareTo(gasPrice.getGasRangeOne()) >= 0 && hasUsedGasNum.compareTo(gasPrice.getGasRangeTwo()) < 0)){
            if ( (totalGas.compareTo(gasPrice.getGasRangeOne()) >= 0 && gasPrice.getGasRangeTwo() == null) || (totalGas.compareTo(gasPrice.getGasRangeOne()) >= 0 && totalGas.compareTo(gasPrice.getGasRangeTwo()) < 0)){
                sb.append(orderGas+"*"+gasPrice.getGasPriceOne());
            } else if ( (totalGas.compareTo(gasPrice.getGasRangeTwo()) >= 0 && gasPrice.getGasRangeThree() == null) || (totalGas.compareTo(gasPrice.getGasRangeTwo()) >= 0 && totalGas.compareTo(gasPrice.getGasRangeThree()) < 0) ){
                sb.append(gasPrice.getGasRangeTwo().subtract(hasUsedGasNum)+"*"+gasPrice.getGasPriceOne()+"+"+totalGas.subtract(gasPrice.getGasRangeTwo())+"*"+gasPrice.getGasPriceTwo());
            } else if ( (totalGas.compareTo(gasPrice.getGasRangeThree()) >= 0 && gasPrice.getGasRangeFour() == null) || (totalGas.compareTo(gasPrice.getGasRangeThree()) >= 0 && totalGas.compareTo(gasPrice.getGasRangeFour()) < 0) ){
                sb.append(gasPrice.getGasRangeTwo().subtract(hasUsedGasNum)+"*"+gasPrice.getGasPriceOne()+"+"+gasPrice.getGasRangeThree().subtract(gasPrice.getGasRangeTwo())+"*"+gasPrice.getGasPriceTwo()+"+"+totalGas.subtract(gasPrice.getGasRangeThree())+"*"+gasPrice.getGasPriceThree());
            } else if (totalGas.compareTo(gasPrice.getGasRangeFour()) >= 0) {
                sb.append(gasPrice.getGasRangeTwo().subtract(hasUsedGasNum)+"*"+gasPrice.getGasPriceOne()+"+"+gasPrice.getGasRangeThree().subtract(gasPrice.getGasRangeTwo())+"*"+gasPrice.getGasPriceTwo()+"+"+gasPrice.getGasRangeFour().subtract(gasPrice.getGasRangeThree())+"*"+gasPrice.getGasPriceThree()+"+"+totalGas.subtract(gasPrice.getGasRangeFour())+"*"+gasPrice.getGasPriceFour());
            }
        } else if ( (hasUsedGasNum.compareTo(gasPrice.getGasRangeTwo()) >= 0 && gasPrice.getGasRangeThree() == null) || (hasUsedGasNum.compareTo(gasPrice.getGasRangeTwo()) >= 0 && hasUsedGasNum.compareTo(gasPrice.getGasRangeThree()) < 0) ){
           if ( (totalGas.compareTo(gasPrice.getGasRangeTwo()) >= 0 && gasPrice.getGasRangeThree() == null) || (totalGas.compareTo(gasPrice.getGasRangeTwo()) >= 0 && totalGas.compareTo(gasPrice.getGasRangeThree()) < 0) ){
               sb.append(orderGas+"*"+gasPrice.getGasPriceTwo());
           } else if ( (totalGas.compareTo(gasPrice.getGasRangeThree()) >= 0 && gasPrice.getGasRangeFour() == null) || (totalGas.compareTo(gasPrice.getGasRangeThree()) >= 0 && totalGas.compareTo(gasPrice.getGasRangeFour()) < 0) ){
               sb.append(gasPrice.getGasRangeThree().subtract(hasUsedGasNum)+"*"+gasPrice.getGasPriceTwo()+"+"+totalGas.subtract(gasPrice.getGasRangeThree())+"*"+gasPrice.getGasPriceThree());
           } else if (totalGas.compareTo(gasPrice.getGasRangeFour()) >= 0) {
               sb.append(gasPrice.getGasRangeThree().subtract(hasUsedGasNum)+"*"+gasPrice.getGasPriceTwo()+"+"+gasPrice.getGasRangeFour().subtract(gasPrice.getGasRangeThree())+"*"+gasPrice.getGasPriceThree()+"+"+totalGas.subtract(gasPrice.getGasRangeFour())+"*"+gasPrice.getGasPriceFour());
           }
        } else if ( (hasUsedGasNum.compareTo(gasPrice.getGasRangeThree()) >= 0 && gasPrice.getGasRangeFour() == null) || (hasUsedGasNum.compareTo(gasPrice.getGasRangeThree()) >= 0 && hasUsedGasNum.compareTo(gasPrice.getGasRangeFour()) < 0) ){
           if ( (totalGas.compareTo(gasPrice.getGasRangeThree()) >= 0 && gasPrice.getGasRangeFour() == null) || (totalGas.compareTo(gasPrice.getGasRangeThree()) >= 0 && totalGas.compareTo(gasPrice.getGasRangeFour()) < 0) ){
               sb.append(orderGas+"*"+gasPrice.getGasPriceThree());
            } else if (totalGas.compareTo(gasPrice.getGasRangeFour()) >= 0) {
               sb.append(gasPrice.getGasRangeFour().subtract(hasUsedGasNum)+"*"+gasPrice.getGasPriceThree()+"+"+totalGas.subtract(gasPrice.getGasRangeFour())+"*"+gasPrice.getGasPriceFour());
            }
        } else if (hasUsedGasNum.compareTo(gasPrice.getGasRangeFour()) >= 0) {
            if (totalGas.compareTo(gasPrice.getGasRangeFour()) >= 0) {
                sb.append(orderGas+"*"+gasPrice.getGasPriceFour());
            }
        }
        return sb.toString();
    }
}
