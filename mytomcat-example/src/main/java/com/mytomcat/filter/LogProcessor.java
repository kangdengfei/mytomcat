package com.mytomcat.filter;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-03-12 18:26
 **/
public class LogProcessor extends AbstractLinkedProcessor {


    @Override
    public void doProcess(Object content) {
        System.out.println("log:" + content);
    }
}



