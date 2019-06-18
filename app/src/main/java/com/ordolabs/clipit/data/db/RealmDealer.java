package com.ordolabs.clipit.data.db;

import android.support.annotation.NonNull;

import com.ordolabs.clipit.data.db.RealmHolder;

/**
 * Created by ordogod on 18.06.19.
 **/

public class RealmDealer {

    public static int getObjectsNumber(@NonNull Class obj) {
        return RealmHolder.getInstance().realm.where(obj).findAll().size();
    }
}
