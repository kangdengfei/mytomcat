package com.mytomcat.utils;

import com.mytomcat.common.html.HtmlMaker;
import com.mytomcat.common.html.HtmlMakerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-23 19:24
 **/
public class HtmlContentUtil {
    public static String getPageContent(HtmlMaker htmlMaker, String htmlTemplate, Map<String,Object> contentMap){
        return htmlMaker.make(htmlTemplate, contentMap);
    }
}



