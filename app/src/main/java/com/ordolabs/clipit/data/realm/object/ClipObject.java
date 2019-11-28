package com.ordolabs.clipit.data.realm.object;

import android.support.annotation.NonNull;

import javax.annotation.Nullable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ordogod on 18.06.19.
 **/

public class ClipObject extends RealmObject {

    @PrimaryKey private int     id;
    @NonNull    private String  text;
    @NonNull    private String  date;
                private boolean isViewed;
                private boolean isRemoved;

    public ClipObject init(
            String text,
            String datetime,
            boolean isViewed,
            boolean isRemoved)
    {
        this.text = text;
        this.date = datetime;
        this.isViewed = isViewed;
        this.isRemoved = isRemoved;

        return this;
    }

              public int getId() { return id; }
    @NonNull  public String getText() { return text; }
    @NonNull  public String getDateTime() { return date; }
              public boolean isRemoved() { return isRemoved; }
              public boolean isViewed() { return isViewed; }

    public void setText(@NonNull String text) { this.text = text; }
    public void setViewed(boolean viewed) { isViewed = viewed; }
    public void setRemoved(boolean removed) { isRemoved = removed; }

    // no date and id setters – should not
    // be editied – set in init() only
}
