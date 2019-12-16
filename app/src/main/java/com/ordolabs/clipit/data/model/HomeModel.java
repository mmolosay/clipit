package com.ordolabs.clipit.data.model;

import com.ordolabs.clipit.data.realm.RealmDealer;
import com.ordolabs.clipit.data.realm.object.ClipObject;
import com.ordolabs.clipit.common.BaseModel;
import com.ordolabs.clipit.util.clipRV.Clip;
import com.ordolabs.clipit.ui.home.HomePresenter;

import java.util.ArrayList;
import java.util.Collections;

import io.realm.RealmResults;

/**
 * Created by ordogod on 17.06.19.
 **/

public class HomeModel<P extends HomePresenter> extends BaseModel<P> {

    public HomeModel(P mvpPresenter) {
        attachPresenter(mvpPresenter);
    }

    @Override
    public void updateData() {
        mvpPresenter.adapter.setClips(getClipsReversed());
    }

    public ArrayList<Clip> getClipsReversed() {
        RealmResults<ClipObject> results = RealmDealer.getClips();
        ArrayList<Clip> clips = new ArrayList<>();

        for (ClipObject item : results)
            if (!item.isRemoved())
                clips.add(new Clip(
                        item.getId(),
                        item.getText(),
                        item.getDateTime(),
                        item.isViewed(),
                        false
                ));

        Collections.reverse(clips);
        return clips;
    }
}
