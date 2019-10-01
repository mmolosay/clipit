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
    @NonNull private boolean isDefault;
    @NonNull private boolean isSelected;

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

    public boolean isDefault() { return isDefault; }

    public void setDefault(boolean isDefault) { this.isDefault = isDefault; }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
