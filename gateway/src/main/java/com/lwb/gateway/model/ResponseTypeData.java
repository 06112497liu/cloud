package com.lwb.gateway.model;

/**
 * @Author wangGang
 * @Description //TODO
 * @Date 2019-05-30 15:44
 **/
public class ResponseTypeData {
    private boolean text;
    private String contentType;

    public ResponseTypeData(){}

    public ResponseTypeData(boolean text, String contentType) {
        this.text = text;
        this.contentType = contentType;
    }

    public boolean isText() {
        return text;
    }

    public ResponseTypeData setText(boolean text) {
        this.text = text;
        return this;
    }

    public String getContentType() {
        return contentType;
    }

    public ResponseTypeData setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }
}
