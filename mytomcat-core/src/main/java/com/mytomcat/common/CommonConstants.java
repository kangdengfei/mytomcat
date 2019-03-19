package com.mytomcat.common;

import com.mytomcat.utils.PropertiesUtil;

import java.util.Properties;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-17 21:48
 **/
public class CommonConstants {
    private static final String REDANT_PROPERTIES_PATH = "/mytomcat.properties";
    public  static PropertiesUtil propertiesUtil= PropertiesUtil.getInstance(REDANT_PROPERTIES_PATH);
    public static final String BEAN_SCAN_PACKAGE = "com";

    public static final int BOSS_GROUP_SIZE = 2;

    public static final int WORKER_GROUP_SIZE = 4;
    /**
     * 服务端口号
     */
    public static final int SERVER_PORT = propertiesUtil.getInt("sever.port",9999);


}



