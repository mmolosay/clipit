package com.ordolabs.clipit.ui.clip;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;

import com.ordolabs.clipit.ClipItApplication;
import com.ordolabs.clipit.R;
import com.ordolabs.clipit.data.C;
import com.ordolabs.clipit.ui.base.BaseActivity;

/**
 * Created by ordogod on 28.06.19.
 **/

public class ClipActivity extends BaseActivity implements ClipMvpContract.View {

    private ClipPresenter<ClipActivity> mvpPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip);

        mvpPresenter = new ClipPresenter<>(
                this,
                getIntent().getIntExtra(C.EXTRA_CLICKED_CLIP_POSITION, -1)
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.clip_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuEdit) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
