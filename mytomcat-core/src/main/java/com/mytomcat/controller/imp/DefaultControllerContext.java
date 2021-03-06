package com.mytomcat.controller.imp;

import com.mytomcat.Router.RouterContext;
import com.mytomcat.Router.imp.DefaultRouterContext;
import com.mytomcat.controller.ControllerContext;
import com.mytomcat.controller.ControllerProxy;
import io.netty.handler.codec.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-18 19:22
 **/
public class DefaultControllerContext implements ControllerContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultControllerContext.class);

    /**
     * 保存所有的RouterController的代理类
     */
    private static Map<String,ControllerProxy> proxyMap;

    /**
     * 路由上下文
     */
    private static RouterContext routerContext;

    /**
     * RouterContext的实例(单例)
     */
    private volatile static DefaultControllerContext context;

    private DefaultControllerContext(){
        routerContext = DefaultRouterContext.getInstance();
        proxyMap = new ConcurrentHashMap<>();
//        proxyMap.putIfAbsent(new ControllerProxy())

    }

    public static ControllerContext gerInstance(){
        if (context == null){
            synchronized (DefaultControllerContext.class){
                if(context == null){
                    context = new DefaultControllerContext();
                }
            }
        }
        return context;
    }


    @Override
    public void addProxy(String path, ControllerProxy controllerProxy) {
        proxyMap.putIfAbsent(path, controllerProxy);

    }

    @Override
    public ControllerProxy getProxy(HttpMethod httpMethod, String uri) {
        //todo 这个匹配有点粗糙
        if (proxyMap.containsKey(uri)){
            return proxyMap.get(uri);
        }else
            return null;
    }
}



