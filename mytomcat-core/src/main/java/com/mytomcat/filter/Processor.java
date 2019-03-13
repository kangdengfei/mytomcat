package com.mytomcat.filter;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-03-12 17:16
 **/
public interface Processor <T> {
    void process(T content);
}



