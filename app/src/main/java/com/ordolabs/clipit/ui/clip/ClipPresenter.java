package com.ordolabs.clipit.ui.clip;

import android.support.v7.widget.Toolbar;

import com.ordolabs.clipit.R;
import com.ordolabs.clipit.data.models.clip.ClipModel;
import com.ordolabs.clipit.ui.base.BasePresenter;

/**
 * Created by ordogod on 28.06.19.
 **/

public class ClipPresenter<V extends ClipActivity> extends BasePresenter<V> implements ClipMvpContract.Presenter<V> {

    private ClipModel<ClipPresenter> mvpModel;
    private int clipPos;

    private Toolbar toolbar;

    ClipPresenter(V mvpView, int clipPos) {
        this.clipPos = clipPos;

        attachView(mvpView);

        initViews();
        mvpModel = new ClipModel<ClipPresenter>(this);
        prepareViews();
    }

    @Override
    protected void initViews() {
        toolbar = mvpView.findViewById(R.id.clipToolbar);
    }

    @Override
    protected void prepareViews() {
        toolbar.setTitle(mvpModel.makeTitle(clipPos));
    }

    @Override
    protected void updateStates() {

    }
}
