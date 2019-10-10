package com.ordolabs.clipit.data.model;

import android.support.v7.widget.RecyclerView;

import com.ordolabs.clipit.data.C;
import com.ordolabs.clipit.data.realm.RealmDealer;
import com.ordolabs.clipit.data.realm.object.CategoryObject;
import com.ordolabs.clipit.common.BaseModel;
import com.ordolabs.clipit.util.categoryRV.CategoryRVadapter;
import com.ordolabs.clipit.util.categoryRV.CategoryRaw;
import com.ordolabs.clipit.ui.category.CategoryPresenter;

import java.util.ArrayList;
import java.util.Collections;

import io.realm.RealmResults;

/**
 * Created by ordogod on 15.07.19.
 **/

public class CategoryModel<P extends CategoryPresenter> extends BaseModel<P> {

    private CategoryRVadapter adapter;

    public CategoryModel(P mvpPresenter, RecyclerView rv) {
        attachPresenter(mvpPresenter);

        this.adapter = new CategoryRVadapter(
                getRawCategoryList(),
                rv
        );
    }

    @Override
    public void updateData() {
        C.getPrettyDate();
        adapter.setCategoryList(getRawCategoryList());
    }

    private ArrayList<CategoryRaw> getRawCategoryList() {
        ArrayList<CategoryRaw> list = new ArrayList<>();
        ArrayList<CategoryRaw> defaults = new ArrayList<>(RealmDealer.getDefaultCategoriesRaw());

        RealmResults<CategoryObject> results = RealmDealer.getCustomCategories();
        int count = results.size();

        for (int i = 0; i < count; i++) {
            list.add(new CategoryRaw(
                    results.get(i).getName(),
                    results.get(i).isDefault(),
                    results.get(i).isSelected()
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
