package com.mytomcat.cookie;

import cn.hutool.db.Session;
import io.netty.handler.codec.http.cookie.Cookie;

import java.util.Map;
import java.util.Set;

/**
 * cookie管理
 */
public interface CookieManager {
    /**
     * 获取所有的cookie
     * @return cookie集合
     */
    Set<Cookie> getCookies();

    /**
     * 获取所有cookie，并返回一个Map;
     * @return
     */
    Map<String,Cookie> getCookieMap();

    /**
     * 根据名称获取cookie
     * @param name
     * @return
     */
    Cookie getCookie(String name);

    /**
     * 根据名称获得cookie的值
     * @param name
     * @return
     */
    String getCookieValue(String name);

    /**
     * 设置cookie到响应结果中
     * @param cookie
     */
    void setCookie(Cookie cookie);


    void setCookies();

    /**
     * 添加一个cookie
     * @param name
     * @param value
     */
    void addCookie(String name,String value);

    /**
     * 添加一个cookie，并指明作用域
     * @param name
     * @param value
     * @param domain
     */
    void addCookie(String name,String value,String domain);

    /**
     * 添加一个cookie，指明作用域跟过期时间
     * @param name
     * @param value
     * @param domain
     * @param maxAge
     */
    void addCookie(String name,String value,String domain,long maxAge);

    /**
     * 删除一个cookie
     * @param name
     * @return
     */
    boolean deleteCookie(String name);
}
