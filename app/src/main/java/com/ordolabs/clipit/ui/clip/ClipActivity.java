package com.ordolabs.clipit.ui.clip;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.ordolabs.clipit.R;

/**
 * Created by ordogod on 28.06.19.
 **/

public class ClipActivity extends AppCompatActivity {

    private ClipPresenter<ClipActivity> mvpPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip);

        mvpPresenter = new ClipPresenter<>(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.clip_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menuEdit: {
                mvpPresenter.onMenuEdit(this);
                break;
            }
            case R.id.menuCopy: {
                mvpPresenter.onMenuCopy(this);
                break;
            }
            case R.id.menuDelete: {
                mvpPresenter.onMenuDelete(this);
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mvpPresenter.update();
    }
}
