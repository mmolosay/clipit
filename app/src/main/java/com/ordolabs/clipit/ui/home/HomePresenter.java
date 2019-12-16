package com.ordolabs.clipit.ui.home;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ordolabs.clipit.R;
import com.ordolabs.clipit.util.ClipboardListenerService;
import com.ordolabs.clipit.data.model.HomeModel;
import com.ordolabs.clipit.common.Animatable;
import com.ordolabs.clipit.common.BasePresenter;
import com.ordolabs.clipit.util.clipRV.ClipAdapter;
import com.ordolabs.clipit.util.clipRV.ClipOffsetDecoration;

import java.util.Objects;

/**
 * Created by ordogod on 23.05.19.
 **/

public class HomePresenter<V extends HomeActivity> extends BasePresenter<V> implements Animatable {

    private HomeModel<HomePresenter> mvpModel;

    private ActionBar actionBar;
    private LinearLayout noClipsContainer;
    private RecyclerView clipRV;

    private Animation bumpUpShow;
    private Animation bumpUpHide;

    public ClipAdapter adapter;

    HomePresenter(V mvpView) {
        attachView(mvpView);
        ClipboardListenerService.start(mvpView);

        initViews();
        mvpModel = new HomeModel<>(this);
        initAnims();
        prepareViews();
    }

    @Override
    protected void initViews() {
        actionBar = mvpView.getSupportActionBar();

        noClipsContainer = mvpView.findViewById(R.id.homeNoClipsContainer);
        clipRV = mvpView.findViewById(R.id.homeClipsRV);
    }

    @Override
    public void initAnims() {
        bumpUpShow = AnimationUtils.loadAnimation(mvpView, R.anim.bump_show);
        bumpUpHide = AnimationUtils.loadAnimation(mvpView, R.anim.bump_hide);

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
        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
            actionBar.setCustomView(R.layout.action_bar);

            TextView title = actionBar.getCustomView().findViewById(R.id.actionBarTitle);
            title.setText(R.string.homeActionBarTitle);
        }

        prepareRecyclerView();
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
        if (adapter.getItemCount() == 0) {
            noClipsContainer.startAnimation(bumpUpShow);
        }
        else if (noClipsContainer.getVisibility() == View.VISIBLE) {
            noClipsContainer.startAnimation(bumpUpHide);
        }
    }

    private void prepareRecyclerView() {
        int rvItemsOffset = mvpView.getResources().getDimensionPixelOffset(R.dimen.content_margin_half);
        adapter = new ClipAdapter(mvpModel.getClipsReversed());

        clipRV.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        clipRV.addItemDecoration(new ClipOffsetDecoration(rvItemsOffset));
        clipRV.addItemDecoration(makeDividerDecoration());
        clipRV.setAdapter(adapter);
    }

    private DividerItemDecoration makeDividerDecoration() {
        DividerItemDecoration divider = new DividerItemDecoration(
                getView(),
                DividerItemDecoration.VERTICAL
        );
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(
                getView(),
                R.drawable.rv_divider_decoration
        )));
        return divider;
    }
}
