package com.tdmh.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Liuxia on 2018/11/15.
 */
@Setter
@Getter
public class FixedEval {
    /**
     * 固定评价项ID
     */
    private Integer fixedEvalId;
    /**
     * 报修单号
     */
    private Integer applyRepairId;
    /**
     * 评价满意度
     */
    private String fixedEvalSelect;
    /**
     * 评价内容
     */
    private String fixedEvalContent;
    /**
     * 评价时间
     */
    private Date fixedEvalTime;
}
