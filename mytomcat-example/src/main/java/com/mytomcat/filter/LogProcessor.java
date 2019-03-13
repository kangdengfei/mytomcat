package com.mytomcat.filter;

import com.mytomcat.context.DefaultHttpContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

import java.net.InetSocketAddress;


/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-03-12 18:26
 **/
public class LogProcessor extends AbstractLinkedProcessor<FullHttpRequest,FullHttpResponse> {


    @Override
    public void doProcess(FullHttpRequest httpRequest,FullHttpResponse httpResponse){
        ChannelHandlerContext context = DefaultHttpContext.currentContext().getContext();
        InetSocketAddress socketAddress =(InetSocketAddress) context.channel().remoteAddress();
        System.out.println("请求iP：" + socketAddress.getAddress());
    }
}



