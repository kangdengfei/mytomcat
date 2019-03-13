package com.mytomcat.filter;

public interface Filter<T,R> {
    void doFilter(T T,R r);
}
