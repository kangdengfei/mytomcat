package com.mytomcat.handler;

import com.mytomcat.context.DefaultHttpContext;
import com.mytomcat.controller.ControllerContext;
import com.mytomcat.controller.ControllerProxy;
import com.mytomcat.controller.imp.DefaultControllerContext;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-19 21:34
 **/
public class ControllerDispatcherHandler extends SimpleChannelInboundHandler<HttpRequest> {
    private static final Logger logger = LoggerFactory.getLogger(ControllerDispatcherHandler.class);

    private ControllerContext controllerContext = DefaultControllerContext.gerInstance();
    /**
     * 从客户度收到新
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, HttpRequest msg)  {
        HttpResponse response = null;
        try {
            //处理业务，找到对应的controllerProxy




        } finally {

            ReferenceCountUtil.release(msg);
        }
    }

    private HttpResponse invokeResponse(HttpRequest httpRequest){
        ControllerProxy proxy = controllerContext.getProxy(httpRequest.method(), httpRequest.uri());
        if(proxy == null){
            return null
        }else {

        }

    }



    private void stageRequest(HttpRequest request, ChannelHandlerContext ctx){
        // 将request和context存储到ThreadLocal中去，便于后期在其他地方获取并使用
        DefaultHttpContext.currentContext().setRequest(request).setContext(ctx);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


}



