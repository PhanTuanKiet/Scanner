package com.datviet.model;

/**
 * Created by Phong Phan on 18-Oct-17.
 */

public class History {

    public int id;
    public String Code;

    public History() {

    }

    public History( String Code){
        this.Code = Code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
