package com.tdmh.param;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author Administrator on 2018/10/12.
 */

@Getter
@Setter
public class WriteCardParam {
    private Integer iccardId;
    private String iccardPassword;
    private BigDecimal orderGas;
    private Integer serviceTimes;
    private String flowNumber;
    private Integer orderId;
    //额外参数，方便打印发票信息
    private String rmbBig;
    private String name;
}
