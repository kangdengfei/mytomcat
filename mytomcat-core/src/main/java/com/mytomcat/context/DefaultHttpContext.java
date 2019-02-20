package com.mytomcat.context;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.util.concurrent.FastThreadLocal;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.logging.Logger;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-17 18:54
 **/
public class DefaultHttpContext {
    private static Logger logger = (Logger) LoggerFactory.getLogger(DefaultHttpContext.class);
    private static final FastThreadLocal<DefaultHttpContext> CONTEXT_HOLDER = new FastThreadLocal<>();
    private HttpRequest request;

    private ChannelHandlerContext context;

    private HttpResponse response;

    private Set<Cookie> cookies;

    private DefaultHttpContext(){

    }

    public HttpRequest getRequest() {
        return request;
    }

    public DefaultHttpContext setRequest(HttpRequest request) {
        this.request = request;
        return this;
    }

    public ChannelHandlerContext getContext() {
        return context;
    }

    public DefaultHttpContext setContext(ChannelHandlerContext context) {
        this.context = context;
        return this;
    }

    public HttpResponse getResponse() {
        return response;
    }

    public void setResponse(HttpResponse response) {
        this.response = response;
    }

    public Set<Cookie> getCookies() {
        return cookies;
    }

    public void setCookies(Set<Cookie> cookies) {
        this.cookies = cookies;
    }


    public static DefaultHttpContext currentContext(){
        DefaultHttpContext context = CONTEXT_HOLDER.get();
        if(context==null){
            context = new DefaultHttpContext();
            CONTEXT_HOLDER.set(context);
        }
        return context;
    }

    public static void clear(){
        CONTEXT_HOLDER.remove();
    }

}



