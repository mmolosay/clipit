package com.ordolabs.clipit.data.models;

import com.ordolabs.clipit.data.db.RealmDealer;
import com.ordolabs.clipit.data.db.realm_objects.ClipObject;
import com.ordolabs.clipit.data.utils.rv.ClipRaw;
import com.ordolabs.clipit.data.utils.rv.RecyclerViewAdapter;
import com.ordolabs.clipit.ui.home.HomePresenter;

import java.util.ArrayList;
import java.util.Collections;

import io.realm.RealmResults;

/**
 * Created by ordogod on 17.06.19.
 **/

public class HomeModel implements BaseMvpModel {

    private HomePresenter mvpPresenter;
    private RecyclerViewAdapter clipsRVadapter;

    public HomeModel(HomePresenter mvpPresenter) {
        this.mvpPresenter = mvpPresenter;

        clipsRVadapter = new RecyclerViewAdapter(getRawClipsList());
//        RealmDealer.dropAllObjects(ClipObject.class);
    }

    @Override
    public void updateData() {
        clipsRVadapter.setClipsList(getRawClipsList());
    }

    private ArrayList<ClipRaw> getRawClipsList() {
        int clipsCount = RealmDealer.getObjectsNumber(ClipObject.class);
        if (clipsCount == 0) return new ArrayList<>();

        ArrayList<ClipRaw> list = new ArrayList<>();
        RealmResults<ClipObject> results = RealmDealer.getAllClips();

        for (int i = 0; i < clipsCount; i++) {
            list.add(new ClipRaw(
                    null,
                    results.get(i).getBody())
            );
        }

        Collections.reverse(list);
        return list;
    }

    public RecyclerViewAdapter getClipsRVadapter() {
        return clipsRVadapter;
    }
}
