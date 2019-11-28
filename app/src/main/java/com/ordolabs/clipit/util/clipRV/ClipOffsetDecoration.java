package com.ordolabs.clipit.util.clipRV;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by ordogod on 27.11.2019.
 **/

public class ClipOffsetDecoration extends RecyclerView.ItemDecoration {

    private int offset;

    public ClipOffsetDecoration(int offset) {
        this.offset = offset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(offset, 0, offset, offset * 2);
    }
}
