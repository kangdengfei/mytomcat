package com.mytomcat.Exception;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-03-13 14:18
 **/
public class ServerException extends RuntimeException {

    Integer code;
    String msg;

    public ServerException(Integer code,String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}



