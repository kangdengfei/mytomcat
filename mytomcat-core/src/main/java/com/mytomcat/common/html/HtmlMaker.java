package com.mytomcat.common.html;

import java.util.HashMap;

public interface HtmlMaker {

    String make(String htmlTemplate,HashMap<String,Object> conentMap);
}
