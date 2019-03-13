package com.mytomcat.filter;

public interface Filter<T,R,S > {
    void doFilter(T t,R r,S s);
}
