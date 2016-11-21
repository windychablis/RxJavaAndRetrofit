package com.lilosoft.outsidescreen.bean;

import java.io.Serializable;
import java.util.Arrays;

public class Userinfo implements Serializable {

    public String getPoliticaltype() {
        return politicaltype;
    }

    public void setPoliticaltype(String politicaltype) {
        this.politicaltype = politicaltype;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkno() {
        return workno;
    }

    public void setWorkno(String workno) {
        this.workno = workno;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    private String politicaltype;
    private String id;
    private String workno;
    private String truename;
    private byte[] img;

    public String getDutyname() {
        return dutyname;
    }

    public void setDutyname(String dutyname) {
        this.dutyname = dutyname;
    }

    private String dutyname;

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Userinfo{" +
                "politicaltype='" + politicaltype + '\'' +
                ", id='" + id + '\'' +
                ", workno='" + workno + '\'' +
                ", truename='" + truename + '\'' +
                ", img=" + Arrays.toString(img) +
                ", dutyname='" + dutyname + '\'' +
                '}';
    }
}
