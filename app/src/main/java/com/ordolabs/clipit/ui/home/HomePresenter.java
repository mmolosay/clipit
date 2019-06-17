package com.ordolabs.clipit.ui.home;

import android.support.v7.widget.Toolbar;

import com.ordolabs.clipit.R;
import com.ordolabs.clipit.ui.base.BasePresenter;

/**
 * Created by ordogod on 23.05.19.
 **/

class HomePresenter<V extends HomeActivity> extends BasePresenter<V> implements HomeMvpContract.Presenter<V> {

    private Toolbar toolbar;

    HomePresenter(V mvpView) {
        attachView(mvpView);
        initViews();
        prepareViews();
    }

    @Override
    protected void initViews() {
        toolbar = mvpView.findViewById(R.id.homeToolbar);
    }

    @Override
    protected void prepareViews() {
        toolbar.setTitle(R.string.homeToolbarTitle);
    }

    @Override
    public void attachView(V mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public boolean isViewAttached() {
        return super.isViewAttached();
    }

    @Override
    public V getAttachedView() {
        return super.getAttachedView();
    }
}
