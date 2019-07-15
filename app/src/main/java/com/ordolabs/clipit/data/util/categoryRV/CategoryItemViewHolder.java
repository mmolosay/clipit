package com.ordolabs.clipit.data.util.categoryRV;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ordolabs.clipit.R;

/**
 * Created by ordogod on 15.07.19.
 **/

class CategoryItemViewHolder extends RecyclerView.ViewHolder {

    TextView name;

    CategoryItemViewHolder(View view) {
        super(view);

        name = view.findViewById(R.id.categoryRVitemName);
    }
}
