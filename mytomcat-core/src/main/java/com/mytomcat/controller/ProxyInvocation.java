package com.mytomcat.controller;

import com.mytomcat.annotation.Param;
import com.mytomcat.converter.PrimitiveConverter;
import com.mytomcat.converter.PrimitiveTypeUtil;
import com.mytomcat.threadlocal.MyThreadLocal;
import com.mytomcat.utils.HttpRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
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
                parames = getParameters(method, parameterTypes);
                method.invoke(controller,parames);
            }catch (Exception e){
                logger.error(e.getMessage());
            }
            return null;
        }

        public Object [] getParameters(Method method,Class<?>[] parameterTypes){
            //获取参数
            Map<String, List<String>> parameeterMap = HttpRequestUtil.getParameeterMap(MyThreadLocal.getInstance().getHttpRequest());
            //构建用于存放数据的数组
            Object [] params = new Object[parameterTypes.length];
            Parameter[] parameters = method.getParameters();
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();

            //构造需要的参数列表
            for (int i = 0;i<parameterTypes.length;i++){
                Object param;
                Class<?> type = parameterTypes[i];
                Parameter parameter = parameters[i];
                if (parameter.isAnnotationPresent(Param.class)){
                    Param annotation = parameter.getAnnotation(Param.class);
                    //生成当前的调用参数
                    Object val = parseParameter(parameeterMap, type, annotation, null, 0);

                    params[i] = val;
                }else {
                    //没有注解
                }
            }
            return params;
        }

        private Object parseParameter(Map<String, List<String>> paramMap,Class<?> type,Param param,Method method,int index){
            Object val = null;
            String key = param.key();
            if (key != null && key.length()>0){

             if (Map.class.isAssignableFrom(Type.class)){

             }else {
                 List<String> list = paramMap.get(key);
                 if (list!= null){
                     // 基础类型
                     if(PrimitiveTypeUtil.isPriType(type)){
                         val = PrimitiveConverter.getInstance().convert(list.get(0), type);
                     }
                 }

             }
            }
            return val;
        }
    }

}



