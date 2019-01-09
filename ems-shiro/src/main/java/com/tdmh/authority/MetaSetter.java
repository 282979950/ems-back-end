package com.tdmh.authority;

import java.util.List;

/**
 * @author litairan on 2019/1/8.
 */
public interface MetaSetter<T> {

    void setMeta(List<T> meta);

    List<T> getMeta();
}