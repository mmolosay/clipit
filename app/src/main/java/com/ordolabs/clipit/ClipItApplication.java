package com.ordolabs.clipit;

import android.app.Application;
import android.content.Context;

/**
 * Created by ordogod on 18.06.19.
 **/

public class ClipItApplication extends Application {

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return appContext;
    }
}
