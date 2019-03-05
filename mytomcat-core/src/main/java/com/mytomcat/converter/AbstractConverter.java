package com.mytomcat.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-03-05 16:28
 **/
public abstract class AbstractConverter implements Converter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public Object conver(Object source, Class<?> toType, Object... patams) {


        /**
         * 如果对象本身已经是所指定的类型则不进行转换直接返回
         * 如果对象能够被复制，则返回复制后的对象
         */
        if (source != null && toType.isInstance(source)){
            if(source.getClass().isArray() && source.getClass().getComponentType() == String.class){
                // 字符串数组虽然是Cloneable的子类，但并没有clone方法
                return source;
            }
        }
        try{
            Method m = source.getClass().getDeclaredMethod("clone", new Class[0]);
            m.setAccessible(true);
            return m.invoke(source, new Object[0])
        }catch (Exception e){

        }

    }
}



