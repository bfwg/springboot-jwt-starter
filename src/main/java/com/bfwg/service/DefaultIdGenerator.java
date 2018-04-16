package com.bfwg.service;

public class DefaultIdGenerator implements IdGenerator {
    public String getNext(){
        return java.util.UUID.randomUUID().toString().replace("-","");
    }
}
