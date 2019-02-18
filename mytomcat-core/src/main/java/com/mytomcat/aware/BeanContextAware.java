package com.mytomcat.aware;

import com.mytomcat.bean.BeanContext;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-17 22:13
 **/
public interface BeanContextAware extends Aware {
    /**
     * 设置beanContext
     * @param beanContext
     */
     void setBeanContext(BeanContext beanContext);
}



