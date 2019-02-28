package com.mytomcat.pojo;

import org.junit.Test;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-27 20:48
 **/
public class StringTest {

    @Test
    public void test(){
        String string = "/test?name=lisi&age=12";
        System.out.println(string.indexOf('?'));
        System.out.println(string.substring(0,5));

    }

}



