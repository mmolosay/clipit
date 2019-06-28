package com.ordolabs.clipit.ui.home;

import com.ordolabs.clipit.ui.base.BaseMvpPresenter;
import com.ordolabs.clipit.ui.base.BaseMvpView;

/**
 * Created by ordogod on 23.05.19.
 **/

public interface HomeMvpContract {
    interface View extends BaseMvpView {
    }

    interface Presenter<V extends BaseMvpView> extends BaseMvpPresenter<V> {

    }
}
