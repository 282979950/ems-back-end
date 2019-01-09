package com.tdmh.authority;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.function.Function;

/**
 * @author litairan on 2019/1/8.
 */
@Getter
@Setter
public class FilterableResult<T> implements Filterable<T>, MetaSetter<T> {

    protected List<T> rows;

    private List<T> meta;

    @Override
    public void doFilter(Function<T, T> filterFunc) {

    }

    @Override
    public void setMeta(List<T> meta) {

    }

    @Override
    public List<T> getMeta() {
        return null;
    }
}
