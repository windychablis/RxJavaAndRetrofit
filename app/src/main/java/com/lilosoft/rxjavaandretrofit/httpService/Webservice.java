package com.lilosoft.rxjavaandretrofit.httpService;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by chablis on 2016/8/5.
 */
public interface Webservice {
    @Headers({
            "Content-Type: application/soap+xml",
            "Accept-Charset: utf-8"
    })
    @POST("如果有子路径就写在这里")
    Call<String> getJson(@Body String requestStr );
}
