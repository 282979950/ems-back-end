package com.tdmh.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class SysDictionary extends BaseEntity{
    private Integer dictId;

    private String dictKey;

    private String dictValue;

    private String dictCategory;

    private Integer dictSort;

    private List<Integer> ids;

    public SysDictionary(Integer dictId, String dictKey, String dictValue, String dictCategory, Integer dictSort, Date createTime, Integer createBy, Date
            updateTime, Integer updateBy, Boolean usable, String remarks) {
        super(createTime, createBy, updateTime, updateBy, usable, remarks);
        this.dictId = dictId;
        this.dictKey = dictKey;
        this.dictValue = dictValue;
        this.dictCategory = dictCategory;
        this.dictSort = dictSort;
    }

    public SysDictionary() {
        super();
    }
}