package com.ordolabs.clipit.data.model.base;

import com.ordolabs.clipit.ui.base.BaseMvpPresenter;

/**
 * Created by ordogod on 19.06.19.
 **/

public interface BaseMvpModel<P extends BaseMvpPresenter> {

    void attachPresenter(P mvpPresenter);
    void detachPresenter();

    boolean isPresenterAttached();
    P getAttachedPresenter();
}
