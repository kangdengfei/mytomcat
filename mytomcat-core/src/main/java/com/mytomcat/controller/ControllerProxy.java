package com.mytomcat.controller;

import com.mytomcat.common.enums.RequestMethod;
import com.mytomcat.common.enums.ResponseType;

import java.lang.reflect.Method;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-18 21:45
 **/
public class ControllerProxy {

    private ResponseType responseType;

    private RequestMethod requestMethod;

    private Object controller;

    private Method  method;

    private String methodName;




    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String toString() {
        return "requestMethod=" + requestMethod +
                ", controller=" + controller.getClass().getName() +
                ", methodName='" + methodName + '\'' +
                '}';
    }
}



