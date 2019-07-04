package com.ordolabs.clipit;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.ordolabs.clipit.data.C;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ordogod on 18.06.19.
 **/

public class ClipItApplication extends Application {

    private static Context appContext;

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();

        C.CURRENT_DATETIME = new SimpleDateFormat("d MMM HH:mm yyyy").format(new Date());
    }

    public static Context getAppContext() {
        return appContext;
    }
}
