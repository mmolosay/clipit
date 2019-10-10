package com.ordolabs.clipit.util.categoryRV;

/**
 * Created by ordogod on 15.07.19.
 **/

public class CategoryRaw {

    String name;
    boolean isDefault;
    boolean isSelected;

    public CategoryRaw(String name, boolean isDefault, boolean isSelected) {
        this.name = name;
        this.isDefault = isDefault;
        this.isSelected = isSelected;
    }
}
