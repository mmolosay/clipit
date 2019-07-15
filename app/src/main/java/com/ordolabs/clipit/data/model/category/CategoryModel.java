package com.ordolabs.clipit.data.model.category;

import android.support.v7.widget.RecyclerView;

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
                mvpPresenter.getAttachedView(),
                rv
        );
    }

    @Override
    public void updateData() {

    }

    private ArrayList<CategoryRaw> getRawCategoryListReversed() {
        int categoriesCount = RealmDealer.getCategoriesCount();
        if (categoriesCount == 0) return new ArrayList<>();

        ArrayList<CategoryRaw> list = new ArrayList<>();
        RealmResults<CategoryObject> results = RealmDealer.getAllCategories();

        for (int i = 0; i < categoriesCount; i++) {
            list.add(new CategoryRaw(
                    results.get(i).getName(),
                    results.get(i).isRemovable(),
                    results.get(i).isActive()
            ));
        }

        Collections.reverse(list);
        return list;
    }

    public CategoryRVadapter getRVadapter() {
        return adapter;
    }
}
