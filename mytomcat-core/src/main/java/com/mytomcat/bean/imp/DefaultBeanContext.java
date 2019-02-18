package com.mytomcat.bean.imp;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.ClassScaner;
import cn.hutool.core.util.StrUtil;
import com.mytomcat.annotation.Autowired;
import com.mytomcat.annotation.Bean;

import com.mytomcat.annotation.Controller;
import com.mytomcat.annotation.Service;
import com.mytomcat.aware.Aware;
import com.mytomcat.aware.BeanContextAware;
import com.mytomcat.bean.BaseBean;
import com.mytomcat.bean.BeanContext;
import com.mytomcat.common.CommonConstants;
import com.mytomcat.init.InitFunc;
import com.mytomcat.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-16 16:27
 **/

public class DefaultBeanContext extends BaseBean implements BeanContext ,InitFunc {

    Logger logger = LoggerFactory.getLogger(DefaultBeanContext.class);
    public static HashMap<String,Object> beanMap = new HashMap();
    public static List<Class<? extends Annotation>> annotationLists = Arrays.asList(Bean.class,Controller.class,Service.class);
    /**
     * BeanContext的实例(单例)
     */
    private static DefaultBeanContext context;

    private DefaultBeanContext(){

    }

    public static BeanContext getInstance(){
        if(context==null) {
            synchronized (DefaultBeanContext.class) {
                if(context==null) {
                    context = new DefaultBeanContext();
                }
            }
        }
        return context;
    }


    //初始化bean
    public void initBean()  {
        logger.info("[DefaultBeanContex] begin initBean");
        try {

                Set<Class<?>> classSets = ClassScaner.scanPackageByAnnotation(CommonConstants.BEAN_SCAN_PACKAGE, Bean.class);
            if (CollectionUtil.isNotEmpty(classSets)) {
                /*
                 * 遍历所以类,找出有bean注解的Class，并且保存到beanmap中
                 */
                for (Class<?> cls : classSets) {

                    if (!cls.isAnnotation() && cls.isAnnotationPresent(Bean.class)) {
                        Bean annotation = (Bean) cls.getAnnotation(Bean.class);
                        String beanName = StrUtil.isNotBlank(annotation.name()) ? annotation.name() : cls.getName();
                        if (beanMap.containsKey(beanName)) {
                            logger.warn("[DefaultBeanContext] duplicate bean with name={}", beanName);
                            continue;
                        }
                        beanMap.put(beanName, cls.newInstance());
                    }
                }
                int size = beanMap.size();
                logger.info("[DefaultBeanContext] initBean success! [" + size + "] beans have created");
            } else {
                logger.warn("[DefaultBeanContext] no bean class scanned");
            }

        }catch (Exception e){
            logger.error("[DefaultBeanContext] initBean find error,cause:{}",e.getMessage(),e);
        }
    }


    /**
     * 注解处理器,bean的依赖注入
     * 如果注解Autowired配置了name属性，则根据name所指定的名称获取要注入的实例引用，
     * 否则根据属性所属类型来扫描配置文件获取要注入的实例引用
     */
    public void injectAnnotation() {
        logger.info("[DefaultBeanContext] start injectAnnotation");
        for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
            Object bean = entry.getValue();
            if (bean != null) {
                propertyAnnotation(bean);
                fieldAnnotation(bean);
            }
        }
        logger.info("[DefaultBeanContext] injectAnnotation success!");
    }

    /**
     * 处理在字段上的注解
     * @param bean 处理的bean
     */
    public void fieldAnnotation(Object bean){
        logger.info("[DefaultBeanContext] start fieldAnnotation");
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field field : declaredFields){
            if (field != null && field.isAnnotationPresent(Autowired.class)){
                Autowired annotation = field.getAnnotation(Autowired.class);
                Object value = null;
                if (StrUtil.isNotBlank(annotation.name())){
                    value = beanMap.get(annotation.name());
                }else {
                    for (Map.Entry<String, Object> entry : beanMap.entrySet()){
                        if (field.getType().isAssignableFrom(entry.getValue().getClass())){
                            value = entry.getValue();
                            break;
                        }
                    }
                }
                // 允许访问private字段
                field.setAccessible(true);
                try {
                    // 把引用对象注入属性
                    field.set(bean,value);
                    logger.info("[DefaultBeanContext] fieldAnnotation success!");

                } catch (IllegalAccessException e) {
                    logger.error("[DefaultBeanContext] fieldAnnotation error,cause:{}",e.getMessage(),e);
                }
            }
        }
    }

    /**
     * 处理在set方法加入的注解
     * @param bean 处理的bean
     */
    private void propertyAnnotation(Object bean){
        logger.info("[DefaultBeanContext] start propertyAnnotation");
        try {
            // 获取其属性的描述
            PropertyDescriptor[] descriptors = Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors();
            for(PropertyDescriptor descriptor : descriptors){
                // 获取所有set方法
                Method setter = descriptor.getWriteMethod();
                // 判断set方法是否定义了注解
                if(setter!=null && setter.isAnnotationPresent(Autowired.class)){
                    // 获取当前注解，并判断name属性是否为空
                    Autowired resource = setter.getAnnotation(Autowired.class);
                    String name;
                    Object value = null;
                    if(StrUtil.isNotBlank(resource.name())){
                        // 获取注解的name属性的内容
                        name = resource.name();
                        value = beanMap.get(name);
                    }else{ // 如果当前注解没有指定name属性,则根据类型进行匹配
                        for(Map.Entry<String,Object>  entry : beanMap.entrySet()){
                            // 判断当前属性所属的类型是否在beanHolderMap中存在
                            if(descriptor.getPropertyType().isAssignableFrom(entry.getValue().getClass())){
                                // 获取类型匹配的实例对象
                                value = entry.getValue();
                                break;
                            }
                        }
                    }
                    // 允许访问private方法
                    setter.setAccessible(true);
                    // 把引用对象注入属性
                    setter.invoke(bean, value);
                }
            }
            logger.info("[DefaultBeanContext] propertyAnnotation success!");
        } catch (Exception e) {
            logger.info("[DefaultBeanContext] propertyAnnotation error,cause:{}",e.getMessage(),e);
        }
    }

    public void processBeanContextAware() {
        Set<Class<?>> classSet = ClassScaner.scanPackageBySuper(CommonConstants.BEAN_SCAN_PACKAGE,BeanContextAware.class);
        if (CollectionUtil.isNotEmpty(classSet)) {
            try {
                for (Class cls : classSet) {
                    if (!cls.isInterface() && BeanContextAware.class.isAssignableFrom(cls)) {
//                        Constructor<?> constructor = cls.getDeclaredConstructor();
//                        constructor.setAccessible(true);
                        BeanContextAware aware = (BeanContextAware)cls.newInstance();
                        aware.setBeanContext(getInstance());
                    }
                }
            }catch (Exception e){

            }


        }
    }

    @Override
    public Object getBean(String name) {
        return beanMap.get(name);
    }


    @Override
    public void init() {
//        context=new DefaultBeanContext();
//        initBean();
//        injectAnnotation();
//        processBeanContextAware();
        System.out.println("Default");
    }
}



