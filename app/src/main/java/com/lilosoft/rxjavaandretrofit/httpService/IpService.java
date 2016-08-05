package com.lilosoft.rxjavaandretrofit.httpService;

import com.lilosoft.rxjavaandretrofit.Entity.IpBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by chablis on 2016/8/3.
 */
public interface IpService {
    @GET("getIpInfo.php")
    Observable<IpBean> getAddress(@Query("ip") String ip);
}
