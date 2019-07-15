package com.ordolabs.clipit.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menuCategory: {
                mvpPresenter.menuOnCategory();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public static Intent getStartingIntent(Context callingContext) {
        if (callingContext == null) callingContext = ClipItApplication.getAppContext();
        return new Intent(callingContext, HomeActivity.class);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mvpPresenter.updateView();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
