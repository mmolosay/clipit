package com.ordolabs.clipit.ui.home;

import android.os.Bundle;

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
}
