package com.mytomcat.pojo;

import com.mytomcat.annotation.Autowired;
import com.mytomcat.annotation.Bean;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-16 17:09
 **/

public class Student {
    @Autowired
    public Person person;

   public Student() {

   }

   public void say(){
       person.say();
       System.out.println("student say: "+person.name);
   }
}



