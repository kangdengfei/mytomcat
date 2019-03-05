package com.mytomcat.converter;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-03-05 16:27
 **/
public interface Converter {
    Object conver(Object source,Class<?> toType,Object... patams);

}



