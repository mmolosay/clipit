package com.ordolabs.clipit.ui.edit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;

import com.ordolabs.clipit.ClipItApplication;
import com.ordolabs.clipit.R;
import com.ordolabs.clipit.data.C;
import com.ordolabs.clipit.ui.base.BaseActivity;

/**
 * Created by ordogod on 09.07.19.
 **/

public class EditActivity extends BaseActivity implements EditMvpContract.View {

    private EditPresenter<EditActivity> mvpPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        mvpPresenter = new EditPresenter<>(
                this,
                getIntent().getIntExtra(C.EXTRA_CLIP_POSITION, -1)
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        switch (id) {
//            case R.id.menuEdit: {
//                break;
//            }
//            case R.id.menuCopy: {
//                mvpPresenter.menuOnCopy(this);
//                break;
//            }
//            case R.id.menuDelete: {
//                mvpPresenter.menuOnDelete(this);
//                break;
//            }
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    public static Intent getStartingIntent(Context callingContext) {
        if (callingContext == null) callingContext = ClipItApplication.getAppContext();
        return new Intent(callingContext, EditActivity.class);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mvpPresenter.updateView();
    }
}
