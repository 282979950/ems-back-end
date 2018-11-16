package com.tdmh.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Liuxia on 2018/11/15.
 */

@Setter
@Getter
public class Eval {
    /**
     * 动态评价ID
     */
    private Integer evalId;
    /**
     * 报修单号
     */
    private Integer applyRepairId;
    /**
     * 动态评价项ID
     */
    private Integer evalItemId;
    /**
     * 动态评价项内容
     */
    private Boolean evalContent;
    /**
     * 评价时间
     */
    private Date evalTime;

}
