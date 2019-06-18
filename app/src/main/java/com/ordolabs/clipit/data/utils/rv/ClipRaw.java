package com.ordolabs.clipit.data.utils.rv;

import javax.annotation.Nullable;

/**
 * Created by ordogod on 18.06.19.
 **/

public class ClipRaw {

    String title;
    String body;

    public ClipRaw(String title, String body) {
        this.title = title;
        this.body = body;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) return false;
        if (obj instanceof ClipRaw) {
            return ((ClipRaw) obj).title.equals(this.title) &&
                    ((ClipRaw) obj).body.equals(this.body);
        }
        return false;
    }
}
