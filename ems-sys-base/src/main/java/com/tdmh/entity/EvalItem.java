package com.tdmh.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Liuxia on 2018/11/14.
 */
@Setter
@Getter
public class EvalItem extends BaseEntity{

    private Integer evalItemId;

    /**
     * 评价项内容
     */
    private String evalItemContent;

}
