package com.mytomcat.common.enums;

public enum ResponseType {
    /**
     * JSON
     */
    JSON("application/json;charset=UTF-8"),

    /**
     * XML
     */
    XML("text/xml;charset=UTF-8"),

    /**
     * TEXT
     */
    TEXT("tet/plain;charset=UTF-8"),

    /**
     * HTML
     */
    HTML("text/html;charset=UTF-8");

    private String contentType;

    ResponseType(String contentType){
        this.contentType= contentType;
    }

    public String getContentType(){
        return contentType;
    }

}
