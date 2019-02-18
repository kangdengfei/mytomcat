package com.mytomcat.common.enums;

import io.netty.handler.codec.http.HttpMethod;

public enum RequestMethod {

    GET(HttpMethod.GET),

    HEAD(HttpMethod.HEAD),

    PSOT(HttpMethod.POST),

    PUT(HttpMethod.PUT),

    PATCH(HttpMethod.PATCH),

    DELETE(HttpMethod.DELETE),

    OPTIONS(HttpMethod.OPTIONS),

    TRACE(HttpMethod.TRACE);

    private HttpMethod httpMethod;

    RequestMethod(HttpMethod httpMethod){
        this.httpMethod = httpMethod;
    }

    public static HttpMethod getHttpMethod(RequestMethod requestMethod){
        for(RequestMethod method : values()){
            if(requestMethod == method)
             return method.httpMethod;
        }
        return null;
    }


}



