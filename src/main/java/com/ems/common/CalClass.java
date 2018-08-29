package com.ems.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author Administrator on 2018/8/29.
 */
@AllArgsConstructor
@Getter
@Setter
public class CalClass{
    private BigDecimal start;
    private BigDecimal end;
    private BigDecimal money;
}
