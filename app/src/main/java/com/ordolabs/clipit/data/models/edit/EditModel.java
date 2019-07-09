package com.ordolabs.clipit.data.models.edit;

import com.ordolabs.clipit.data.db.RealmDealer;
import com.ordolabs.clipit.data.db.realm_objects.ClipObject;
import com.ordolabs.clipit.data.models.base.BaseModel;
import com.ordolabs.clipit.ui.edit.EditPresenter;

/**
 * Created by ordogod on 09.07.19.
 **/

public class EditModel<P extends EditPresenter> extends BaseModel<P> implements EditModelContract<P> {

    private int clipPos;
    private ClipObject clip;

    public EditModel(P mvpPresenter, int clipPos) {
        attachPresenter(mvpPresenter);

        this.clip = RealmDealer.getClipAtPos(clipPos, true);
        this.clipPos = clipPos;
    }

    public ClipObject getClip() {
        return clip;
    }

    @Override
    public void updateData() {

    }

    public int getClipPos() {
        return clipPos;
    }

    public void rewriteClip(String title, String body) {
        RealmDealer.rewriteClip(clipPos, title, body);
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
