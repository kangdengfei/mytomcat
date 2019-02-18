package com.mytomcat.Router;

import com.mytomcat.common.ResponseType;
import io.netty.handler.codec.http.HttpMethod;

public interface RouterContext {
    RouterResult<ResponseType> getRouteResult(HttpMethod method , String uri);
}
