package com.ordolabs.clipit.data.db;

import com.ordolabs.clipit.ClipItApplication;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by ordogod on 18.06.19.
 **/

class RealmHolder {

    private static volatile RealmHolder currentInstance;
    public Realm realm;

    private RealmHolder() {
        Realm.init(ClipItApplication.getAppContext());
        RealmConfiguration config = new RealmConfiguration.Builder().name("dbname.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
    }

    public static RealmHolder getInstance() {
        if (currentInstance == null) {
            currentInstance = new RealmHolder();
        }
        return currentInstance;
    }
}
