package com.ordolabs.clipit.data.utils.rv;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.view.View;

import com.ordolabs.clipit.R;
import com.ordolabs.clipit.data.db.RealmDealer;

import static android.support.v7.widget.helper.ItemTouchHelper.*;

/**
 * Created by ordogod on 06.07.19.
 **/

public class RVswipeController extends Callback {

    private RVadapter adapter;

    private ClipRaw clipRemoved;
    private int clipPosition;

    public RVswipeController(RVadapter adapter) {
        this.adapter = adapter;
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
                        if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                            RealmDealer.deleteClipAtPosition(clipPosition);
                        }
                    }
                }
        ).show();
    }
}