package com.ordolabs.clipit.ui.edit;

import com.ordolabs.clipit.ui.base.BaseMvpPresenter;
import com.ordolabs.clipit.ui.base.BaseMvpView;

/**
 * Created by ordogod on 09.07.19.
 **/

public interface EditMvpContract {

    interface View extends BaseMvpView {

    }

    interface Presenter<V extends BaseMvpView> extends BaseMvpPresenter<V> {

    }
}
