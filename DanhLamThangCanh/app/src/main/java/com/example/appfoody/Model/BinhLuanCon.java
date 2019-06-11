package com.example.appfoody.Model;

public class BinhLuanCon {
    String userName;
    String noiDung;
    Double mark;

    public BinhLuanCon()
    {

    }
    public String getUserName() {
        return userName;
    }

    public BinhLuanCon setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public BinhLuanCon setNoiDung(String noiDung) {
        this.noiDung = noiDung;
        return this;
    }

    public Double getMark() {
        return mark;
    }

    public BinhLuanCon setMark(Double mark) {
        this.mark = mark;
        return this;
    }
}
