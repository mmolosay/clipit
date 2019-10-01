package com.ordolabs.clipit;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * Created by ordogod on 18.06.19.
 **/

public class App extends Application {

    private volatile static Context context;

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
    }

    public static Context getContext() { return context; }
}
