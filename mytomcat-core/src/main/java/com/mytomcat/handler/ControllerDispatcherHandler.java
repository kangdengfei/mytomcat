package com.mytomcat.handler;

import com.mytomcat.context.DefaultHttpContext;
import com.mytomcat.controller.ControllerContext;
import com.mytomcat.controller.ControllerProxy;
import com.mytomcat.controller.ProxyInvocation;
import com.mytomcat.controller.imp.DefaultControllerContext;
import com.mytomcat.http.HttpRenderUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
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
     * @param request
     * @throws Exception
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, HttpRequest request)  {
        FullHttpResponse response = null;
        stageRequest(request,ctx);
        try {
            response = invokeResponse(request);

            //处理业务，找到对应的controllerProxy
        } catch (Exception e){
            logger.error("[ControllerDispatcherHandler] find error,cause by {}",e.getMessage(),e);
            response = HttpRenderUtil.getErrorResponse(e.getMessage());
        }
        finally {
            writeResponse(response,ctx);

            DefaultHttpContext.clear();
        }
    }

    private FullHttpResponse invokeResponse(HttpRequest httpRequest) {
        FullHttpResponse httpResponse = null;
        //todo 再加个判断
        if (httpRequest.uri().equals("/") ) {
            return HttpRenderUtil.getDefaultResponse();
        } else {
            String uri = httpRequest.uri();
            int i = uri.indexOf('?');
            ControllerProxy proxy = controllerContext.getProxy(httpRequest.method(), uri.substring(0,i));
            if (proxy == null) {
                httpResponse = HttpRenderUtil.getNotFoundResponse();
                return httpResponse;
            } else{
                try {
                    Object result = ProxyInvocation.invoke(proxy);
                    httpResponse = HttpRenderUtil.buildResponse(result, proxy.getResponseType());
                } catch (Exception e) {
                    logger.error("[ControllerDispatcherHandelr] get httpResponse find exception,cause by {}",e.getMessage(),e);
                    httpResponse = HttpRenderUtil.getErrorResponse(e.getMessage());
                }
            }
                return httpResponse;

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

    public void writeResponse(FullHttpResponse response,ChannelHandlerContext ctx){
        HttpHeaders heads = response.headers();
//        heads.add(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.TEXT_PLAIN + "; charset=UTF-8");
        heads.add(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes()); // 3
        heads.add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        ctx.write(response);
        ctx.flush();

    }

}



