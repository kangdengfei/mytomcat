package com.context;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.ClassScaner;
import cn.hutool.core.util.StrUtil;
import com.annotation.Bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Set;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-16 16:27
 **/

public class DefaultBeanContext implements BeanContext {

    Logger logger = LoggerFactory.getLogger(DefaultBeanContext.class);
    public static HashMap<String,Object> beanMap = new HashMap();
    @Override
    public Object getBean(String name) {
        return null;
    }



    //初始化bean
    public void initBean()  {
        logger.info("[DefaultBeanContex] begin initBean");
        try {
            Set<Class<?>> classSets = ClassScaner.scanPackageByAnnotation("com", Bean.class);
            if (CollectionUtil.isNotEmpty(classSets)) {
                /*
                 * 遍历所以类,找出有bean注解的Class，并且保存到beanmap中
                 */
                for (Class<?> cls : classSets) {
                    Bean annotation = cls.getAnnotation(Bean.class);
                    if (annotation != null) {
                        String beanName = StrUtil.isNotBlank(annotation.name()) ? annotation.name() : cls.getName();
                        if (beanMap.containsKey(beanName)) {
                            logger.warn("[DefaultBeanContext] duplicate bean with name={}", beanName);
                            continue;
                        }
                        beanMap.put(beanName,cls.newInstance());
                    }
                }
                logger.info("[DefaultBeanContext] initBean success!");
            }else {
                logger.warn("[DefaultBeanContext] no bean class scanned");
            }
        }catch (Exception e){
            logger.error("[DefaultBeanContext] initBean find error,cause:{}",e.getMessage(),e);
        }
    }

    private void injectAnnotation() {

    }

}



