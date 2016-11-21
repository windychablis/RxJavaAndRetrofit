package com.lilosoft.retrofit;

/**
 * Created by chablis on 2016/10/18.
 */
public class HttpResult<T> {
    //用来模仿resultCode和resultMessage
    private boolean success;

    //用来模仿Data
    private T data;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "success=" + success +
                ", data=" + data +
                '}';
    }
}
