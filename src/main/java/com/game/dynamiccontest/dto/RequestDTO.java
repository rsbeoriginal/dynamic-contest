package com.game.dynamiccontest.dto;

public class RequestDTO<T> {

    private String userId;
    private T request;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public T getRequest() {
        return request;
    }

    public void setRequest(T request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "RequestDTO{" +
                "userId='" + userId + '\'' +
                ", request=" + request +
                '}';
    }
}
