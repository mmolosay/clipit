package com.ordolabs.clipit.ui.home;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.ordolabs.clipit.R;
import com.ordolabs.clipit.data.ClipObject;
import com.ordolabs.clipit.data.ClipboardListenerService;
import com.ordolabs.clipit.data.HomeModel;
import com.ordolabs.clipit.data.RealmDealer;
import com.ordolabs.clipit.ui.base.BasePresenter;

/**
 * Created by ordogod on 23.05.19.
 **/

public class HomePresenter<V extends HomeActivity> extends BasePresenter<V> implements HomeMvpContract.Presenter<V> {

    private HomeModel mvpModel;

    private Toolbar toolbar;
    private LinearLayout noClipsContainer;

    HomePresenter(V mvpView) {
        mvpModel = new HomeModel(this);
        mvpView.startService(new Intent(mvpView, ClipboardListenerService.class));

        attachView(mvpView);
        initViews();
        prepareViews();
    }

    @Override
    protected void initViews() {
        toolbar = mvpView.findViewById(R.id.homeToolbar);
        noClipsContainer = mvpView.findViewById(R.id.homeNoClipsContainer);
    }

    @Override
    protected void prepareViews() {
        toolbar.setTitle(R.string.homeToolbarTitle);

        if (RealmDealer.getObjectsNumber(ClipObject.class) == 0) {
            noClipsContainer.setVisibility(View.VISIBLE);
        }
        else {
            noClipsContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public void attachView(V mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public boolean isViewAttached() {
        return super.isViewAttached();
    }

    @Override
    public V getAttachedView() {
        return super.getAttachedView();
    }
}
