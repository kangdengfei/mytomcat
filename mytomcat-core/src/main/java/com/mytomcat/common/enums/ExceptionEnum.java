package com.mytomcat.common.enums;


public enum ExceptionEnum {

    FORBBINEN(403,"请求拒绝");

     ExceptionEnum(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }


    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    private Integer code;
    private String msg;


}
