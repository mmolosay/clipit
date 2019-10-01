package com.ordolabs.clipit.generic;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by ordogod on 23.05.19.
 **/

public abstract class BasePresenter<V extends AppCompatActivity> {

    protected V mvpView;

    protected abstract void initViews();

    protected abstract void prepareViews();

    public abstract void update();

    public abstract void updateViews();

    protected void attachView(V mvpView) {
        this.mvpView = mvpView;
    }

    public V getView() {
        if (mvpView != null) {
            return this.mvpView;
        }
        else throw new MvpViewNotAttachedException();
    }

    private static class MvpViewNotAttachedException extends RuntimeException {
        private MvpViewNotAttachedException() {
            super(
                "MVP View is not attached. " +
                "Please call \'attachView\' method before requesting data from the Presenter."
            );
        }
    }
}
