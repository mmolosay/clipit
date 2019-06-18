package com.ordolabs.clipit.ui.home;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.ordolabs.clipit.R;
import com.ordolabs.clipit.data.db.realm_objects.ClipObject;
import com.ordolabs.clipit.data.service.ClipboardListenerService;
import com.ordolabs.clipit.data.models.HomeModel;
import com.ordolabs.clipit.data.db.RealmDealer;
import com.ordolabs.clipit.data.utils.rv.RecyclerViewAdapter;
import com.ordolabs.clipit.ui.base.BasePresenter;

/**
 * Created by ordogod on 23.05.19.
 **/

public class HomePresenter<V extends HomeActivity> extends BasePresenter<V> implements HomeMvpContract.Presenter<V> {

    private HomeModel mvpModel;

    private Toolbar toolbar;
    private LinearLayout noClipsContainer;
    private RecyclerView clipsRV;

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
        clipsRV = mvpView.findViewById(R.id.homeClipsRV);
    }

    @Override
    protected void prepareViews() {
        toolbar.setTitle(R.string.homeToolbarTitle);

        clipsRV.setLayoutManager(new LinearLayoutManager(mvpView));
        clipsRV.setAdapter(new RecyclerViewAdapter(mvpModel.getRawClipsList()));
    }

    void toggleNoClipsContainer() {
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
