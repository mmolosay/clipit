package com.ordolabs.clipit.ui.home;

import com.ordolabs.clipit.ui.base.BasePresenter;

/**
 * Created by ordogod on 23.05.19.
 **/

class HomePresenter<V extends HomeActivity> extends BasePresenter<V> implements HomeMvpContract.Presenter<V> {

    HomePresenter(V mvpView) {
        attachView(mvpView);
        initViews();
        prepareViews();
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void prepareViews() {

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
