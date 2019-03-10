package com.mytomcat.pojo;

import com.mytomcat.annotation.Autowired;
import com.mytomcat.annotation.Bean;
import com.mytomcat.annotation.Param;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-16 14:44
 **/
@Bean
public class Person {

    public String name = "Tom";

    public int age = 18;

    public void say(){
        System.out.println("I am ok");
    }

    public String speak(String string){
        System.out.println(string);
        return "大家好";
    }

    public Student getStudent(MyRquest myRquest){
        return new Student(myRquest.getAge(),myRquest.getName());
    }

    public void doWork(@Param @Autowired  int day, String string){
        System.out.println("this is " +day+" "+string);
    }

}



