package com.mytomcat.common.html.imp;

import cn.hutool.core.collection.CollectionUtil;
import com.mytomcat.common.html.HtmlMaker;
import com.mytomcat.common.view.HtmlKeyHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-21 21:08
 **/
public class DefaultHtmlMaker implements HtmlMaker {
    @Override
    public String make(String htmlTemplate, Map<String, Object> contentMap) {
        String html = htmlTemplate;
        if(CollectionUtil.isNotEmpty(contentMap)){
            for(Map.Entry<String,Object> entry : contentMap.entrySet()){
                String key = entry.getKey();
                Object val = entry.getValue();
                if(val instanceof String){
                    html = html.replaceAll(HtmlKeyHolder.START_ESCAPE+key+HtmlKeyHolder.END,val.toString());
                }
            }
        }
        return html;
    }

}



