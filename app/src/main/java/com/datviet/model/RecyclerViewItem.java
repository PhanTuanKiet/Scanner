package com.datviet.model;

import java.io.Serializable;

public class RecyclerViewItem implements Serializable {

    public String code;
    public String name;
    public String datetime;
    public String beginDate;
    public String expireDate;

    public RecyclerViewItem() {
    }

    public RecyclerViewItem(String code, String datetime){
        this.code = code;
        this.datetime = datetime;
    }

    public RecyclerViewItem(String code, String name, String beginDate, String expireDate){
        this.code = code;
        this.name = name;
        this.beginDate = beginDate;
        this.expireDate = expireDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String code) {
        datetime = datetime;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        beginDate = beginDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        expireDate = expireDate;
    }

}
