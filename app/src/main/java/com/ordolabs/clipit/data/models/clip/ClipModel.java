package com.ordolabs.clipit.data.models.clip;

import com.ordolabs.clipit.data.db.RealmDealer;
import com.ordolabs.clipit.data.db.realm_objects.ClipObject;
import com.ordolabs.clipit.data.models.base.BaseModel;
import com.ordolabs.clipit.ui.clip.ClipPresenter;

/**
 * Created by ordogod on 03.07.19.
 **/

public class ClipModel<P extends ClipPresenter> extends BaseModel<P> implements ClipModelContract<P> {

    public ClipModel(P mvpPresenter) {
        attachPresenter(mvpPresenter);
    }

    public String makeTitle(final int itemPos) {
        ClipObject clip = RealmDealer.getClipAtPosReversed(itemPos);
        String title;

        if (clip.getTitle() == null)
            title = clip.getBody();
        else
            title = clip.getTitle();

        return title;
    }

    @Override
    public void updateData() {

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
