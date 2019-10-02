package com.ordolabs.clipit.data.model;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.ordolabs.clipit.data.db.RealmDealer;
import com.ordolabs.clipit.data.db.realm_objects.ClipObject;
import com.ordolabs.clipit.generic.BaseModel;
import com.ordolabs.clipit.util.clipRV.ClipRaw;
import com.ordolabs.clipit.util.clipRV.ClipRVadapter;
import com.ordolabs.clipit.util.clipRV.ClipRVswipeController;
import com.ordolabs.clipit.ui.home.HomePresenter;

import java.util.ArrayList;
import java.util.Collections;

import io.realm.RealmResults;

/**
 * Created by ordogod on 17.06.19.
 **/

public class HomeModel<P extends HomePresenter> extends BaseModel<P> {

    private ClipRVadapter adapter;
    private ClipRVswipeController swipeController;

    public HomeModel(P mvpPresenter, RecyclerView rv) {
        attachPresenter(mvpPresenter);

        this.adapter = new ClipRVadapter(
                getClipsReversed(),
                mvpPresenter.getView(),
                rv
        );
        this.swipeController = new ClipRVswipeController(adapter, this);

        new ItemTouchHelper(swipeController).attachToRecyclerView(rv);
    }

    @Override
    public void updateData() {
        adapter.setClips(getClipsReversed());
    }

    public int getClipsCount() { return  adapter.getClipsCount(); }

    private ArrayList<ClipRaw> getClipsReversed() {
        RealmResults<ClipObject> res = RealmDealer.getClips();
        ArrayList<ClipRaw> clips = new ArrayList<>();

        for (ClipObject item : res)
            if (!item.isRemoved())
                clips.add(new ClipRaw(
                        item.getTitle(),
                        item.getBody(),
                        item.getDateTime(),
                        item.isViewed(),
                        false
                ));

        Collections.reverse(clips);
        return clips;
    }

    public ClipRVadapter getRVadapter() { return adapter; }
}
