package com.ordolabs.clipit.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

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

    public static Intent getStartingIntent(@NonNull Context callingContext) {
        return new Intent(callingContext, HomeActivity.class);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mvpPresenter.updateStates();
    }
}
