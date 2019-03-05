package com.mytomcat.bean;

public interface BeanContext {
    /**
     * 获得Bean
     * @param name Bean的名称
     * @return Bean
     */
    Object getBean(String name);

    /**
     * 获得bean
     * @param name bean的名称
     * @param clazz bean的类型
     * @param <T> 泛型
     * @return bean
     */
    <T> T getBean(String name,Class<T> clazz);
}
