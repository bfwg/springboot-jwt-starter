package com.bfwg.dto;


public class Token {
    private final String access_token;
    private final Long expires_in;

    public Token() {
        this.access_token = null;
        this.expires_in = null;
    }

    public Token(String access_token, long expires_in) {
        this.access_token = access_token;
        this.expires_in = expires_in;
    }

    public String getAccess_token() {
        return access_token;
    }


    public Long getExpires_in() {
        return expires_in;
    }

}