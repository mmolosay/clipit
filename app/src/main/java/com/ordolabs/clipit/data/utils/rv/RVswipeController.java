package com.ordolabs.clipit.data.utils.rv;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.view.View;

import com.ordolabs.clipit.ClipItApplication;
import com.ordolabs.clipit.R;
import com.ordolabs.clipit.data.db.RealmDealer;

import static android.support.v7.widget.helper.ItemTouchHelper.*;

/**
 * Created by ordogod on 06.07.19.
 **/

public class RVswipeController extends Callback {

    private RVadapter adapter;
    private ColorDrawable swipeBG;
    private Drawable deleteIcon;

    private ClipRaw clipRemoved;
    private int clipPosition;

    public RVswipeController(RVadapter adapter) {
        this.adapter = adapter;
        this.swipeBG = new ColorDrawable(Color.parseColor("#ff0000"));
        this.deleteIcon = ContextCompat.getDrawable(ClipItApplication.getAppContext(), R.drawable.ic_delete_light_24dp);
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder)
    {
        return makeMovementFlags(0, LEFT | RIGHT);
    }

    @Override
    public boolean onMove(
            @NonNull RecyclerView recyclerView,
            @NonNull  RecyclerView.ViewHolder viewHolder,
            @NonNull  RecyclerView.ViewHolder target)
    {
        return false;
    }

    @Override
    public void onSwiped(
            @NonNull RecyclerView.ViewHolder viewHolder,
            int direction)
    {
        clipPosition = viewHolder.getAdapterPosition();
        clipRemoved = adapter.deleteItem(clipPosition);

        Snackbar
        .make(
                viewHolder.itemView,
                R.string.clipDeleteSnackbarNotification,
                Snackbar.LENGTH_LONG
        ).setAction(
                R.string.clipDeleteSnackbarAction,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.restoreItem(clipPosition, clipRemoved);
                    }
                }
        ).addCallback(
                new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        if (event != Snackbar.Callback.DISMISS_EVENT_ACTION) {
                            RealmDealer.deleteClipAtPosition(clipPosition);
                        }
                    }
                }
        ).show();
    }

    @Override
    public void onChildDraw(@NonNull Canvas c,
                            @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            float dX,
                            float dY,
                            int actionState,
                            boolean isCurrentlyActive)
    {
        View itemView = viewHolder.itemView;
        if (dX > 0) {
            swipeBG.setBounds(
                    itemView.getLeft() - ((RecyclerView.LayoutParams) itemView.getLayoutParams()).leftMargin,
                    itemView.getTop(),
                    (int) dX, itemView.getBottom()
            );
        }
        else {

        }

        swipeBG.draw(c);

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}