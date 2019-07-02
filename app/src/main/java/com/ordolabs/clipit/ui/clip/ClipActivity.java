package com.ordolabs.clipit.ui.clip;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ordolabs.clipit.ClipItApplication;
import com.ordolabs.clipit.R;
import com.ordolabs.clipit.ui.base.BaseActivity;

/**
 * Created by ordogod on 28.06.19.
 **/

public class ClipActivity extends BaseActivity implements ClipMvpContract.View {

    private ClipPresenter<ClipActivity> mvpPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acrivity_clip);

        mvpPresenter = new ClipPresenter<>(this);
    }

    public static Intent getStartingIntent(Context callingContext) {
        if (callingContext == null) callingContext = ClipItApplication.getAppContext();
        return new Intent(callingContext, ClipActivity.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mvpPresenter.updateStates();
    }
}
