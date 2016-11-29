package com.triad.tools;


public enum RegState {
    RUN(0,"Running"),
    STOP(1,"Stopped"),
    INITIALIZING(2,"Initializing");

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    RegState(Integer code,String message){
        this.code = code;
        this.message = message;
    }

    RegState(){
        this.code = 1;
        this.message = "Stopped";
    }
}
