package com.ordolabs.clipit.data;

import android.support.annotation.NonNull;

/**
 * Created by ordogod on 18.06.19.
 **/

public class RealmDealer {

    public static int getObjectsNumber(@NonNull Class obj) {
        return RealmHolder.getInstance().realm.where(obj).findAll().size();
    }
}
