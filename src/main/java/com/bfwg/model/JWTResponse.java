package com.bfwg.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

/**
 * Created by fan.jin on 2016-10-17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JWTResponse extends BaseResponse {
    private String exceptionType;
    private String jwt;
    private Jws<Claims> jwsClaims;

    public JWTResponse() {}

    public JWTResponse(String jwt) {
        this.jwt = jwt;
        setStatus(Status.SUCCESS);
    }

    public JWTResponse(Jws<Claims> jwsClaims) {
        this.jwsClaims = jwsClaims;
        setStatus(Status.SUCCESS);
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Jws<Claims> getJwsClaims() {
        return jwsClaims;
    }

    public void setJwsClaims(Jws<Claims> jwsClaims) {
        this.jwsClaims = jwsClaims;
    }
}
