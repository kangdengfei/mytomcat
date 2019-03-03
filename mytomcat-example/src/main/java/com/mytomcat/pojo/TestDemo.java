package com.mytomcat.pojo;

import com.mytomcat.annotation.Autowired;
import com.mytomcat.annotation.Param;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
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

        //获取参数类型
        Type[] genericExceptionTypes = doWork.getParameterTypes();
        Class<?>[] clss = doWork.getParameterTypes();
        //获取参数
        Parameter[] parameters = doWork.getParameters();
        Annotation[][] parameterAnnotations = doWork.getParameterAnnotations();


        String name = parameters[0].getName();
        Class<?> type = parameters[0].getType();
        boolean annotationPresent = parameters[0].isAnnotationPresent(Param.class);
        boolean annotationPresent2 = parameters[0].isAnnotationPresent(Autowired.class);

        System.out.println(annotationPresent);

        System.out.println(genericExceptionTypes.length);
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



