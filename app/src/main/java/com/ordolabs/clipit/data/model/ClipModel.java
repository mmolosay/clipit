package com.ordolabs.clipit.data.model;

import com.ordolabs.clipit.data.C;
import com.ordolabs.clipit.data.realm.RealmDealer;
import com.ordolabs.clipit.data.realm.object.ClipObject;
import com.ordolabs.clipit.common.BaseModel;
import com.ordolabs.clipit.ui.clip.ClipPresenter;

/**
 * Created by ordogod on 03.07.19.
 **/

public class ClipModel<P extends ClipPresenter> extends BaseModel<P> {

    private int clipID;
    private ClipObject clip;

    public ClipModel(P mvpPresenter) {
        attachPresenter(mvpPresenter);

        this.clipID = mvpPresenter
                .getView().getIntent()
                .getIntExtra(C.EXTRA_CLIP_ID, -1);
        this.clip = RealmDealer.getClip(clipID);
    }

    @Override
    public void updateData() {
        this.clip = RealmDealer.getClip(clipID);
    }

    public ClipObject getClip() {
        return clip;
    }


    public int getClipID() {
        return clipID;
    }
}
