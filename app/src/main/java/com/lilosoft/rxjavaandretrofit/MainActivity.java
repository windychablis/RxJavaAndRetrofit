package com.lilosoft.rxjavaandretrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

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
        getAddress();
    }

    //进行网络请求
    private void getAddress() {
        String baseUrl = "http://ip.taobao.com/service/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IpService ipService=retrofit.create(IpService.class);
        Call<IpBean> call=ipService.getAddress("myip");
        call.enqueue(new Callback<IpBean>() {
            @Override
            public void onResponse(Call<IpBean> call, Response<IpBean> response) {
                result_TV.setText(response.body().toString());
            }

            @Override
            public void onFailure(Call<IpBean> call, Throwable t) {
                result_TV.setText(t.getMessage());
            }
        });
    }
}
