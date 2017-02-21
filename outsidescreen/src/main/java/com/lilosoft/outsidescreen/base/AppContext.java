package com.lilosoft.outsidescreen.base;

/**
 * Created by chablis on 2016/11/7.
 */

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.lilosoft.outsidescreen.bean.Userinfo;

public class AppContext extends Application {

    private static Application sInstance;
    public Userinfo user;
    public static final long INTERVAL = 3000*60L; //防止连续点击的时间间隔
    private static long lastClickTime = 0L; //上一次点击的时间

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }


    /**
     * 是否启动检查更新
     *
     * @return
     */
    public boolean isCheckUp() {
        return true;
    }

    public boolean isPlayAnim() {
        return false;
    }

    public static Application get() {
        return sInstance;
    }

    public static boolean filter()
    {
        long time = System.currentTimeMillis();
        if ( ( time - lastClickTime ) > INTERVAL )
        {
            lastClickTime = time;
            return false;
        }
        return true;
    }
}
