package com.mytomcat.utils;

import com.mytomcat.controller.ControllerContext;
import com.mytomcat.controller.ControllerProxy;
import com.mytomcat.controller.imp.DefaultControllerContext;
import com.mytomcat.http.HttpRenderUtil;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.CharsetUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-28 14:26
 **/
public class HttpRequestUtil {

    public static ControllerContext controllerContext = DefaultControllerContext.gerInstance();
    public static Map<String,List<String>> getParameeterMap(HttpRequest httpRequest){
        Map<String,List<String>> paramMap = new HashMap<>();
        HttpMethod method = httpRequest.method();
        if(method.equals(HttpMethod.GET)){
            String uri = httpRequest.uri();
            QueryStringDecoder queryStringDecoder = new QueryStringDecoder(uri, CharsetUtil.UTF_8);
            paramMap = queryStringDecoder.parameters();
        }else if(method.equals(HttpMethod.POST)){


        }
        return paramMap;

    }


    public HttpResponse invokeResponse(HttpRequest httpRequest){
        HttpMethod method = httpRequest.method();
        String uri = httpRequest.uri();
        ControllerProxy proxy = controllerContext.getProxy(method, uri);
        if (proxy == null){
            return HttpRenderUtil.getNotFoundResponse();
        }


        return null;

    }
}



