package com.datviet.model;

/**
 * Created by Phong Phan on 18-Oct-17.
 */

public class History {

    public int id;
    public String code;
    public String datetime;

    public History() {

    }

    public History(String code,String datetime){
        this.code = code;
        this.datetime = datetime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        code = code;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String code) {
        datetime = datetime;
    }
}
