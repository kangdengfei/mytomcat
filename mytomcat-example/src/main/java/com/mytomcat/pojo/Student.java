package com.mytomcat.pojo;

import com.alibaba.fastjson.JSON;
import com.mytomcat.annotation.Autowired;
import com.mytomcat.annotation.Bean;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-16 17:09
 **/
@Bean
public class Student {
    private int age;
    private String  name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Student() {

   }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public Student(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public void say(){
        System.out.println("hahh");

    }
}



