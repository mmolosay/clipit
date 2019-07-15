package com.ordolabs.clipit.data.model.base;

import com.ordolabs.clipit.ui.base.BasePresenter;

/**
 * Created by ordogod on 028 28.06.19.
 **/

public abstract class BaseModel<P extends BasePresenter> implements BaseMvpModel<P> {

    private P mvpPresenter;

    public abstract void updateData();

    @Override
    public void attachPresenter(P mvpPresenter) {
        this.mvpPresenter = mvpPresenter;
    }

    @Override
    public void detachPresenter() {
        this.mvpPresenter = null;
    }

    @Override
    public boolean isPresenterAttached() {
        return this.mvpPresenter != null;
    }

    @Override
    public P getAttachedPresenter() {
        if (isPresenterAttached() == true) {
            return this.mvpPresenter;
        }

        throw new MvpPresenterNotAttachedException();
    }

    private static class MvpPresenterNotAttachedException extends RuntimeException {
        private MvpPresenterNotAttachedException() {
            super("Please call \'attachPresenter\' method before requesting data from the Model.");
        }
    }
}
