package com.lilosoft.rxjavaandretrofit.httpService;

import com.lilosoft.rxjavaandretrofit.Entity.HttpResult;
import com.lilosoft.rxjavaandretrofit.Entity.Subject;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by chablis on 2016/8/4.
 */
public interface MovieService {
    @GET("top250")
    Observable<HttpResult<List<Subject>>> getTopMovie(@Query("start") int start, @Query("count") int count);
}
