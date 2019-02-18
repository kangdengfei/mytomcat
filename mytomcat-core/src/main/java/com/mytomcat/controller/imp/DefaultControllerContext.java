package com.mytomcat.controller.imp;

import com.mytomcat.controller.ControllerContext;
import com.mytomcat.controller.ControllerProxy;
import io.netty.handler.codec.http.HttpMethod;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-18 17:22
 **/
public class DefaultControllerContext implements ControllerContext {
    @Override
    public void addProxy(String path, ControllerProxy controllerProxy) {
        //todo

    }

    @Override
    public ControllerProxy getProxy(HttpMethod httpMethod, String uri) {
        //todo
        return null;
    }
}



