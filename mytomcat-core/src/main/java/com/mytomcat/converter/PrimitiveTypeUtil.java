package com.mytomcat.converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-03-05 15:44
 *
 * 定义基本类型的工具类，
 * 用来判断一个Class对象是否是属于基本类型获基本类型的数组
 * String
 * boolean
 * byte
 * short
 * int
 * long
 * float
 * double
 * char
 * Boolean
 * Byte
 * Short
 * Integer
 * Long
 * Float
 * Double
 * Character
 * BigInteger
 * BigDecimal
 *
 **/
public class PrimitiveTypeUtil {
    private PrimitiveTypeUtil(){};

    /** 基本类型  **/
    private static final Class<?> [] PRI_TYPE ={
            String .class,
            boolean.class,
            byte.class,
            short.class,
            int.class,
            long.class,
            float.class,
            double.class,
            char.class,
            Boolean.class,
            Byte.class,
            Short.class,
            Integer.class,
            Long.class,
            Float.class,
            Double.class,
            Character.class,
            BigInteger.class,
            BigDecimal.class
    };

    /** 基本数组类型  **/
    private static final Class<?>[] PRI_ARRAY_TYPE = {
            String[].class,
            boolean[].class,
            byte[].class,
            short[].class,
            int[].class,
            long[].class,
            float[].class,
            double[].class,
            char[].class,
            Boolean[].class,
            Byte[].class,
            Short[].class,
            Integer[].class,
            Long[].class,
            Float[].class,
            Double[].class,
            Character[].class,
            BigInteger[].class,
            BigDecimal[].class
    };

    /**
     * 基本类型默认值
     */
    private static final Map<Class<?>, Object> primitiveDefaults = new HashMap<Class<?>, Object>(9);
    static {
        primitiveDefaults.put(boolean.class, false);
        primitiveDefaults.put(byte.class, (byte)0);
        primitiveDefaults.put(short.class, (short)0);
        primitiveDefaults.put(char.class, (char)0);
        primitiveDefaults.put(int.class, 0);
        primitiveDefaults.put(long.class, 0L);
        primitiveDefaults.put(float.class, 0.0f);
        primitiveDefaults.put(double.class, 0.0);
    }

    /**
     * 判断是否为基本类型数组
     * @return
     */
    public static boolean isPriType(Class<?> cls) {
        for (Class<?> priType : PRI_ARRAY_TYPE) {
            if (priType.isAssignableFrom(cls))
                return true;

        }
        return false;
    }

    /**
     * 获得基本类型的默认值
     * @param type
     * @return
     */
    public static Object getPriDefaultValue(Class<?> type) {
        return primitiveDefaults.get(type);
    }
}



