package com.lilosoft.retrofit;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by chablis on 2016/10/18.
 */
public interface MovieService {
    @GET("projectService/listAllDeptToAndroid")
    Observable<HttpResult<List<Dept>>> getTopMovie(@Query("p_isBus") int isBus);
}
