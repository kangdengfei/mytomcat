package com.mytomcat.filter;

import com.mytomcat.context.DefaultHttpContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

import java.net.InetSocketAddress;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-03-12 18:24
 **/
public class LogginProcessor extends AbstractLinkedProcessor<FullHttpRequest,FullHttpResponse> {
    @Override
    public void doProcess(FullHttpRequest httpRequest,FullHttpResponse httpResponse){
        System.out.println("log: 登录成功");
    }
}



