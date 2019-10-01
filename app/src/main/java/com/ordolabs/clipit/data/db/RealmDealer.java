package com.ordolabs.clipit.data.db;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ordolabs.clipit.data.db.realm_objects.CategoryObject;
import com.ordolabs.clipit.data.db.realm_objects.ClipObject;
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
                    getNewClipID()
            );

            clip.setTitle(title);
            clip.setBody(body);
            clip.setDateTime(datetime);
            clip.setViewed(false);
        });
    }

    private synchronized static int getNewClipID() {
        Number maxId = RealmHolder.i()
                .where(ClipObject.class)
                .max("id");
        return (maxId == null) ? 0 : maxId.intValue() + 1;
    }

    public static RealmResults<ClipObject> getAllClips() {
        return RealmHolder.i()
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

        return RealmHolder.i()
                .where(ClipObject.class)
                .findAll()
                .get(pos);
    }

    public static int getClipsCount() {
        return getAllClips().size();
    }

    public static void setClipAsViewed(final int clipPos) {
        RealmHolder.i().executeTransaction(realm -> {
            ClipObject a = getClipAtPos(clipPos, true);
            a.setViewed(true);
        });
    }

    public static void deleteClipAtPosition(final int position) {
        RealmHolder.i().executeTransaction(realm ->
            getClipAtPos(position, true).deleteFromRealm()
        );
    }

    public static boolean isSameBodyClipExist(String body) {
        RealmResults<ClipObject> clips = getAllClips();
        for (ClipObject clip : clips) {
            if (clip.getBody().equals(body)) return true;
        }
        return false;
    }

    public static void rewriteClip(final int clipPos, final String newTitle, final String newBody) {
        RealmHolder.i().executeTransaction(realm -> {
            ClipObject clip = getClipAtPos(clipPos, true);
            clip.setBody(newBody);
            clip.setTitle(newTitle.length() == 0 ? null : newTitle);
        });
    }

    //============================== CATEGORIES ==============================//

    public static void createCategoryObject(@NonNull final String name,
                                            final boolean isRemovable,
                                            final boolean isActvie) {
        RealmHolder.i().executeTransaction(realm -> {
            CategoryObject category = RealmHolder.i().createObject(
                    CategoryObject.class,
                    getIdForNewCategory()
            );

            category.setName(name);
            category.setRemovable(isRemovable);
            category.setActive(isActvie);
        });
    }

    private static int getIdForNewCategory() {
        Number maxId = RealmHolder.i()
                .where(CategoryObject.class)
                .max("id");
        return (maxId == null) ? 1 : maxId.intValue() + 1;
    }

    public static ArrayList<CategoryRaw> getDefaultCategories() {
        ArrayList<CategoryRaw> list = new ArrayList<>();
        CategoryObject defaultCategory;

        defaultCategory = getCategoryWithId(1);
        list.add(new CategoryRaw(
                defaultCategory.getName(),
                defaultCategory.isRemovable(),
                defaultCategory.isActive()
        ));
        defaultCategory = getCategoryWithId(2);
        list.add(new CategoryRaw(
                defaultCategory.getName(),
                defaultCategory.isRemovable(),
                defaultCategory.isActive()
        ));

        return list;
    }

    public static RealmResults<CategoryObject> getCustomCategories() {
        return RealmHolder.i()
                .where(CategoryObject.class)
                .and()
                .equalTo("id", 1)
                .and()
                .equalTo("id", 2)
                .findAll();
    }

    public static int getCustomCategoriesCount() {
        return getCustomCategories().size();
    }

    public static CategoryObject getCategoryWithId(int id) {
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
