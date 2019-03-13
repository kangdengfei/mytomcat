package com.mytomcat.filter;

import org.junit.Test;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-03-12 18:27
 **/

public class LinkedProcessorChainFilter implements Filter<Object,LinkedProcessorChain>  {

    @Test
    public void testChain(){
//        doFilter();

    }


    @Override
    public void doFilter(Object object , LinkedProcessorChain chain) {
//        LinkedProcessorChain chain = new  LinkedProcessorChain<Integer>();
        chain.addLast(new AuthProcessor());
        chain.addLast(new LogginProcessor());
        chain.addLast(new LogProcessor());
        chain.process(object);
    }

    @Test
    public void test2(){
        Filter instance = FilterContext.getInstance();
//        instance.doFilter();
    }
}



