package com.lilosoft.outsidescreen.base;

/**
 * Created by chablis on 2016/11/7.
 */

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.lilosoft.outsidescreen.bean.Userinfo;

public class AppContext extends Application {

    private static Application sInstance;
    public Userinfo user;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Fresco.initialize(this);
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

}
