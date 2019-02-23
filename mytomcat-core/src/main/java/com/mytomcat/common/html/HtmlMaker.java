package com.mytomcat.common.html;

import java.util.Map;

public interface HtmlMaker {

    String make(String htmlTemplate,Map<String,Object> conentMap);
}
