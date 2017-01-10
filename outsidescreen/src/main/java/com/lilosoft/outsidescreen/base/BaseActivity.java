package com.lilosoft.outsidescreen.base;

import android.app.KeyguardManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.lilosoft.outsidescreen.R;

import java.io.Serializable;

public class BaseActivity extends FragmentActivity {
    public AppContext appContext;
    Window _window;
    protected FragmentManager mFragmentManager;
    protected String TAG=this.getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appContext= (AppContext) AppContext.get();
        mFragmentManager = getSupportFragmentManager();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// 横屏
        _window = getWindow();
        WindowManager.LayoutParams params = _window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
        _window.setAttributes(params);
        setContentView(R.layout.activity_base);
        unlock();


    }

    public void nextActivity(Class<?> clazz, boolean isPlayAnim, String name, Serializable s){
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        if (null != name && !name.trim().equals("")) {
            intent.putExtra(name, s);
        }
        startActivity(intent);
        if (isPlayAnim) {
//            overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
        }
    }
    public void nextActivity(Class<?> clazz){
        nextActivity(clazz, true);
    }
    public void nextActivity(Class<?> clazz, boolean isPlayAnim){
        nextActivity(clazz, true, null, null);
    }
    public void nextActivity(Class<?> clazz, String name, Serializable s){
        nextActivity(clazz, true, name, s);
    }

    public void unlock(){

        PowerManager pm = (PowerManager)getSystemService(POWER_SERVICE);
        PowerManager.WakeLock mWakelock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |PowerManager.SCREEN_DIM_WAKE_LOCK, "SimpleTimer");
        mWakelock.acquire();
        mWakelock.release();
        KeyguardManager keyguardManager = (KeyguardManager)getSystemService(KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("");
        keyguardLock.disableKeyguard();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}
