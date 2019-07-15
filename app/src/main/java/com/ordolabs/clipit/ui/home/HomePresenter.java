package com.ordolabs.clipit.ui.home;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.ordolabs.clipit.R;
import com.ordolabs.clipit.data.service.ClipboardListenerService;
import com.ordolabs.clipit.data.model.home.HomeModel;
import com.ordolabs.clipit.ui.base.BasePresenter;
import com.ordolabs.clipit.ui.category.CategoryActivity;

/**
 * Created by ordogod on 23.05.19.
 **/

public class HomePresenter<V extends HomeActivity> extends BasePresenter<V> implements HomeMvpContract.Presenter<V> {

    private HomeModel<HomePresenter> mvpModel;

    private Toolbar toolbar;
    private LinearLayout noClipsContainer;
    private RecyclerView clipsRV;

    HomePresenter(V mvpView) {
        mvpView.startService(new Intent(mvpView, ClipboardListenerService.class));

        attachView(mvpView);

        initViews();
        prepareViews();
    }

    @Override
    protected void initViews() {
        toolbar = mvpView.findViewById(R.id.homeToolbar);
        noClipsContainer = mvpView.findViewById(R.id.homeNoClipsContainer);
        clipsRV = mvpView.findViewById(R.id.homeClipsRV);

        mvpModel = new HomeModel<HomePresenter>(this, clipsRV);
    }

    @Override
    protected void prepareViews() {
        mvpView.setSupportActionBar(toolbar);
        assert mvpView.getSupportActionBar() != null;

        mvpView.getSupportActionBar().setTitle(R.string.homeToolbarTitle);

        clipsRV.setLayoutManager(new LinearLayoutManager(mvpView));
        clipsRV.setAdapter(mvpModel.getRVadapter());
    }

    @Override
    public void updateView() {
        mvpModel.updateData();

        toggleNoClipsContainer();
    }

    @Override
    protected void animateActivityHiding() {

    }

    @Override
    protected void animateActivityShowing() {

    }

    public void toggleNoClipsContainer() {
        if (mvpModel.getClipsVisible() == 0) {
            noClipsContainer.setVisibility(View.VISIBLE);
        }
        else {
            noClipsContainer.setVisibility(View.GONE);
        }
    }

    void menuOnCategory() {
        mvpView.startActivity(CategoryActivity.getStartingIntent(mvpView));
    }
}
