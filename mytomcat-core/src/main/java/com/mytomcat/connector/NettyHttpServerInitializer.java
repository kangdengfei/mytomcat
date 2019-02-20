package com.mytomcat.connector;

import com.mytomcat.handler.ControllerDispatcherHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-19 21:33
 **/
public class NettyHttpServerInitializer extends ChannelInitializer {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast("decoder", new HttpRequestDecoder()) ;  // 1
        pipeline.addLast("encoder", new HttpResponseEncoder()) ; // 2)
        pipeline.addLast(new ControllerDispatcherHandler());
    }
}



