package com.mytomcat.pojo;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-23 16:51
 **/
public class TestDemo {

    @Test
    public void test() throws Exception {
        Class<?> cls = Class.forName("com.mytomcat.pojo.Person");
        Method doWork = cls.getMethod("doWork",  new Class[]{int.class,String.class});
        Type[] genericExceptionTypes = doWork.getParameterTypes();
        System.out.println(genericExceptionTypes.length);
        Annotation[][] parameterAnnotations = doWork.getParameterAnnotations();
        System.out.println(parameterAnnotations.length);
        System.out.println();
    }

    @Test
    public void test2() throws Exception {
        Class<?> cls = Class.forName("com.mytomcat.pojo.Person");
        Person person = (Person)cls.newInstance();
        Method say = cls.getMethod("say");
        say.invoke(person);
        Method speak = cls.getMethod("speak",new Class[]{String.class});
        Object nihao = speak.invoke(person, "nihao");
        System.out.println(nihao);
        Method doWork = cls.getMethod("doWork",  new Class[]{int.class,String.class});
        doWork.getGenericExceptionTypes();
        doWork.invoke(person,2,"吃饭");
    }

    @Test
    public void test1(){
        Map map = new HashMap();
        if (map.containsKey("/")){
            System.out.println(true);
        }
    }


}



