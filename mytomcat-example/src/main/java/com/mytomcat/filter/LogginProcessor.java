package com.mytomcat.filter;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-03-12 18:24
 **/
public class LogginProcessor extends AbstractLinkedProcessor {
    @Override
    public void doProcess(Object content) {
        System.out.println("loggin" + content);
    }
}



