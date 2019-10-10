package com.ordolabs.clipit.util.clipRV;

import javax.annotation.Nullable;

/**
 * Created by ordogod on 18.06.19.
 **/

public class ClipRaw {

    int id;
    String title;
    String body;
    String datetime;
    boolean isViewed;
    boolean isRemoved;

    public ClipRaw(int id, String title, String body, String datetime, boolean isViewed, boolean isRemoved) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.datetime = datetime;
        this.isViewed = isViewed;
        this.isRemoved = isRemoved;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) return false;
        if (obj instanceof ClipRaw) {
            return ((ClipRaw) obj).body.equals(this.body);
        }
        return false;
    }
}
