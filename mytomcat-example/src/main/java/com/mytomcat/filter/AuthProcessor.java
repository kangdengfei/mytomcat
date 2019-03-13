package com.mytomcat.filter;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-03-12 18:16
 **/
public class AuthProcessor extends  AbstractLinkedProcessor {



    @Override
    public void doProcess(Object content) {
        System.out.println("auth:" + content);
        throw new RuntimeException("请先登录");
    }


}



