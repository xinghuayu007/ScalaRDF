package com.triad.tools;

/**
 * Created by zhuoying on 2015/11/11.
 */
public enum LUBMQueries {
    Q1(1),
    Q2(2),
    Q3(3),
    Q4(4),
    Q5(5),
    Q6(6),
    Q7(7);


    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    LUBMQueries(int code){
        this.code = code;
    }
}
