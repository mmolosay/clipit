package com.ordolabs.clipit.ui.category;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ordolabs.clipit.R;
import com.ordolabs.clipit.data.model.CategoryModel;
import com.ordolabs.clipit.common.BasePresenter;

/**
 * Created by ordogod on 15.07.19.
 **/

public class CategoryPresenter<V extends CategoryActivity>
        extends BasePresenter<V> {

    private CategoryModel<CategoryPresenter> mvpModel;

    private ActionBar actionBar;
    private RecyclerView categoryRV; // TODO: replace RV with ListView

    CategoryPresenter(V mvpView) {
        attachView(mvpView);

        initViews();
        mvpModel = new CategoryModel<>(this, categoryRV);
        prepareViews();
    }

    @Override
    protected void initViews() {
        actionBar = mvpView.getSupportActionBar();

        categoryRV = mvpView.findViewById(R.id.categoryRV);
    }

    @Override
    protected void prepareViews() {
        if (actionBar != null) {
            actionBar.setTitle(R.string.categoryToolbarTitle);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        categoryRV.setLayoutManager(new LinearLayoutManager(mvpView));
        categoryRV.setAdapter(mvpModel.getRVadapter());
    }

    @Override
    public void update() {
        mvpModel.updateData();
        updateViews();
    }

    @Override
    public void updateViews() {
        //
    }

    void menuOnCategoryAdd() {

    }
}
