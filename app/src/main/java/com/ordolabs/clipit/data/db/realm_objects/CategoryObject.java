package com.ordolabs.clipit.data.db.realm_objects;

import android.support.annotation.NonNull;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ordogod on 15.07.19.
 **/

public class CategoryObject extends RealmObject {

    @PrimaryKey private int id;
    @NonNull private String name;
    @NonNull private boolean isRemovable;
    @NonNull private boolean isActive;

    public int getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public boolean isRemovable() {
        return isRemovable;
    }

    public void setRemovable(boolean removable) {
        isRemovable = removable;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
