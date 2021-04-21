package com.juhi.amazonapi;

import java.util.List;

public class CustomizedResponse<T>{
    private String msg;
    private List<T> body;

    public CustomizedResponse() {

    }

    public CustomizedResponse(String msg, List<T> body) {
        this.msg = msg;
        this.body = body;
    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getBody() {
        return body;
    }

    public void setBody(List<T> body) {
        this.body = body;
    }

}
