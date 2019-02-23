package com.mytomcat.common.html;

import com.mytomcat.common.enums.HtmlMakerEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.misc.ReflectUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    Logger logger = LoggerFactory.getLogger(HtmlMakerFactory.class);


    public static HtmlMakerFactory instance(){
        if(factory == null){
            synchronized (HtmlMakerFactory.class){
                if(factory == null){
                    factory = new HtmlMakerFactory();
                }
            }
        }
        return factory;
    }

    public HtmlMaker build(HtmlMakerEnum type,Class<? extends  HtmlMaker> cls){
        if(type == null) {
            return null;
        }else {
            HtmlMaker htmlMaker = htmlMakerMap.get(type);
            try {
                if (htmlMaker == null) {
                    if (!htmlMakerMap.containsKey(type)) {
                        htmlMaker = (HtmlMaker) ReflectUtil.newInstance(cls);
                        htmlMakerMap.putIfAbsent(type,htmlMaker);
                    }else {
                        htmlMaker = htmlMakerMap.get(type);
                    }
                }
            }catch (Exception e){
                logger.error("[HtmlMakeFactory] buid htmalmaker find error,casuse by {}",e.getMessage(),e);
            }
            return htmlMaker;
        }

    }
}



