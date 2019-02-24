package com.mytomcat.Router.imp;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.ClassScaner;
import cn.hutool.core.util.StrUtil;
import com.mytomcat.Router.Router;
import com.mytomcat.Router.RouterContext;
import com.mytomcat.Router.RouterResult;
import com.mytomcat.annotation.Bean;
import com.mytomcat.annotation.Controller;
import com.mytomcat.annotation.Order;
import com.mytomcat.annotation.RequestMapping;
import com.mytomcat.bean.BeanContext;
import com.mytomcat.bean.imp.DefaultBeanContext;
import com.mytomcat.common.CommonConstants;
import com.mytomcat.common.enums.RequestMethod;
import com.mytomcat.common.enums.ResponseType;
import com.mytomcat.controller.ControllerContext;
import com.mytomcat.controller.ControllerProxy;
import com.mytomcat.controller.imp.DefaultControllerContext;
import com.mytomcat.init.InitFunc;
import io.netty.handler.codec.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Set;


/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-18 15:22
 **/
@Order(value = 2)
public class DefaultRouterContext implements RouterContext ,InitFunc {
    private static final Logger logger =  LoggerFactory.getLogger(DefaultRouterContext.class);

    /**
     * 所有路由信息
     */
    private static Router<ResponseType> router;

    /**
     * 初始化完成标志
     */
    private static volatile boolean inited;

    /**
     * bean 上下文
     */
    private static BeanContext beanContext;

    /**
     * controllerContext上下文
     */
    private static ControllerContext controllerContext;

    /**
     * 单实例
     */
    public  static volatile DefaultRouterContext defaultRouterContext;

    private DefaultRouterContext(){

    }


    public static RouterContext getInstance(){
        if(defaultRouterContext == null){ //减少对锁的调用
            synchronized (DefaultRouterContext.class){
                if(defaultRouterContext == null){ //防止重复调用
                    return new DefaultRouterContext();
                }
            }
        }
        return defaultRouterContext;
    }

    @Override
    public RouterResult<ResponseType> getRouteResult(HttpMethod method, String uri) {
        return null;
    }

    @Override
    public void init() {
        doInit();
    }

    public void doInit(){
        synchronized (DefaultRouterContext.class){
            beanContext = DefaultBeanContext.getInstance();
            controllerContext = DefaultControllerContext.gerInstance();
            initRouter();
        }

    }

    public void initRouter(){
        logger.info("[defaultRouterContext] initRouter");
        router = new Router<>();
        Set<Class<?>> classSet = ClassScaner.scanPackageByAnnotation(CommonConstants.BEAN_SCAN_PACKAGE, Controller.class);
        if (CollectionUtil.isNotEmpty(classSet)){
            for (Class cls : classSet){
                Controller controller = (Controller) cls.getAnnotation(Controller.class);
                Method[] declaredMethods = cls.getDeclaredMethods();
                if (declaredMethods != null){
                    for (Method method : declaredMethods){
                        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                        if (requestMapping != null){
                            addRoute(controller,requestMapping);
                            // 添加控制器
                            addProxy(cls,method,controller,requestMapping);
                        }
                    }

                }

            }
        }
    }


    private void addRoute(Controller controller,RequestMapping requestMapping){
        String path = controller.path()+requestMapping.path();
        HttpMethod httpMethod = RequestMethod.getHttpMethod(requestMapping.requestMethod());
        router.addRoute(path,httpMethod,requestMapping.responseType());
    }

    private void addProxy(Class cls,Method method,Controller controller, RequestMapping requestMapping){
        try {
            String path = controller.path() + requestMapping.path();
            ControllerProxy controllerProxy = new ControllerProxy();
            Bean bean = (Bean) cls.getAnnotation(Bean.class);
            Object object;
            if (bean != null) {
                 object = beanContext.getBean(StrUtil.isNotBlank(bean.name()) ? bean.name() : cls.getName());
            } else {
                 object = cls.newInstance();
            }
            controllerProxy.setController(object);
            controllerProxy.setMethod(method);
            controllerProxy.setResponseType(requestMapping.responseType());
            controllerProxy.setMethodName(method.getName());
            controllerProxy.setRequestMethod(requestMapping.requestMethod());
            controllerContext.addProxy(path,controllerProxy);
            logger.info("[DefaultRouter] addProxy path={} to proxy={}",path,controllerProxy);
        }catch (Exception e){
            logger.error("[DefaultRouter] add controllerProxy error ,cause {}",e.getMessage(),e);
        }

    }


}



