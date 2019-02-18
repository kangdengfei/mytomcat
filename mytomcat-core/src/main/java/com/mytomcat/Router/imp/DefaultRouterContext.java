package com.mytomcat.Router.imp;

import com.mytomcat.Router.Router;
import com.mytomcat.Router.RouterContext;
import com.mytomcat.Router.RouterResult;
import com.mytomcat.common.ResponseType;
import com.mytomcat.init.InitFunc;
import io.netty.handler.codec.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-18 15:22
 **/
public class DefaultRouterContext implements RouterContext ,InitFunc {
    private static final Logger logger =  LoggerFactory.getLogger(DefaultRouterContext.class);
    @Override
    public RouterResult<ResponseType> getRouteResult(HttpMethod method, String uri) {
        return null;
    }

    @Override
    public void init() {

    }
}



