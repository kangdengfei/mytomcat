package com.mytomcat.controller;

import com.mytomcat.threadlocal.MyThreadLocal;
import com.mytomcat.utils.HttpRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.renderable.;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-28 20:32
 **/
public class ProxyInvocation {
    Logger logger = LoggerFactory.getLogger(ProxyInvocation.class);
    private static Invoke invoke = new Invoke();


    public static Object invoke(ControllerProxy proxy) throws Exception{
        Object controller = proxy.getController();
        Method method = proxy.getMethod();
        String methodName = proxy.getMethodName();
        return invoke.invoke(controller,method,methodName);
    }

    public static class Invoke{
        Logger logger = LoggerFactory.getLogger(Invoke.class);
        public Object invoke (Object controller,Method method,String methodName){
            if (method == null){
                throw  new RuntimeException();
            }

            Class<?> cls = controller.getClass();
            Class<?>[] parameterTypes = method.getParameterTypes();
            Object [] parames = null;
            Object result;
            try {
                parames = HttpRequestUtil.getParameeterMap();
                method.invoke(controller,parames);
            }catch (Exception e){
                logger.error(e.getMessage());
            }


        }

        public Object [] getParameters(Method method,Class<?>[] parameterTypes){
            Map<String, List<String>> parameeterMap = HttpRequestUtil.getParameeterMap(MyThreadLocal.getInstance().getHttpRequest());
            method.
        }
    }

}



