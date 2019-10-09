package com.ordolabs.clipit.data.model;

import com.ordolabs.clipit.R;
import com.ordolabs.clipit.data.C;
import com.ordolabs.clipit.data.realm.RealmDealer;
import com.ordolabs.clipit.data.realm.object.ClipObject;
import com.ordolabs.clipit.common.BaseModel;
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
        this.clip = RealmDealer.getClipReversed(clipPos);
    }

    public String makeActivityTitle() {
        if (clip.getTitle() == null)
            return mvpPresenter.getView().getResources()
                    .getString(R.string.clipToolbarTitile);
        else
            return clip.getTitle();
    }

    public ClipObject getClip() {
        return clip;
    }

    @Override
    public void updateData() {
        this.clip = RealmDealer.getClipReversed(clipPos);
    }

    public int getClipPos() {
        return clipPos;
    }
}
