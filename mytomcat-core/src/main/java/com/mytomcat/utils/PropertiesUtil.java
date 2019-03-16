package com.mytomcat.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-03-15
 **/
public class PropertiesUtil {
    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
    private static Properties properties = null;
    public  static Properties getInstance(String propertiesPath){
        InputStream inputStream = PropertiesUtil.class.getResourceAsStream(propertiesPath);
        if (properties == null){
            synchronized (PropertiesUtil.class){
                if (properties == null) {
                    try {
                        if (inputStream != null) {
                            properties = new Properties();
                            properties.load(inputStream);
                        }
                    }catch (Exception e){
                        logger.error("[PropertiesUtil getInstance occur error,cause:{}]",e.getMessage());
                    }

                }
            }
        }
        return properties;
    }
}



