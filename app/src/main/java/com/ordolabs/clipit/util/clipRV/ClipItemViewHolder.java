package com.ordolabs.clipit.util.clipRV;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ordolabs.clipit.R;

/**
 * Created by ordogod on 18.06.19.
 **/

final class ClipItemViewHolder extends RecyclerView.ViewHolder {

    TextView title;
    TextView body;
    TextView titleBody;
    TextView dateText;
    View     newDriverMark;

    ClipItemViewHolder(View view) {
        super(view);

        title = view.findViewById(R.id.RVclipsItemTitle);
        body = view.findViewById(R.id.RVclipsItemBody);
        titleBody = view.findViewById(R.id.RVclipsItemTitleBody);
        dateText = view.findViewById(R.id.RVclipsItemDate);
        newDriverMark = view.findViewById(R.id.RVclipsItemNewDriverMark);
    }
}
