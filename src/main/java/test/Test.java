package test;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-16 14:47
 **/
public class Test {
    public static void main(String[] args) throws Exception {


        Class<?> aClass = Class.forName("test.Person");
        System.out.println(aClass);
        Person person = (Person)aClass.newInstance();
        System.out.println(person.name);

        System.out.println("=====");
        Method[] method = aClass.getDeclaredMethods();
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields){
            System.out.println(field.getName());
        }

        Field age = aClass.getDeclaredField("age");
        age.set(person,99999);
        System.out.println(person.age);
        System.out.println("======");
        Method say = aClass.getMethod("sayOk");
        System.out.println(say.getName());
        say.invoke(person);

        System.out.println("========");
        PropertyDescriptor[] descriptors = Introspector.getBeanInfo(aClass).getPropertyDescriptors();
        for(PropertyDescriptor descriptor : descriptors) {
            // 获取所有set方法
            Method setter = descriptor.getWriteMethod();

            Class<?> propertyType = descriptor.getPropertyType();

            System.out.println(descriptor.getPropertyType());
        }


    }
}



