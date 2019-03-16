package com.mytomcat.filter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.ClassScaner;
import com.mytomcat.common.CommonConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.Set;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-03-13 21:42
 **/
public class FilterContext {

    private static Logger logger = LoggerFactory.getLogger(FilterContext.class);
    private static Filter instance;

    private FilterContext(){

    }
    public static Filter getInstance(){
        if (instance == null){
            synchronized (FilterContext.class){
                if (instance == null){
                    instance = getLinkedProcessChain();
                }
            }
        }
        return instance;
    }

    private static Filter getLinkedProcessChain(){
        Filter instance = null;
        try {
            Set<Class<?>> classSet = ClassScaner.scanPackageBySuper(CommonConstants.BEAN_SCAN_PACKAGE, Filter.class);
            if (CollectionUtil.isNotEmpty(classSet)) {
                for (Class cls : classSet) {
                    if (Filter.class.isAssignableFrom(cls) && !cls.isInterface()) {
                        Constructor declaredConstructor = cls.getDeclaredConstructor();
                        declaredConstructor.setAccessible(false);
                        instance = (Filter) cls.newInstance();
                    }
                }
            }
        }catch (Exception e){
            logger.error("[FilterContext] getLinkedProcessChain find exception ,cause by {}",e.getMessage());
        }

        return instance;
    }
}



