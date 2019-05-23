package com.ordolabs.clipit.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ordogod on 23.05.19.
 **/

public abstract class BaseActivity extends AppCompatActivity implements BaseMvpView {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Intent getStartingIntent(@NonNull Context callingContext) {
        return new Intent(callingContext, this.getClass());
    }
}
