package com.ordolabs.clipit.data;

import android.support.annotation.NonNull;

import javax.annotation.Nullable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ordogod on 18.06.19.
 **/

public class ClipObject extends RealmObject {
    @PrimaryKey private int id;
    @Nullable private String title;
    @NonNull private String body;

    public int getId() {
        return id;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @NonNull
    public String getBody() {
        return body;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
