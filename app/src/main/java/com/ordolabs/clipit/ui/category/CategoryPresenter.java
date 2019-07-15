package com.ordolabs.clipit.ui.category;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ordolabs.clipit.R;
import com.ordolabs.clipit.data.model.category.CategoryModel;
import com.ordolabs.clipit.ui.base.BasePresenter;

/**
 * Created by ordogod on 15.07.19.
 **/

public class CategoryPresenter<V extends CategoryActivity> extends BasePresenter<V> implements CategoryMvpContract.Presenter<V> {

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

        mvpModel = new CategoryModel<CategoryPresenter>(this, categoryRV);
    }

    @Override
    protected void prepareViews() {
        mvpView.setSupportActionBar(toolbar);
        assert (mvpView.getSupportActionBar() != null);
        mvpView.getSupportActionBar().setTitle(R.string.categoryToolbarTitle);
        toolbar.setNavigationIcon(mvpView.getResources().getDrawable(R.drawable.ic_arrow_back_light_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpView.finish();
            }
        });

        categoryRV.setLayoutManager(new LinearLayoutManager(mvpView));
        categoryRV.setAdapter(mvpModel.getRVadapter());
    }

    @Override
    public void updateView() {
        mvpModel.updateData();
    }

    @Override
    protected void animateActivityHiding() {

    }

    @Override
    protected void animateActivityShowing() {

    }

    void menuOnCategoryAdd() {

    }
}
