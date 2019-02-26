package com.mytomcat.pojo;

import com.mytomcat.annotation.Bean;

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

    public void doWork(int day,String string){
        System.out.println("this is " +day+" "+string);
    }

}



