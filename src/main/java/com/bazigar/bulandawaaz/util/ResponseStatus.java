package com.bazigar.bulandawaaz.util;

public class ResponseStatus {
    private Integer responseCode;
    private String responseReason;

    public ResponseStatus() {
    }

    public ResponseStatus(Integer responseCode, String responseReason) {
        this.responseCode = responseCode;
        this.responseReason = responseReason;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseReason() {
        return responseReason;
    }

    public void setResponseReason(String responseReason) {
        this.responseReason = responseReason;
    }

    @Override
    public String toString() {
        return "ResponseStatus{" +
                "responseCode=" + responseCode +
                ", responseReason='" + responseReason + '\'' +
                '}';
    }
}
