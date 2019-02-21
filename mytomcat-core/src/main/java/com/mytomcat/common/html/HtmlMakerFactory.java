package com.mytomcat.common.html;

import com.mytomcat.common.enums.HtmlMakerEnum;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-21 21:51
 **/
public class HtmlMakerFactory {


    private volatile static HtmlMakerFactory factory;

    private Map<HtmlMakerEnum,HtmlMaker> htmlMakerMap;

//    private Lock lock;

    private HtmlMakerFactory(){
        htmlMakerMap = new ConcurrentHashMap<>();
    }

    public static HtmlMakerFactory build(){
        if(factory == null){
            synchronized (HtmlMakerFactory.class){
                if(factory == null){
                    factory = new HtmlMakerFactory();
                }
            }
        }
        return factory;
    }

    private
}



