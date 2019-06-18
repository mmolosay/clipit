package com.ordolabs.clipit.data.db;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ordolabs.clipit.data.db.objects.ClipObject;

import io.realm.RealmResults;

/**
 * Created by ordogod on 18.06.19.
 **/

public class RealmDealer {

    public static void createClipObject(@Nullable String title, @NonNull String body) {

        RealmHolder.getInstance().realm.beginTransaction();

        ClipObject clip = RealmHolder.getInstance().realm.createObject(
                ClipObject.class,
                getIdForNewObject(ClipObject.class)
        );
        clip.setTitle(title);
        clip.setBody(body);

        RealmHolder.getInstance().realm.commitTransaction();
    }

    private static int getIdForNewObject(@NonNull Class obj) {
        Number maxId = RealmHolder.getInstance().realm.where(obj).max("id");
        return (maxId == null) ? 1 : maxId.intValue() + 1;
    }

    public static int getObjectsNumber(@NonNull Class obj) {
        return RealmHolder.getInstance().realm.where(obj).findAll().size();
    }

    public static void dropAllObjects(@NonNull Class obj) {
        RealmHolder.getInstance().realm.beginTransaction();
        RealmHolder.getInstance().realm.where(obj).findAll().deleteAllFromRealm();
        RealmHolder.getInstance().realm.commitTransaction();
    }
}
