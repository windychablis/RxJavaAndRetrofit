package com.lilosoft.rxjavaandretrofit;

/**
 * Created by chablis on 2016/8/3.
 */
public class IpBean {
    private String code;
    private IpDetail data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public IpDetail getData() {
        return data;
    }

    public void setData(IpDetail data) {
        this.data = data;
    }

    public IpBean() {
    }
}
