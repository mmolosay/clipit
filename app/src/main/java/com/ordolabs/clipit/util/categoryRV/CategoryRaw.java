package com.ordolabs.clipit.util.categoryRV;

/**
 * Created by ordogod on 15.07.19.
 **/

public class CategoryRaw {

    String name;
    boolean isRemovable;
    boolean isActive;

    public CategoryRaw(String name, boolean isRemovable, boolean isActive) {
        this.name = name;
        this.isRemovable = isRemovable;
        this.isActive = isActive;
    }
}
