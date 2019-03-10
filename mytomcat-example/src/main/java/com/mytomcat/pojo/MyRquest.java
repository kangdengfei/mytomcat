package com.mytomcat.pojo;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-03-10 15:59
 **/
public class MyRquest {
    String name;
    Integer age;

    public MyRquest(){

    }
    public MyRquest(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "MyRquest{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}



