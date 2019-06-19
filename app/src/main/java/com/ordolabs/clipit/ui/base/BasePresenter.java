package com.ordolabs.clipit.ui.base;

/**
 * Created by ordogod on 23.05.19.
 **/

public abstract class BasePresenter<V extends BaseActivity> implements BaseMvpPresenter<V> {

    protected V mvpView;

    protected abstract void initViews();

    protected abstract void prepareViews();

    protected abstract void updateStates();

    @Override
    public void attachView(V mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void detachView() {
        this.mvpView = null;
    }

    @Override
    public boolean isViewAttached() {
        return this.mvpView != null;
    }

    @Override
    public V getAttachedView() {
        if (isViewAttached() == true) {
            return this.mvpView;
        }

        throw new MvpViewNotAttachedException();
    }

    private static class MvpViewNotAttachedException extends RuntimeException {
        private MvpViewNotAttachedException() {
            super("Please call \'attachView\' method before requesting data from the Presenter.");
        }
    }
}
