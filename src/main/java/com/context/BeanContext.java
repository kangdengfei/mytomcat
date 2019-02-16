package com.context;

public interface BeanContext {
    /**
     * 获得Bean
     * @param name Bean的名称
     * @return Bean
     */
    Object getBean(String name);
}
