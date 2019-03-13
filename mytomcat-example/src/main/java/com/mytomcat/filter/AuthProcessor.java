package com.mytomcat.filter;

import com.mytomcat.Exception.ServerException;
import com.mytomcat.common.enums.ExceptionEnum;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-03-12 18:16
 **/
public class AuthProcessor extends  AbstractLinkedProcessor<FullHttpRequest,FullHttpResponse> {



    @Override
    public void doProcess(FullHttpRequest httpRequest,FullHttpResponse httpResponse) {
        System.out.println("auth: 认证通过");
//        throw new ServerException(ExceptionEnum.FORBBINEN.getCode(),ExceptionEnum.FORBBINEN.getMsg());
    }


}



