package com.ordolabs.clipit.ui.base;

/**
 * Created by ordogod on 23.05.19.
 **/

public interface BaseMvpPresenter<V extends BaseMvpView> {

    void attachView(V mvpView);
    void detachView();

    boolean isViewAttached();
    V getAttachedView();
}
