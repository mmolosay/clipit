package com.ordolabs.clipit.data.utils.rv;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ordolabs.clipit.R;

/**
 * Created by ordogod on 18.06.19.
 **/

class ClipItemViewHolder extends RecyclerView.ViewHolder {

    TextView bodyTextView;
    TextView infoTextView;
    View newDriverMark;

    ClipItemViewHolder(View view) {
        super(view);

        bodyTextView = view.findViewById(R.id.RVclipsItemBody);
        infoTextView = view.findViewById(R.id.RVclipsItemInfo);
        newDriverMark = view.findViewById(R.id.RVclipsItemNewDriverMark);
    }
}
