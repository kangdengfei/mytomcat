package com.mytomcat.filter;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import org.junit.Test;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-03-12 18:27
 **/

public class LinkedProcessorChainFilter implements Filter<FullHttpRequest,FullHttpResponse,LinkedProcessorChain>  {



    @Override
    public void doFilter(FullHttpRequest httpRequest ,FullHttpResponse httpResponse,LinkedProcessorChain chain) {
        chain.addLast(new AuthProcessor());
        chain.addLast(new LogProcessor());
        chain.addLast(new LogginProcessor());
        chain.process(httpRequest,httpResponse);
    }

    @Test
    public void test2(){
        Filter instance = FilterContext.getInstance();
//        instance.doFilter();
    }

    @Test
    public void test3(){
        LinkedProcessorChain chain = new  LinkedProcessorChain<Integer,Integer>();
        chain.addLast(new AuthProcessor());
        chain.addLast(new LogginProcessor());
        chain.addLast(new LogProcessor());
        chain.process(2,3);
    }
}



