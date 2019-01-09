package com.tdmh.authority;

import java.util.function.Function;

/**
 * @author litairan on 2019/1/8.
 */
public interface Filterable<T> {
    void doFilter(Function<T, T> filterFunc);
}
