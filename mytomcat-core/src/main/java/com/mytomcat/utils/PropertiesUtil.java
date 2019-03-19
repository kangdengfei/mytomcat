package com.mytomcat.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-03-15
 **/
public class PropertiesUtil {
    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
    private static Properties properties = null;
    private static Map<String,PropertiesUtil> propertiesUtilsHolder = new HashMap();
    private static Map<PropertiesUtil,Properties> propertiesMap = new HashMap<>();
    private static volatile boolean flag;

    public  static PropertiesUtil getInstance(String propertiesPath){
        PropertiesUtil propertiesUtil = propertiesUtilsHolder.get(propertiesPath);
        if (propertiesUtil != null){
            return propertiesUtil;
        }
        try {
            propertiesUtil = new PropertiesUtil();
            properties = new Properties();
            InputStream inputStream = PropertiesUtil.class.getResourceAsStream(propertiesPath);
            if (inputStream != null){
                properties = new Properties();
                properties.load(inputStream);
                propertiesUtilsHolder.put(propertiesPath,propertiesUtil);
                propertiesMap.put(propertiesUtil,properties);
                flag = true;
            }
        }catch (Exception e){
            logger.error("[PropertiesUtil getInstance occur error,cause:{}]",e.getMessage());

        }
        return propertiesUtil;
    }

    private boolean propertiesLoaded(){
        int retryTime = 0;
        int retryTimeOut = 1000;
        int sleep = 500;
        while (!flag && retryTime <retryTimeOut){
            try{
                Thread.sleep(sleep);
                retryTime += sleep;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return flag;

    }

    public String getValue(String key){
        if (propertiesLoaded()){
            Properties properties = propertiesMap.get(this);
            return properties != null ? properties.getProperty(key) : null;
        }
        return null;
    }

    public Integer getInt(String key,Integer defaultValue){
        String value = getValue(key);
        int intVal;
        try {
            intVal = Integer.valueOf(value);
        }catch (Exception e){
            intVal = defaultValue;
        }
        return intVal;
    }
}



