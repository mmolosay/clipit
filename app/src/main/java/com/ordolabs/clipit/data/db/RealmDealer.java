package com.ordolabs.clipit.data.db;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ordolabs.clipit.data.db.realm_objects.ClipObject;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by ordogod on 18.06.19.
 **/

public class RealmDealer {

    public static void createClipObject(@Nullable final String title,
                                        @NonNull final String body,
                                        @NonNull final String datetime) {

        RealmHolder.getInstance().realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                ClipObject clip = RealmHolder.getInstance().realm.createObject(
                        ClipObject.class,
                        getIdForNewClip()
                );

                clip.setTitle(title);
                clip.setBody(body);
                clip.setDateTime(datetime);
                clip.setViewed(false);
            }
        });
    }

    private static int getIdForNewClip() {
        Number maxId = RealmHolder.getInstance().realm
                .where(ClipObject.class)
                .max("id");
        return (maxId == null) ? 1 : maxId.intValue() + 1;
    }

    public static RealmResults<ClipObject> getAllClips() {
        return RealmHolder.getInstance().realm
                .where(ClipObject.class)
                .findAll();
    }

    public static ClipObject getClipAtPos(int pos, boolean reversed) {
        int clipsSize = getClipsCount();
        if (pos >= clipsSize)
            throw new IllegalArgumentException(
                    "Position number is invalid. pos: " + pos + ", size: " + clipsSize + "."
            );

        if (reversed == true) pos = clipsSize - 1 - pos;

        return RealmHolder.getInstance().realm
                .where(ClipObject.class)
                .findAll()
                .get(pos);
    }

    public static int getClipsCount() {
        return getAllClips().size();
    }

    public static void setClipAsViewed(final int clipPos) {
        RealmHolder.getInstance().realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                ClipObject a = getClipAtPos(clipPos, true);
                a.setViewed(true);
            }
        });
    }

    public static void deleteClipAtPosition(final int position) {
        RealmHolder.getInstance().realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                getClipAtPos(position, true).deleteFromRealm();
            }
        });
    }

    public static boolean isSameBodyClipExist(String body) {
        RealmResults<ClipObject> clips = getAllClips();
        for (ClipObject clip : clips) {
            if (clip.getBody().equals(body)) return true;
        }
        return false;
    }

    public static void dropAllObjects(@NonNull final Class obj) {
        RealmHolder.getInstance().realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                RealmHolder.getInstance().realm.where(obj).findAll().deleteAllFromRealm();
            }
        });
    }
}
