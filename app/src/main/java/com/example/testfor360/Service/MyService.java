package com.example.testfor360.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.testfor360.Managers.FloatViewManager;

/**
 * Created by 蔡大爷 on 2016/8/17.
 */
public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.e("TestFor360", "###onCreate###");
        FloatViewManager floatViewManager = FloatViewManager.getInstace(this);
        floatViewManager.showFloatCricleView();

        Log.e("TestFor360", "###onCreate11###");
    }
}
