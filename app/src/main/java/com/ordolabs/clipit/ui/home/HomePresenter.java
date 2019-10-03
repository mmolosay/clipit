package com.ordolabs.clipit.ui.home;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.ordolabs.clipit.R;
import com.ordolabs.clipit.data.service.ClipboardListenerService;
import com.ordolabs.clipit.data.model.HomeModel;
import com.ordolabs.clipit.generic.Animatable;
import com.ordolabs.clipit.generic.BasePresenter;
import com.ordolabs.clipit.ui.category.CategoryActivity;

/**
 * Created by ordogod on 23.05.19.
 **/

public class HomePresenter<V extends HomeActivity>
        extends BasePresenter<V> implements Animatable {

    private HomeModel<HomePresenter> mvpModel;

    private Toolbar toolbar;
    private LinearLayout noClipsContainer;
    private RecyclerView clipsRV;

    private Animation bumpUpShow;
    private Animation bumpUpHide;

    HomePresenter(V mvpView) {
        attachView(mvpView);
        ClipboardListenerService.start(mvpView);

        initViews();
        mvpModel = new HomeModel<>(this, clipsRV);
        initAnims();
        prepareViews();
    }

    @Override
    protected void initViews() {
        toolbar = mvpView.findViewById(R.id.homeToolbar);
        noClipsContainer = mvpView.findViewById(R.id.homeNoClipsContainer);
        clipsRV = mvpView.findViewById(R.id.homeClipsRV);
    }

    @Override
    public void initAnims() {
        bumpUpShow = AnimationUtils.loadAnimation(mvpView, R.anim.bump_up_show);
        bumpUpHide = AnimationUtils.loadAnimation(mvpView, R.anim.bump_up_hide);

        bumpUpShow.setAnimationListener(new Animation.AnimationListener() {
            @Override public void onAnimationStart(Animation animation) {
                noClipsContainer.setVisibility(View.VISIBLE);
            }
            @Override public void onAnimationEnd(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}
        });
        bumpUpHide.setAnimationListener(new Animation.AnimationListener() {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation) {
                noClipsContainer.setVisibility(View.GONE);
            }
            @Override public void onAnimationRepeat(Animation animation) {}
        });
    }

    @Override
    protected void prepareViews() {
        mvpView.setSupportActionBar(toolbar);
        assert mvpView.getSupportActionBar() != null;

        mvpView.getSupportActionBar().setTitle(R.string.homeToolbarTitle);

        clipsRV.setLayoutManager(new LinearLayoutManager(mvpView));
        clipsRV.setAdapter(mvpModel.getRVadapter());
    }

    @Override
    public void update() {
        mvpModel.updateData();
        updateViews();
    }

    @Override
    public void updateViews() {
        toggleNoClipsContainer();
    }

    private void toggleNoClipsContainer() {
        if (mvpModel.getClipsCount() == 0) {
            noClipsContainer.startAnimation(bumpUpShow);
        }
        else if (noClipsContainer.getVisibility() == View.VISIBLE) {
            noClipsContainer.startAnimation(bumpUpHide);
        }
    }

    void menuOnCategory() {
        mvpView.startActivity(new Intent(mvpView, CategoryActivity.class));
    }
}
