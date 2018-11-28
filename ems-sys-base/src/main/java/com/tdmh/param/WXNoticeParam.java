package com.tdmh.param;

import com.tdmh.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author litairan on 2018/11/16.
 */
@Getter
@Setter
public class WXNoticeParam extends BaseEntity {

    /**
     * 微信公告ID
     */
    private Integer wxNoticeId;

    /**
     * 微信公告标题
     */
    @NotNull(message = "微信公告标题不能为空")
    private String wxNoticeTitle;

    /**
     * 微信公告类型
     */
    @NotNull(message = "微信公告类型不能为空")
    private Integer wxNoticeType;

    /**
     * 微信公告类型名称
     */
    private String wxNoticeTypeName;

    /**
     * 微信公告内容
     */
    @NotNull(message = "微信公告内容不能为空")
    private String wxNoticeContent;

    public WXNoticeParam() {
        super();
    }

    public WXNoticeParam(Integer wxNoticeId, String wxNoticeTitle, Integer wxNoticeType, String wxNoticeTypeName, String wxNoticeContent, Date createTime,
                         Integer createBy, Date updateTime, Integer updateBy, Boolean usable, String remarks) {
        super(createTime, createBy, updateTime, updateBy, usable, remarks);
        this.wxNoticeId = wxNoticeId;
        this.wxNoticeTitle = wxNoticeTitle;
        this.wxNoticeType = wxNoticeType;
        this.wxNoticeTypeName = wxNoticeTypeName;
        this.wxNoticeContent = wxNoticeContent;
    }
}
