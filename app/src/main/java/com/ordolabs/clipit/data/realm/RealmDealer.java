package com.ordolabs.clipit.data.realm;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ordolabs.clipit.data.realm.object.CategoryObject;
import com.ordolabs.clipit.data.realm.object.ClipObject;
import com.ordolabs.clipit.util.PrettyDate;
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
            @NonNull  final String body) {

        RealmHolder.i().executeTransaction(realm -> {
            ClipObject clip = RealmHolder.i().createObject(
                ClipObject.class,
                makeNewClipID()
            );

            clip.init(title, body, PrettyDate.now(), false, false);
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

    public static ClipObject getClip(final int id) {
        RealmResults<ClipObject> clips = getClips();

        return clips.where().equalTo("id", id).findFirst();
    }

    public synchronized static void markClipViewed(final int id) {
        RealmHolder.i().executeTransaction(realm ->
            getClip(id).setViewed(true));
    }

    public synchronized static void markClipRemoved(final int id, final boolean mark) {
        RealmHolder.i().executeTransaction(realm ->
            getClip(id).setRemoved(mark));
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

    public static void editClip(final int id, final String newTitle, final String newBody) {
        RealmHolder.i().executeTransaction(realm -> {
            ClipObject clip = getClip(id);
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
        CategoryObject obj;

        obj = getCategory(0);
        list.add(new CategoryRaw(
                obj.getName(),
                obj.isDefault(),
                obj.isSelected()
        ));

        obj = getCategory(1);
        list.add(new CategoryRaw(
                obj.getName(),
                obj.isDefault(),
                obj.isSelected()
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

    private static CategoryObject getCategory(final int id) {
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
}
