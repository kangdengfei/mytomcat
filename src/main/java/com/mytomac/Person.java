package com.mytomac;

import com.annotation.Bean;

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


}



