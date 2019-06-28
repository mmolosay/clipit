package com.ordolabs.clipit.data.models;

import com.ordolabs.clipit.ui.base.BaseMvpPresenter;

/**
 * Created by ordogod on 19.06.19.
 **/

interface BaseMvpModel<P extends BaseMvpPresenter> {

    void attachPresenter(P mvpPresenter);
    void detachPresenter();

    boolean isPresenterAttached();
    P getAttachedPresenter();
}
