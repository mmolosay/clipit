package com.ordolabs.clipit.data;

import com.ordolabs.clipit.ClipItApplication;

import io.realm.Realm;

/**
 * Created by ordogod on 18.06.19.
 **/

public class RealmHolder {

    private static volatile RealmHolder currentInstance;
    public Realm realm;

    private RealmHolder() {
        Realm.init(ClipItApplication.getAppContext());
        realm = Realm.getDefaultInstance();
    }

    public static RealmHolder getInstance() {
        if (currentInstance == null) {
            currentInstance = new RealmHolder();
        }
        return currentInstance;
    }
}
