package com.bazigar.bulandawaaz.util;

public class Response {
    private String message;
    private ResponseStatus status;
    private Object data;

    public Response() {
    }

    public Response(String message, ResponseStatus status, Object data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public Response(String message, ResponseStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", data=" + data +
                '}';
    }
}
