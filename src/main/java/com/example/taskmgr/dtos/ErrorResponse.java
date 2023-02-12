package com.example.taskmgr.dtos;

public class ErrorResponse {
    public String Message;

    public ErrorResponse(String message) {
        Message = message;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
