package com.ordolabs.clipit.data.model;

import com.ordolabs.clipit.data.C;
import com.ordolabs.clipit.data.realm.RealmDealer;
import com.ordolabs.clipit.data.realm.object.ClipObject;
import com.ordolabs.clipit.common.BaseModel;
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
        this.clip = RealmDealer.getClipReversed(clipPos);
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
