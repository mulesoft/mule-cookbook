package com.cookbook.tutorial;

/**
 * Created by Mulesoft.
 */
public class ErrorResponse {
    private String error;
    private String message;

    public ErrorResponse(String error,String message){
        this.error = error;
        this.message = message;
    }

    public ErrorResponse(){
        this("","");
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
