package com.mytomac;

import com.annotation.Bean;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-16 17:09
 **/
@Bean
public class Student {
    public Person person;

    public void setPerson(Person person){
        this.person = person;
    }
}



