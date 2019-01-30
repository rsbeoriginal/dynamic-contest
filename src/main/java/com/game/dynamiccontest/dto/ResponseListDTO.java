package com.game.dynamiccontest.dto;

import java.util.List;

public class ResponseListDTO<T> {
    private String status;
    private String errorMessage;
    private List<T> response;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<T> getResponse() {
        return response;
    }

    public void setResponse(List<T> response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "ResponseListDTO{" +
                "status='" + status + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", response=" + response +
                '}';
    }
}
