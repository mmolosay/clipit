package com.ordolabs.clipit.ui.clip;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.ordolabs.clipit.R;
import com.ordolabs.clipit.data.models.clip.ClipModel;
import com.ordolabs.clipit.ui.base.BasePresenter;

/**
 * Created by ordogod on 28.06.19.
 **/

public class ClipPresenter<V extends ClipActivity> extends BasePresenter<V> implements ClipMvpContract.Presenter<V> {

    private ClipModel<ClipPresenter> mvpModel;

    private Toolbar toolbar;
    private TextView titleTextView;
    private TextView bodyTextView;

    ClipPresenter(V mvpView, int clipPos) {
        attachView(mvpView);

        initViews();
        mvpModel = new ClipModel<ClipPresenter>(this, clipPos);
        prepareViews();
    }

    @Override
    protected void initViews() {
        toolbar = mvpView.findViewById(R.id.clipToolbar);
        titleTextView = mvpView.findViewById(R.id.clipTitleText);
        bodyTextView = mvpView.findViewById(R.id.clipBodyText);
    }

    @Override
    protected void prepareViews() {
        toolbar.setTitle(mvpModel.makeActivityTitle());
        titleTextView.setText(mvpModel.getClip().getTitle());
        bodyTextView.setText(mvpModel.getClip().getBody());
    }

    @Override
    protected void updateStates() {

    }
}
