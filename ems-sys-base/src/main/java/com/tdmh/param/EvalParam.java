package com.tdmh.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tdmh.entity.Eval;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author Liuxia on 2018/11/15.
 */
@Setter
@Getter
public class EvalParam {
    /**
     * 报修单ID
     */
    private Integer applyRepairId;
    /**
     * 报修单号
     */
    private String applyRepairFlowNumber;
    /**
     * 户号
     */
    private Integer userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 评价项
     */
    private List<Eval> evalList;
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
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date evalTime;
}
