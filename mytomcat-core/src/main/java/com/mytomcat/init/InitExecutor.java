package com.mytomcat.init;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.ClassScaner;
import com.mytomcat.annotation.Order;
import com.mytomcat.common.CommonConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-24 14:43
 **/
public class InitExecutor {
    private static Logger logger = LoggerFactory.getLogger(InitExecutor.class);

    public static void init() {
        try{
        Set<Class<?>> classSet = ClassScaner.scanPackageBySuper(CommonConstants.BEAN_SCAN_PACKAGE, InitFunc.class);
        if (CollectionUtil.isNotEmpty(classSet)) {
            List<OrderWrapper> list = new ArrayList<>();
            for (Class cls : classSet) {
                if (!cls.isInterface() && InitFunc.class.isAssignableFrom(cls)) {
                    Constructor declaredConstructor = cls.getDeclaredConstructor();
                    declaredConstructor.setAccessible(true);
                    InitFunc initFunc = (InitFunc)declaredConstructor.newInstance();
                    insertFunc(list,initFunc);
                }
            }

            for (OrderWrapper orderWrapper : list){
                orderWrapper.initFunc.init();
                logger.info("[InitExecutor] initialized: {} with order={}", orderWrapper.initFunc.getClass().getCanonicalName(), orderWrapper.order);


            }
        }
    }catch (Exception e){

        }

    }


    public static void insertFunc(List<OrderWrapper> list,InitFunc initFunc){
        int i = resolverOrder(initFunc);
        int index = 0;
        for (;index<list.size();i++){
            if (list.get(index).getOrder()> i){
                break;
            }
        }
        list.add(index,new OrderWrapper(i,initFunc));
    }

    public static int resolverOrder(InitFunc initFunc){
        if (!initFunc.getClass().isAnnotationPresent(Order.class)){
            return Order.LOWEST_PRECEDENCE;
        }else {
            return initFunc.getClass().getAnnotation(Order.class).value();
        }

    }
    public static class OrderWrapper{
        private int order;
        private InitFunc initFunc;
        OrderWrapper(int order,InitFunc initFunc){
            this.initFunc = initFunc;
            this.order = order;
        }

        public int getOrder(){
            return order;
        }
    }

}



