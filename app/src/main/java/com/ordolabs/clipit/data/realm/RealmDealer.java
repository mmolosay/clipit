package com.ordolabs.clipit.data.realm;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ordolabs.clipit.BuildConfig;
import com.ordolabs.clipit.data.realm.object.CategoryObject;
import com.ordolabs.clipit.data.realm.object.ClipObject;
import com.ordolabs.clipit.util.categoryRV.CategoryRaw;

import java.util.ArrayList;

import io.realm.RealmResults;

/**
 * Created by ordogod on 18.06.19.
 **/

public class RealmDealer {

    //============================== CLIPS ==============================//

    public static void createClipObject(
            @Nullable final String title,
            @NonNull  final String body,
            @NonNull  final String datetime) {

        RealmHolder.i().executeTransaction(realm -> {
            ClipObject clip = RealmHolder.i().createObject(
                ClipObject.class,
                makeNewClipID()
            );

            clip.init(title, body, datetime, false, false);
        });
    }

    private synchronized static int makeNewClipID() {
        Number maxId = RealmHolder.i()
                .where(ClipObject.class)
                .max("id");
        return (maxId == null) ? 0 : maxId.intValue() + 1;
    }

    public synchronized static RealmResults<ClipObject> getClips() {
        return RealmHolder.i()
                .where(ClipObject.class)
                .findAll();
    }

    public static ClipObject getClipReversed(final int pos) {
        return getClip(getClipsCount() - pos - 1);
    }

    private static ClipObject getClip(final int pos) {
        RealmResults<ClipObject> clips = getClips();
        if (!BuildConfig.DEBUG && (pos >= clips.size() || pos < 0)) {
            throw new IllegalPositionArgumentEcxeptioin(pos, clips.size());
        }
        return clips.get(pos);
    }

    private static int getClipsCount() { return getClips().size(); }

    public synchronized static void markClipViewed(final int pos) {
        RealmHolder.i().executeTransaction(realm ->
            getClipReversed(pos).setViewed(true));
    }

    public synchronized static void markClipRemoved(final int pos, final boolean mark) {
        RealmHolder.i().executeTransaction(realm ->
            getClipReversed(pos).setRemoved(mark));
    }

    public static void deleteMarkedClips() {
        RealmHolder.i().executeTransaction(realm ->
            getClips()
                .where()
                .equalTo("isRemoved", true)
                .findAll()
                .deleteAllFromRealm());
    }

    public static boolean isSameBodyClipExist(final String body) {
        RealmResults<ClipObject> clips = getClips();
        for (ClipObject clip : clips) {
            if (clip.getBody().equalsIgnoreCase(body)) return true;
        }
        return false;
    }

    public static void editClip(final int clipPos, final String newTitle, final String newBody) {
        RealmHolder.i().executeTransaction(realm -> {
            ClipObject clip = getClipReversed(clipPos);
            clip.setBody(newBody);
            clip.setTitle(newTitle.length() == 0 ? null : newTitle);
        });
    }

    //============================== CATEGORIES ==============================//

    public static void createCategoryObject(@NonNull final String name,
                                                     final boolean isDefault) {
        RealmHolder.i().executeTransaction(realm -> {
            CategoryObject category = RealmHolder.i().createObject(
                    CategoryObject.class,
                    makeNewCategoryID()
            );

            category.setName(name);
            category.setDefault(isDefault);
            category.setSelected(false);
        });
    }

    private static int makeNewCategoryID() {
        Number maxId = RealmHolder.i()
                .where(CategoryObject.class)
                .max("id");
        return (maxId == null) ? 0 : maxId.intValue() + 1;
    }

    public static ArrayList<CategoryRaw> getDefaultCategoriesRaw() {
        ArrayList<CategoryRaw> list = new ArrayList<>();
        CategoryObject defaultCategory;

        defaultCategory = getCategoryWithId(0);
        list.add(new CategoryRaw(
                defaultCategory.getName(),
                defaultCategory.isDefault(),
                defaultCategory.isSelected()
        ));
        defaultCategory = getCategoryWithId(1);
        list.add(new CategoryRaw(
                defaultCategory.getName(),
                defaultCategory.isDefault(),
                defaultCategory.isSelected()
        ));

        return list;
    }

    public static RealmResults<CategoryObject> getCustomCategories() {
        return RealmHolder.i()
                .where(CategoryObject.class)
                .and()
                .notEqualTo("id", 0)
                .and()
                .notEqualTo("id", 1)
                .findAll();
    }

    public static int getCustomCategoriesCount() {
        return getCustomCategories().size();
    }

    private static CategoryObject getCategoryWithId(final int id) {
        return RealmHolder.i()
                .where(CategoryObject.class)
                .equalTo("id", id)
                .findFirst();
    }

    public static void dropAllObjects(@NonNull final Class obj) {
        RealmHolder.i().executeTransaction(realm ->
                RealmHolder.i().where(obj).findAll().deleteAllFromRealm()
        );
    }

    private static class IllegalPositionArgumentEcxeptioin extends RuntimeException {
        IllegalPositionArgumentEcxeptioin(int pos, int count) {
            super("Position can not be negative or greater than total clips count:" +
                  " pos: " + pos +
                  " count: " + count + "."
            );
        }
    }
}
