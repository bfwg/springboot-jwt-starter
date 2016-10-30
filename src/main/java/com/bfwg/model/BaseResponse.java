package com.bfwg.model;

/**
 * Created by fan.jin on 2016-10-17.
 */
public class BaseResponse {
    private String message;
    private Status status;

    public enum Status {
        SUCCESS, ERROR
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
