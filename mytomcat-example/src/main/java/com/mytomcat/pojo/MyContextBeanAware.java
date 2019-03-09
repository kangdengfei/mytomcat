package com.mytomcat.pojo;

import cn.hutool.core.util.StrUtil;
import com.mytomcat.aware.BeanContextAware;
import com.mytomcat.bean.BeanContext;
import com.mytomcat.bean.imp.DefaultBeanContext;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-17 23:31
 **/
public class MyContextBeanAware implements BeanContextAware {
    public static BeanContext context;
    @Override
    public void setBeanContext(BeanContext beanContext) {
        this.context = beanContext;
    }

    public static void testBeanAware(){
        Student bean = (Student) context.getBean("com.mytomcat.pojo.Student");
        bean.say();
    }
}



