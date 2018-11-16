package com.tdmh.param;

import com.tdmh.entity.Eval;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author Liuxia on 2018/11/15.
 */
@Getter
@Setter
public class WxEvalParam {
    /**
     * 报修单ID
     */
    @NotNull(message = "报修单Id不能为空")
    private Integer applyRepairId;
    /**
     * 评价项
     */
    private List<Eval> evalList;
    /**
     * 报修满意度
     */
    private String fixedEvalSelect;
    /**
     * 报修内容
     */
    private String fixedEvalContent;
    /**
     * 评价时间
     */
    private Date fixedEvalTime;
}
