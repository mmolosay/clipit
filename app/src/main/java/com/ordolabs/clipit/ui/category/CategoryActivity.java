package com.ordolabs.clipit.ui.category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.ordolabs.clipit.R;

/**
 * Created by ordogod on 15.07.19.
 **/

public class CategoryActivity extends AppCompatActivity {

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

    @Override
    protected void onResume() {
        mvpPresenter.updateView();

        super.onResume();
    }
}
