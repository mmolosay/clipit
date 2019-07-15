package com.ordolabs.clipit.data.util.categoryRV;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ordolabs.clipit.R;

import java.util.ArrayList;

/**
 * Created by ordogod on 15.07.19.
 **/

public class CategoryRVadapter extends RecyclerView.Adapter<CategoryItemViewHolder> {

    private ArrayList<CategoryRaw> categoryList;
    private RecyclerView recyclerView;

    public CategoryRVadapter(ArrayList<CategoryRaw> categoryList, RecyclerView rv) {
        this.categoryList = categoryList;
        this.recyclerView = rv;
    }

    public void setCategoryList(ArrayList<CategoryRaw> categoryList) {
        this.categoryList.clear();
        this.categoryList.addAll(categoryList);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.category_list_item,
                        parent,
                        false
                );
        view.setOnClickListener(newOnClickListener(view));

        return new CategoryItemViewHolder(view);
    }

    private View.OnClickListener newOnClickListener(final View view) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPos = recyclerView.getChildLayoutPosition(view);
                // make active on click
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryItemViewHolder holder, int i) {
        CategoryRaw category = categoryList.get(i);

        // in cause of only RV adapter has an ability to interact with RV items,
        // all VFX with them should be performed here :(

        holder.name.setText(category.name);
    }

    @Override
    public int getItemCount() {
        if (categoryList != null)
            return categoryList.size();
        else
            return 0;
    }
}
