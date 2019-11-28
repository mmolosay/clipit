package com.ordolabs.clipit.util.clipRV;

import javax.annotation.Nullable;

/**
 * Created by ordogod on 18.06.19.
 **/

public class Clip {

    int     id;
    String text;
    String  date;
    boolean isViewed;
    boolean isRemoved;

    public Clip(int id, String text, String date, boolean isViewed, boolean isRemoved) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.isViewed = isViewed;
        this.isRemoved = isRemoved;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) return false;
        if (obj instanceof Clip) {
            return ((Clip) obj).text.equals(this.text);
        }
        return false;
    }
}
