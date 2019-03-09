package com.mytomcat.example;

import com.mytomcat.bean.BeanContext;
import com.mytomcat.bean.imp.DefaultBeanContext;
import com.mytomcat.pojo.MyContextBeanAware;
import com.mytomcat.pojo.Person;
import com.mytomcat.pojo.Student;
import org.junit.Test;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-16 17:00
 **/
public class ContextExample {
    public static void main(String[] args) {
        DefaultBeanContext defaultBeanContext = (DefaultBeanContext) DefaultBeanContext.getInstance();
        Person person = new Person();
        defaultBeanContext.initBean();
        defaultBeanContext.injectAnnotation();
        Student student1 = (Student) defaultBeanContext.getBean("com.mytomcat.pojo.Student");
        student1.say();
        defaultBeanContext.processBeanContextAware();
        Student student = (Student) MyContextBeanAware.context.getBean("com.mytomcat.pojo.Student");
        student.say();
        MyContextBeanAware.testBeanAware();
    }


    @Test
    public void test(){
        System.out.println("nihao");

    }

}



