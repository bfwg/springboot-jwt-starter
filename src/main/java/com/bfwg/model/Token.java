package com.bfwg.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by fan.jin on 2016-10-17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Token {
    private String jwt;

    public Token() {}

    public Token(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

}
