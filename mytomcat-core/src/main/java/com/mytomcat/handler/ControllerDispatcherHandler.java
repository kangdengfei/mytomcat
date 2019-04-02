package com.mytomcat.handler;

import cn.hutool.core.collection.CollectionUtil;
import com.mytomcat.context.DefaultHttpContext;
import com.mytomcat.controller.ControllerContext;
import com.mytomcat.controller.ControllerProxy;
import com.mytomcat.controller.ProxyInvocation;
import com.mytomcat.controller.imp.DefaultControllerContext;
import com.mytomcat.cookie.CookieManager;
import com.mytomcat.cookie.imp.DefaultCookieManager;
import com.mytomcat.filter.FilterContext;
import com.mytomcat.filter.LinkedProcessorChain;
import com.mytomcat.http.HttpRenderUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.ServerCookieEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-19 21:34
 **/
public class ControllerDispatcherHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private static final Logger logger = LoggerFactory.getLogger(ControllerDispatcherHandler.class);

    private ControllerContext controllerContext = DefaultControllerContext.gerInstance();
    /**
     * 从客户度收到新
     * @param ctx
     * @param request
     * @throws Exception
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request)  {
        FullHttpResponse response = null;


        stageRequest(request,ctx);
        try {
            LinkedProcessorChain chain = new  LinkedProcessorChain<FullHttpRequest ,FullHttpResponse>();
            FilterContext.getInstance().doFilter(request,response,chain);
            response = invokeResponse(request);
            //处理业务，找到对应的controllerProxy
        } catch (Exception e){
            logger.error("[ControllerDispatcherHandler] find error,cause by {}",e.getMessage());
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
            uri = i == -1 ? uri:uri.substring(0,i);
            ControllerProxy proxy = controllerContext.getProxy(httpRequest.method(), uri);
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
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelRegistered:"+ 2);
        super.channelRegistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive:"+ 1);
        super.channelActive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelReadComplete:" + 3);
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public void writeResponse(FullHttpResponse response,ChannelHandlerContext ctx){
        buildHeader(response);
        ctx.write(response);
        ctx.flush();

    }

    public void buildHeader(FullHttpResponse response){
        HttpHeaders headers = response.headers();
        headers.add(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes()); // 3
        headers.add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        Set<Cookie> cookies = DefaultHttpContext.currentContext().getCookies();
        if (CollectionUtil.isNotEmpty(cookies)){
            //给对应的请求加cookie
            for (Cookie cookie : cookies){
                headers.add(HttpHeaderNames.SET_COOKIE,ServerCookieEncoder.STRICT.encode(cookie));
            }
        }
    }

}



