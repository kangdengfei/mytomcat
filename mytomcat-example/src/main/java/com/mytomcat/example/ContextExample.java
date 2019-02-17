package com.mytomcat.example;

import com.mytomcat.bean.DefaultBeanContext;
import com.mytomcat.mytomact.Student;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-16 17:00
 **/
public class ContextExample {
    public static void main(String[] args) {
        DefaultBeanContext defaultBeanContext = new DefaultBeanContext();
        defaultBeanContext.initBean();
        defaultBeanContext.injectAnnotation();
        Student student = (Student)defaultBeanContext.getBean("com.mytomcat.mytomact.Student");
        student.say();

    }
}



