package com.ordolabs.clipit.ui.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;

import com.ordolabs.clipit.ClipItApplication;
import com.ordolabs.clipit.R;
import com.ordolabs.clipit.ui.base.BaseActivity;

/**
 * Created by ordogod on 15.07.19.
 **/

public class CategoryActivity extends BaseActivity implements CategoryMvpContract.View  {

    private CategoryPresenter<CategoryActivity> mvpPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        mvpPresenter = new CategoryPresenter<>(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.category_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menuCategoryAdd: {
                mvpPresenter.menuOnCategoryAdd();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public static Intent getStartingIntent(Context callingContext) {
        if (callingContext == null) callingContext = ClipItApplication.getAppContext();
        return new Intent(callingContext, CategoryActivity.class);
    }

    @Override
    protected void onResume() {
        mvpPresenter.updateView();

        super.onResume();
    }
}
