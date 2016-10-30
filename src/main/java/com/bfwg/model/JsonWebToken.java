package com.bfwg.model;

/**
 * Created by fan.jin on 2016-10-27.
 */
public class JsonWebToken {
    private String token;

    public JsonWebToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
