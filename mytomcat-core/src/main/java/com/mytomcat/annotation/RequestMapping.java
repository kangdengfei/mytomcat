package com.mytomcat.annotation;

import com.mytomcat.common.enums.RequestMethod;
import com.mytomcat.common.enums.ResponseType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    /**
     * 请求路径
     * @return
     */
    String path() default "";

    /**
     * 请求方法
     * @return
     */
    RequestMethod requestMethod() default RequestMethod.GET;

    /**
     * 返回类型
     * @return
     */
    ResponseType responseType() default ResponseType.JSON;


}
