package com.ordolabs.clipit.data.models.clip;

import com.ordolabs.clipit.data.db.RealmDealer;
import com.ordolabs.clipit.data.db.realm_objects.ClipObject;
import com.ordolabs.clipit.data.models.base.BaseModel;
import com.ordolabs.clipit.ui.clip.ClipPresenter;

/**
 * Created by ordogod on 03.07.19.
 **/

public class ClipModel<P extends ClipPresenter> extends BaseModel<P> implements ClipModelContract<P> {

    private int clipPos;
    private ClipObject clip;

    public ClipModel(P mvpPresenter, int clipPos) {
        attachPresenter(mvpPresenter);

        this.clip = RealmDealer.getClipAtPos(clipPos, true);
        this.clipPos = clipPos;
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

    }

    public int getClipPos() {
        return clipPos;
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
