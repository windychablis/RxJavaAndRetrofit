package com.lilosoft.outsidescreen.activity;

import android.content.Intent;
import android.os.Bundle;

import com.lilosoft.outsidescreen.R;
import com.lilosoft.outsidescreen.base.BaseActivity;
import com.lilosoft.outsidescreen.common.PrefUtils;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class StartActivity extends BaseActivity {

    private VideoView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_video);
//        String str = PrefUtils.getUserInfo();
//        Userinfo userinfo = JSON.parseObject(str, Userinfo.class);
        Intent intent=null;
        if(PrefUtils.getCacheLoginState()){
            intent=new Intent(this,MainActivity.class);
        }else{
            intent=new Intent(this,LoginActivity.class);
            intent.putExtra("flag",1); //从开始登陆的
        }
        this.finish();
        startActivity(intent);
    }
}
