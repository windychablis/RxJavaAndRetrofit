package com.lilosoft.rxjavaandretrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by chablis on 2016/8/3.
 */
public interface IpService {
    @GET("getIpInfo.php")
    Call<IpBean> getAddress(@Query("ip") String ip);
}
