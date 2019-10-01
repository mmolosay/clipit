package com.ordolabs.clipit.ui.category;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.ordolabs.clipit.R;
import com.ordolabs.clipit.data.model.CategoryModel;
import com.ordolabs.clipit.generic.AdvancedToolbar;
import com.ordolabs.clipit.generic.BasePresenter;

/**
 * Created by ordogod on 15.07.19.
 **/

public class CategoryPresenter<V extends CategoryActivity>
        extends BasePresenter<V> implements AdvancedToolbar {

    private CategoryModel<CategoryPresenter> mvpModel;
    private Toolbar toolbar;
    private RecyclerView categoryRV;

    CategoryPresenter(V mvpView) {
        attachView(mvpView);

        initViews();
        prepareViews();
    }

    @Override
    protected void initViews() {
        toolbar = mvpView.findViewById(R.id.categoryToolbar);
        categoryRV = mvpView.findViewById(R.id.categoryRV);

        mvpModel = new CategoryModel<>(this, categoryRV);
    }

    @Override
    protected void prepareViews() {
        AdvancedToolbar.prepareToolbar(mvpView, toolbar);
        toolbar.setNavigationOnClickListener(v -> mvpView.finish());

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
