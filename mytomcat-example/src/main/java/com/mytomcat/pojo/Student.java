package com.mytomcat.pojo;

import com.mytomcat.annotation.Autowired;
import com.mytomcat.annotation.Bean;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-16 17:09
 **/

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

    public Student(int age, String name) {
        this.age = age;
        this.name = name;
    }
}



