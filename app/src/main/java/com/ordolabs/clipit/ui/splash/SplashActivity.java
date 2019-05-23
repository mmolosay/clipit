package com.ordolabs.clipit.ui.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ordolabs.clipit.ui.base.BaseActivity;
import com.ordolabs.clipit.ui.home.HomeActivity;

/**
 * Created by ordogod on 23.05.19.
 **/

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try { //TODO: remove sleep
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        startActivity(HomeActivity.getStartingIntent(this));
        finish();
    }
}
