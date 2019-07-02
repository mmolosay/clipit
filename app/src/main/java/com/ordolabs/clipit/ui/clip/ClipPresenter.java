package com.ordolabs.clipit.ui.clip;

import android.support.v7.widget.Toolbar;

import com.ordolabs.clipit.R;
import com.ordolabs.clipit.ui.base.BasePresenter;

/**
 * Created by ordogod on 28.06.19.
 **/

public class ClipPresenter<V extends ClipActivity> extends BasePresenter<V> implements ClipMvpContract.Presenter<V> {

    int clipNumber;
    private Toolbar toolbar;

    ClipPresenter(V mvpView, int clipNumber) {
        this.clipNumber = clipNumber;

        attachView(mvpView);

        initViews();
        prepareViews();
    }

    @Override
    protected void initViews() {
        toolbar = mvpView.findViewById(R.id.clipToolbar);
    }

    @Override
    protected void prepareViews() {
        toolbar.setTitle("Clip first words");
    }

    @Override
    protected void updateStates() {

    }
}
