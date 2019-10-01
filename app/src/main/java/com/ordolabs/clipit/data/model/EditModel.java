package com.ordolabs.clipit.data.model;

import com.ordolabs.clipit.data.C;
import com.ordolabs.clipit.data.db.RealmDealer;
import com.ordolabs.clipit.data.db.realm_objects.ClipObject;
import com.ordolabs.clipit.generic.BaseModel;
import com.ordolabs.clipit.ui.edit.EditPresenter;

/**
 * Created by ordogod on 09.07.19.
 **/

public class EditModel<P extends EditPresenter> extends BaseModel<P> {

    private int clipPos;
    private ClipObject clip;

    public EditModel(P mvpPresenter) {
        attachPresenter(mvpPresenter);

        this.clipPos = mvpPresenter
                .getView().getIntent()
                .getIntExtra(C.EXTRA_CLIP_POSITION, -1);
        this.clip = RealmDealer.getClipAtPosReversed(clipPos);
    }

    public ClipObject getClip() {
        return clip;
    }

    @Override
    public void updateData() {
        //
    }

    public void rewriteClip(String title, String body) {
        RealmDealer.editClip(clipPos, title, body);
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
