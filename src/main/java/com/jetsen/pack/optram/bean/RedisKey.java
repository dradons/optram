package com.jetsen.pack.optram.bean;

/**
 * redis bean
 * Created by lenovo on 2017/10/24.
 */
public class RedisKey {
    private String key;
    private String value;

    public RedisKey(String key, String value){
        this.key=key;
        this.value=value;
    }

    public RedisKey(){
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
