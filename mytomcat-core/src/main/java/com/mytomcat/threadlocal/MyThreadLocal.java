package com.mytomcat.threadlocal;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-28 21:29
 **/
public class MyThreadLocal {

    private static final ThreadLocal<MyThreadLocal> threadLocal = new ThreadLocal();
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    public void setHttpRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }

    public void setHttpResponse(HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }

    private MyThreadLocal(){

    }
    public static MyThreadLocal getInstance(){
        MyThreadLocal myThreadLocal = threadLocal.get();
        if (myThreadLocal == null){
            myThreadLocal = new MyThreadLocal();
            threadLocal.set(myThreadLocal);
        }
        return myThreadLocal;
    }

    public static void clear(){
        threadLocal.remove();
    }

}



