package com.mytomcat.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mytomcat.common.enums.ContentType;
import com.mytomcat.controller.ControllerContext;
import com.mytomcat.controller.imp.DefaultControllerContext;
import com.mytomcat.converter.PrimitiveTypeUtil;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-28
 **/
public class HttpRequestUtil {

    public static ControllerContext controllerContext = DefaultControllerContext.gerInstance();
    public static Map<String,List<String>> getParameeterMap(HttpRequest httpRequest){
        Map<String,List<String>> paramMap = new HashMap<>();
//        HttpMethod method = httpRequest.method();
        String method = httpRequest.method().toString().toUpperCase();
        if(method.equals(HttpMethod.GET.name())){
            String uri = httpRequest.uri();
            QueryStringDecoder queryStringDecoder = new QueryStringDecoder(uri, CharsetUtil.UTF_8);
            paramMap = queryStringDecoder.parameters();
        }else if(method.equals(HttpMethod.POST.name())){

            FullHttpRequest fullRequest = (FullHttpRequest) httpRequest;

            paramMap = getPostParamMap(fullRequest);
        }
        return paramMap;

    }

    /**
     * 获取post请求参数
     * @param fullRequest
     * @return
     */
    private static Map<String, List<String>> getPostParamMap(FullHttpRequest fullRequest) {
        Map<String, List<String>> paramMap = new HashMap<>();
        HttpHeaders headers = fullRequest.headers();
        String contentType = getContentType(headers);
        if(ContentType.APPLICATION_JSON.toString().equals(contentType)){
            String jsonStr = fullRequest.content().toString(CharsetUtil.UTF_8);
            JSONObject obj = JSON.parseObject(jsonStr);
            for(Map.Entry<String, Object> item : obj.entrySet()){
                String key = item.getKey();
                Object value = item.getValue();
                Class<?> valueType = value.getClass();

                List<String> valueList;
                if(paramMap.containsKey(key)){
                    valueList = paramMap.get(key);
                }else{
                    valueList = new ArrayList<String>();
                }

                if(PrimitiveTypeUtil.isPriType(valueType)){
                    valueList.add(value.toString());
                    paramMap.put(key, valueList);

                }else if(valueType.isArray()){
                    int length = Array.getLength(value);
                    for(int i=0; i<length; i++){
                        String arrayItem = String.valueOf(Array.get(value, i));
                        valueList.add(arrayItem);
                    }
                    paramMap.put(key, valueList);

                }else if(List.class.isAssignableFrom(valueType)){
                    if(valueType.equals(JSONArray.class)){
                        JSONArray jArray = JSONArray.parseArray(value.toString());
                        for(int i=0; i<jArray.size(); i++){
                            valueList.add(jArray.getString(i));
                        }
                    }else{
                        valueList = (ArrayList<String>) value;
                    }
                    paramMap.put(key, valueList);

                }else if(Map.class.isAssignableFrom(valueType)){
                    Map<String, String> tempMap = (Map<String, String>) value;
                    for(Map.Entry<String, String> entry : tempMap.entrySet()){
                        List<String> tempList = new ArrayList<String>();
                        tempList.add(entry.getValue());
                        paramMap.put(entry.getKey(), tempList);
                    }
                }
            }

        }else if(ContentType.APPLICATION_FORM_URLENCODED.toString().equals(contentType)){
            String jsonStr = fullRequest.content().toString(CharsetUtil.UTF_8);
            QueryStringDecoder queryDecoder = new QueryStringDecoder(jsonStr, false);
            paramMap = queryDecoder.parameters();
        }
        return paramMap;
    }


    public static String getContentType(HttpHeaders httpHeaders){
        String string = httpHeaders.get(HttpHeaderNames.CONTENT_TYPE);
        String[] split = string.split(";");
        return split[0];

    }



}



