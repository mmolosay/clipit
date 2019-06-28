package com.ordolabs.clipit.ui.clip;

import com.ordolabs.clipit.ui.base.BaseMvpPresenter;
import com.ordolabs.clipit.ui.base.BaseMvpView;

/**
 * Created by ordogod on 28.06.19.
 **/

public interface ClipMvpContract {
    interface View extends BaseMvpView {

    }

    interface Presenter<V extends BaseMvpView> extends BaseMvpPresenter<V> {

    }
}
