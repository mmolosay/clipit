package com.ordolabs.clipit.data.model;

import com.ordolabs.clipit.data.C;
import com.ordolabs.clipit.data.db.RealmDealer;
import com.ordolabs.clipit.data.db.realm_objects.ClipObject;
import com.ordolabs.clipit.generic.BaseModel;
import com.ordolabs.clipit.ui.clip.ClipPresenter;

/**
 * Created by ordogod on 03.07.19.
 **/

public class ClipModel<P extends ClipPresenter> extends BaseModel<P> {

    private int clipPos;
    private ClipObject clip;

    public ClipModel(P mvpPresenter) {
        attachPresenter(mvpPresenter);

        this.clipPos = mvpPresenter
                .getView().getIntent()
                .getIntExtra(C.EXTRA_CLIP_POSITION, -1);
        this.clip = RealmDealer.getClipAtPos(clipPos, true);
    }

    public String makeActivityTitle() {
        if (clip.getTitle() == null)
            return clip.getBody();
        else
            return clip.getTitle();
    }

    public ClipObject getClip() {
        return clip;
    }

    @Override
    public void updateData() {
        this.clip = RealmDealer.getClipAtPos(clipPos, true);
    }

    public int getClipPos() {
        return clipPos;
    }

    @Override
    public void attachPresenter(P mvpPresenter) {
        super.attachPresenter(mvpPresenter);
    }

    @Override
    public P getPresenter() {
        return super.getPresenter();
    }
}