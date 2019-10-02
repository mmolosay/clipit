package com.ordolabs.clipit.data.db;

import com.ordolabs.clipit.App;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by ordogod on 18.06.19.
 **/

class RealmHolder {

    private static volatile RealmHolder currentInstance;
    private Realm realm;

    private RealmHolder() {
        Realm.init(App.getContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("dbname.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
    }

    // returns the current instance of RealmHolder class
    static Realm i() {
        if (currentInstance == null) {
            currentInstance = new RealmHolder();
        }
        return currentInstance.realm;
    }
}
