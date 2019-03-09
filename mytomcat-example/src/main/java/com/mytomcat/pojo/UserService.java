package com.mytomcat.pojo;

import com.mytomcat.annotation.Bean;
import com.mytomcat.annotation.Controller;
import com.mytomcat.annotation.RequestMapping;
import org.junit.Before;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-18 22:05
 **/
@Bean
public class UserService {

    public Student getStudent(Integer idex){
        return new Student(idex,"李晓明");
    }
}



