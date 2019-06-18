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

public class HomeModel {

    private HomePresenter mvpPresenter;

    public HomeModel(HomePresenter mvpPresenter) {
        this.mvpPresenter = mvpPresenter;

//        RealmDealer.dropAllObjects(ClipObject.class);
    }

    public ArrayList<ClipRaw> getRawClipsList() {
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

    public void updateClipsList(RecyclerViewAdapter adapter) {
        adapter.getClipsList().clear();
        adapter.getClipsList().addAll(getRawClipsList());
        adapter.notifyDataSetChanged();
    }
}
