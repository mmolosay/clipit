package com.ordolabs.clipit.data.models.home;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.ordolabs.clipit.data.C;
import com.ordolabs.clipit.data.db.RealmDealer;
import com.ordolabs.clipit.data.db.realm_objects.ClipObject;
import com.ordolabs.clipit.data.models.base.BaseModel;
import com.ordolabs.clipit.data.utils.rv.ClipRaw;
import com.ordolabs.clipit.data.utils.rv.RVadapter;
import com.ordolabs.clipit.data.utils.rv.RVswipeController;
import com.ordolabs.clipit.ui.home.HomePresenter;

import java.util.ArrayList;
import java.util.Collections;

import io.realm.RealmResults;

/**
 * Created by ordogod on 17.06.19.
 **/

public class HomeModel<P extends HomePresenter> extends BaseModel<P> implements HomeModelContract<P> {

    private RVadapter adapter;
    private RVswipeController swipeController;
    private int clipsVisible;

    public HomeModel(P mvpPresenter, RecyclerView rv) {
        attachPresenter(mvpPresenter);

        this.clipsVisible = RealmDealer.getClipsCount();
        this.adapter = new RVadapter(
                getRawClipsListReversed(),
                mvpPresenter.getAttachedView(),
                rv
        );
        this.swipeController = new RVswipeController(adapter, this);

        new ItemTouchHelper(swipeController).attachToRecyclerView(rv);
    }

    @Override
    public void updateData() {
        C.updateData();
        adapter.setClipsList(getRawClipsListReversed());
        this.clipsVisible = adapter.getItemCount();
    }

    private ArrayList<ClipRaw> getRawClipsListReversed() {
        int clipsCount = RealmDealer.getClipsCount();
        if (clipsCount == 0) return new ArrayList<>();

        ArrayList<ClipRaw> list = new ArrayList<>();
        RealmResults<ClipObject> results = RealmDealer.getAllClips();

        for (int i = 0; i < clipsCount; i++) {
            list.add(new ClipRaw(
                    results.get(i).getTitle(),
                    results.get(i).getBody(),
                    results.get(i).getDateTime(),
                    results.get(i).isViewed()
            ));
        }

        Collections.reverse(list);
        return list;
    }

    public RVadapter getRVadapter() {
        return adapter;
    }

    public int getClipsVisible() {
        return clipsVisible;
    }

    public void increaseClipsVisibleBy(int increment) {
        if (clipsVisible + increment < 0)
            throw new IllegalArgumentException("Final count of visible clips can not be less than zero.");

        this.clipsVisible += increment;
        getAttachedPresenter().toggleNoClipsContainer();
    }

    @Override
    public void attachPresenter(P mvpPresenter) {
        super.attachPresenter(mvpPresenter);
    }

    @Override
    public void detachPresenter() {
        super.detachPresenter();
    }

    @Override
    public boolean isPresenterAttached() {
        return super.isPresenterAttached();
    }

    @Override
    public P getAttachedPresenter() {
        return super.getAttachedPresenter();
    }
}
