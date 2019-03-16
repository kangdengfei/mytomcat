package com.mytomcat.converter;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-03-05 19:27
 **/
public interface Converter {
    Object convert(Object source,Class<?> toType,Object... patams);

}



