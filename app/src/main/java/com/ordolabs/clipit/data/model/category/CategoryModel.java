package com.ordolabs.clipit.data.model.category;

import android.support.v7.widget.RecyclerView;

import com.ordolabs.clipit.data.C;
import com.ordolabs.clipit.data.db.RealmDealer;
import com.ordolabs.clipit.data.db.realm_objects.CategoryObject;
import com.ordolabs.clipit.data.model.base.BaseModel;
import com.ordolabs.clipit.data.util.categoryRV.CategoryRVadapter;
import com.ordolabs.clipit.data.util.categoryRV.CategoryRaw;
import com.ordolabs.clipit.ui.category.CategoryMvpContract;
import com.ordolabs.clipit.ui.category.CategoryPresenter;

import java.util.ArrayList;
import java.util.Collections;

import io.realm.RealmResults;

/**
 * Created by ordogod on 15.07.19.
 **/

public class CategoryModel<P extends CategoryPresenter> extends BaseModel<P> implements CategoryMvpContract {

    private CategoryRVadapter adapter;

    public CategoryModel(P mvpPresenter, RecyclerView rv) {
        attachPresenter(mvpPresenter);

        this.adapter = new CategoryRVadapter(
                getRawCategoryListReversed(),
                rv
        );
    }

    @Override
    public void updateData() {
        C.updateData();
        adapter.setCategoryList(getRawCategoryListReversed());
    }

    private ArrayList<CategoryRaw> getRawCategoryListReversed() {
        int categoriesCount = RealmDealer.getCustomCategoriesCount();
        ArrayList<CategoryRaw> list = new ArrayList<>();
        ArrayList<CategoryRaw> defaults = new ArrayList<>(RealmDealer.getDefaultCategories());

        RealmResults<CategoryObject> results = RealmDealer.getCustomCategories();

        for (int i = 0; i < categoriesCount; i++) {
            list.add(new CategoryRaw(
                    results.get(i).getName(),
                    results.get(i).isRemovable(),
                    results.get(i).isActive()
            ));
        }

        Collections.reverse(list);
        defaults.addAll(list);

        return defaults;
    }

    public CategoryRVadapter getRVadapter() {
        return adapter;
    }
}
