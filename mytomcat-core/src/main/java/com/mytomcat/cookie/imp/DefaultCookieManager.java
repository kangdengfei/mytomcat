package com.mytomcat.cookie.imp;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.mytomcat.context.DefaultHttpContext;
import com.mytomcat.cookie.CookieManager;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.DefaultCookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-03-09 17:10
 **/
public class DefaultCookieManager implements CookieManager {

    private static CookieManager cookieManager;

    private DefaultCookieManager(){};

    public static CookieManager getInstance(){
        if (cookieManager == null){
            synchronized (DefaultCookieManager.class){
                if (cookieManager == null)
                    cookieManager = new DefaultCookieManager();
            }
        }
        return cookieManager;
    }

    @Override
    public Set<Cookie> getCookies() {
        HttpRequest request = DefaultHttpContext.currentContext().getRequest();
        Set<Cookie> cookies = new HashSet<>();
        if (request != null){
            String val = request.headers().get(HttpHeaderNames.COOKIE);
            if (val != null){
                cookies = ServerCookieDecoder.STRICT.decode(val);
            }
        }
        return cookies;
    }

    @Override
    public Map<String, Cookie> getCookieMap() {
        Map<String,Cookie> cookieMap = new HashMap<>();
        Set<Cookie> cookies = getCookies();
        if (CollectionUtil.isNotEmpty(cookieMap)){
            for (Cookie cookie :cookies){
                cookieMap.put(cookie.name(),cookie);
            }
        }
        return cookieMap;
    }

    @Override
    public Cookie getCookie(String name) {
        return getCookieMap().get(name);
    }

    @Override
    public String getCookieValue(String name) {
        Cookie cookie = getCookie(name);
        return cookie == null ? null : cookie.value();
    }

    @Override
    public void setCookie(Cookie cookie) {
        DefaultHttpContext.currentContext().addCookie(cookie);
    }

    @Override
    public void setCookies() {
        Set<Cookie> cookies = getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies){
                setCookie(cookie);
            }
        }

    }

    @Override
    public void addCookie(String name, String value) {

        addCookie(name,value,null);
    }

    @Override
    public void addCookie(String name, String value, String domain) {
        addCookie(name,value,domain,0);

    }

    @Override
    public void addCookie(String name, String value, String domain, long maxAge) {
        if (StrUtil.isNotBlank(name) && StrUtil.isNotBlank(value)){
            Cookie cookie = new DefaultCookie(name,value);
            cookie.setPath("/");
            if (StrUtil.isNotBlank(domain)){
                cookie.setDomain(domain);
            }
            if (maxAge>0){
                cookie.setMaxAge(maxAge);
            }
            setCookie(cookie);
        }
    }

    @Override
    public boolean deleteCookie(String name) {
        Cookie cookie = getCookie(name);
        if (cookie != null){
            cookie.setMaxAge(0);
            cookie.setPath("/");
            setCookie(cookie);
            return true;
        }
        return false;
    }
}



