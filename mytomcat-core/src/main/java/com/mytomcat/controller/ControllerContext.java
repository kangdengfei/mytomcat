package com.mytomcat.controller;

import io.netty.handler.codec.http.HttpMethod;

public interface ControllerContext {
    /**
     * 添加Controller 代理
     * @param path 请求路径
     * @param controllerProxy 代理
     */
    void addProxy(String path,ControllerProxy controllerProxy);


    /**
     * 获取Controller代理
     * @param httpMethod 请求方法类型
     * @param uri 请求路径
     * @return
     */
    ControllerProxy getProxy(HttpMethod httpMethod,String uri);

}
