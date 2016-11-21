package com.lilosoft.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lilosoft.retrofit.subscribers.ProgressSubscriber;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.click_me_BN)
    Button clickMeBN;
    @Bind(R.id.result_TV)
    TextView resultTV;
    private ProgressSubscriber<List<Dept>> subscriber;

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
    private void getMovie() {
        /*subscriber = new ProgressSubscriber<List<Dept>>(this){

            @Override
            public void onNext(List<Dept> depts) {
                System.out.println(depts.toString());
            }
        };*/
        subscriber=new ProgressSubscriber<List<Dept>>(this) {
            @Override
            public void onNext(List<Dept> depts) {
                System.out.println(depts.get(0).toString());
                Toast.makeText(MainActivity.this, depts.get(0).toString(), Toast.LENGTH_SHORT).show();
                if (BuildConfig.DEBUG) Log.d("MainActivity", depts.get(0).toString());
            }
        };
        HttpMethods.getInstance().getTopMovie(subscriber,1);

    }
}
