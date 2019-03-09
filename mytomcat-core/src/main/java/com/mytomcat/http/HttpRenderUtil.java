package com.mytomcat.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mytomcat.common.enums.HtmlMakerEnum;
import com.mytomcat.common.enums.ResponseType;
import com.mytomcat.common.html.HtmlMaker;
import com.mytomcat.common.html.HtmlMakerFactory;
import com.mytomcat.common.html.imp.DefaultHtmlMaker;
import com.mytomcat.common.view.Page404;
import com.mytomcat.common.view.PageIndex;
import com.mytomcat.utils.HtmlContentUtil;
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
    public static FullHttpResponse buildResponse(Object object , ResponseType responseType){
        byte[] bytes = HttpRenderUtil.getBytes(object);
        ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
        ResponseType type = responseType!=null ? responseType:ResponseType.JSON;
        response.headers().add(HttpHeaderNames.CONTENT_TYPE, type.getContentType());
        response.headers().add(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(byteBuf.readableBytes()));
        return response;
    }



    /**
     * 404 NotFoundResponse
     * @return 响应对象
     */
    public static FullHttpResponse getNotFoundResponse(){
        HtmlMaker htmlMaker = HtmlMakerFactory.instance().build(HtmlMakerEnum.STRING,DefaultHtmlMaker.class);
        String htmlTpl = Page404.HTML;
        String content = HtmlContentUtil.getPageContent(htmlMaker,htmlTpl,null);
        return buildResponse(content, ResponseType.HTML);
    }

    public static FullHttpResponse getDefaultResponse(){
        HtmlMaker htmlMaker = HtmlMakerFactory.instance().build(HtmlMakerEnum.STRING,DefaultHtmlMaker.class);
        String htmlTpl = PageIndex.HTML;
        String content = HtmlContentUtil.getPageContent(htmlMaker, htmlTpl, null);
        return buildResponse(content,ResponseType.HTML);
    }

    /**
     * ErrorResponse
     * @param errorMessage
     * @return
     */
    public static FullHttpResponse getErrorResponse(String errorMessage){
        JSONObject object = new JSONObject();
        object.put("code",500);
        object.put("message",errorMessage);
        return buildResponse(object,ResponseType.JSON);
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



