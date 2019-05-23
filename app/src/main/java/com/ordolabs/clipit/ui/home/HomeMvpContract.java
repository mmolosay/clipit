package com.ordolabs.clipit.ui.home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.ordolabs.clipit.ui.base.BaseMvpPresenter;
import com.ordolabs.clipit.ui.base.BaseMvpView;

/**
 * Created by ordogod on 23.05.19.
 **/

interface HomeMvpContract {
    interface View extends BaseMvpView {
        @Override
        Intent getStartingIntent(@NonNull Context callingContext);
    }

    interface Presenter<V extends BaseMvpView> extends BaseMvpPresenter<V> {

        @Override
        void attachView(V mvpView);

        @Override
        void detachView();

        @Override
        boolean isViewAttached();

        @Override
        V getAttachedView();
    }
}
