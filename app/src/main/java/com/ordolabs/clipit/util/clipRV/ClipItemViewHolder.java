package com.ordolabs.clipit.util.clipRV;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ordolabs.clipit.R;

/**
 * Created by ordogod on 18.06.19.
 **/

final class ClipItemViewHolder extends RecyclerView.ViewHolder {

    TextView textView;
    TextView dateView;

    ClipItemViewHolder(View view) {
        super(view);

        textView = view.findViewById(R.id.RVclipsItemText);
        dateView = view.findViewById(R.id.RVclipsItemDate);
    }
}
