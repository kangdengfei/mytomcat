package com.mytomcat.pojo;

import com.mytomcat.annotation.Bean;
import com.mytomcat.annotation.Controller;
import com.mytomcat.annotation.RequestMapping;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-18 22:05
 **/
@Controller(path = "/test")
public class UserService {
    @RequestMapping(path = "/say")
    public void say(){

    }
}



