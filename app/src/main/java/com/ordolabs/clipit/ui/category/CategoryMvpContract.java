package com.ordolabs.clipit.ui.category;

import com.ordolabs.clipit.ui.base.BaseMvpPresenter;
import com.ordolabs.clipit.ui.base.BaseMvpView;

/**
 * Created by ordogod on 15.07.19.
 **/

public interface CategoryMvpContract {

    interface View extends BaseMvpView {

    }

    interface Presenter<V extends BaseMvpView> extends BaseMvpPresenter<V> {

    }
}
