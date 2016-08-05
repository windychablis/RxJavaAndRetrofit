package com.lilosoft.rxjavaandretrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lilosoft.rxjavaandretrofit.Entity.IpBean;
import com.lilosoft.rxjavaandretrofit.Entity.Subject;
import com.lilosoft.rxjavaandretrofit.httpService.IpService;
import com.lilosoft.rxjavaandretrofit.subscribers.ProgressSubscriber;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private ProgressSubscriber<List<Subject>> subscriber;

    @Bind(R.id.click_me_BN)
    Button click_me_BN;

    @Bind(R.id.result_TV)
    TextView result_TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.click_me_BN)
    public void onClick() {
        getMovie();
    }

    //进行网络请求
    private void getAddress() {
        String baseUrl = "http://ip.taobao.com/service/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                //添加rxjava的代码
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        IpService ipService = retrofit.create(IpService.class);
        ipService.getAddress("myip")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<IpBean>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(MainActivity.this, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        result_TV.setText(e.getMessage());
                    }

                    @Override
                    public void onNext(IpBean ipBean) {
                        result_TV.setText(ipBean.toString());
                    }
                })
        ;

    }


    /**
     * 使用httpmethod封装的retrofit请求
     */
    private void getMovie() {
        subscriber = new ProgressSubscriber<List<Subject>>(this) {
            @Override
            public void onNext(List<Subject> subjects) {
                result_TV.setText(subjects.toString());
            }
        };
        HttpMethods.getInstance().getTopMovie(subscriber, 0, 10);
    }
}
