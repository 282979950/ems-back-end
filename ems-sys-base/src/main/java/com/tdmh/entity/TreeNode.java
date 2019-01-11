package com.tdmh.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author litairan on 2019/1/10.
 */
public class TreeNode {
    @Getter
    @Setter
    private Integer id;
    @Getter
    @Setter
    private Integer value;
    @Getter
    @Setter
    private String title;
    @Getter
    @Setter
    private String key;

    @JsonProperty("pId")
    private Integer pId;

    public Integer getPId() {
        return pId;
    }

    public void setPId(Integer pId) {
        this.pId = pId;
    }
}
