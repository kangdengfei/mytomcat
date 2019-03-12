package com.mytomcat.example;

import com.alibaba.fastjson.JSONObject;
import com.mytomcat.annotation.Bean;
import com.mytomcat.annotation.Controller;
import com.mytomcat.annotation.Param;
import com.mytomcat.annotation.RequestMapping;
import com.mytomcat.common.enums.RequestMethod;
import com.mytomcat.common.enums.ResponseType;
import com.mytomcat.cookie.CookieManager;
import com.mytomcat.cookie.imp.DefaultCookieManager;

/**
 * @program: mytomcat
 * @authonr: KDF
 * @create: 2019-03-11 18:23
 **/
@Bean
@Controller(path = "/cookie")
public class CookieController {
    private CookieManager cookieManager = DefaultCookieManager.getInstance();

    @RequestMapping(path = "/add",requestMethod = RequestMethod.GET ,responseType = ResponseType.JSON)
    public JSONObject add(@Param(key = "name")String name,@Param(key = "value") String val){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tip","请在响应头 Response Headers 中查看 set-cookie 的值");
        jsonObject.put("cookieName",name);
        jsonObject.put("cookieValue",val);
        cookieManager.addCookie(name,val);
        return jsonObject;
    }
}



