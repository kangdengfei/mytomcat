package com.mytomcat.http;

import com.mytomcat.common.enums.ResponseType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-20 21:21
 **/
public class HttpRenderUtil {
    public static final String EMPTY_CONTENT = "";

    /**
     *
     * @param object 放回结果
     * @param responseType 数据类型
     * @return
     */
    public static FullHttpResponse Render(Object object , ResponseType responseType){
        byte[] bytes = HttpRenderUtil.getBytes(object);
        ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
        ResponseType type = responseType!=null ? responseType:ResponseType.JSON;
        response.headers().add(HttpHeaderNames.CONTENT_TYPE, type.getContentType());
        response.headers().add(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(byteBuf.readableBytes()));
        return response;
    }

    /**
     * 转换byte
     * @param content 内容
     * @return 响应对象
     */
    private static byte[] getBytes(Object content){
        if(content==null){
            return EMPTY_CONTENT.getBytes(CharsetUtil.UTF_8);
        }
        String data = content.toString();
        data = (data==null || data.trim().length()==0)?EMPTY_CONTENT:data;
        return data.getBytes(CharsetUtil.UTF_8);
    }
}



