package com.mytomcat.pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-23 16:51
 **/
public class Test {

    @org.junit.Test
    public void test1(){
        Map map = new HashMap();
        if (map.containsKey("/")){
            System.out.println(true);
        }
    }
}



