package com.lilosoft.outsidescreen.base;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.lilosoft.outsidescreen.activity.StartActivity;

/**
 * Created by chablis on 2016/12/3.
 */

public class BootRestartReceiver extends BroadcastReceiver {
    private final String ACTION = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION)) ;
        {
            Intent intent2 = new Intent(context, StartActivity.class);
            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent2);
            Log.d("BootRestartReceiver", "开机自动服务自动启动...");
        }
    }
}
