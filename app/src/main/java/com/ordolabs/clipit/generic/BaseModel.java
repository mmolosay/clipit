package com.ordolabs.clipit.generic;

/**
 * Created by ordogod on 028 28.06.19.
 **/

public abstract class BaseModel<P extends BasePresenter> {

    private P mvpPresenter;

    protected abstract void updateData();

    public void attachPresenter(P mvpPresenter) {
        this.mvpPresenter = mvpPresenter;
    }

    public P getPresenter() {
        if (mvpPresenter != null) {
            return this.mvpPresenter;
        }

        throw new MvpPresenterNotAttachedException();
    }

    private static class MvpPresenterNotAttachedException extends RuntimeException {
        private MvpPresenterNotAttachedException() {
            super(
                "MVP Presenter is not attached." +
                "Please call \'attachPresenter\' method before requesting data from the Model."
            );
        }
    }
}
