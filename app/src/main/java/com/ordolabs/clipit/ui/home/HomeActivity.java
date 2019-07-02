package com.ordolabs.clipit.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ordolabs.clipit.ClipItApplication;
import com.ordolabs.clipit.R;
import com.ordolabs.clipit.ui.base.BaseActivity;

public class HomeActivity extends BaseActivity implements HomeMvpContract.View {

    private HomePresenter<HomeActivity> mvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mvpPresenter = new HomePresenter<>(this);
    }

    public static Intent getStartingIntent(Context callingContext) {
        if (callingContext == null) callingContext = ClipItApplication.getAppContext();
        return new Intent(callingContext, HomeActivity.class);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mvpPresenter.updateStates();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }
}
