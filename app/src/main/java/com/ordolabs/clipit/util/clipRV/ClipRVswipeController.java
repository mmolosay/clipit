package com.ordolabs.clipit.util.clipRV;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.view.View;

import com.ordolabs.clipit.App;
import com.ordolabs.clipit.R;
import com.ordolabs.clipit.data.realm.RealmDealer;
import com.ordolabs.clipit.data.model.HomeModel;

import static android.support.v7.widget.helper.ItemTouchHelper.*;

/**
 * Created by ordogod on 06.07.19.
 **/

public class ClipRVswipeController extends Callback {

    private HomeModel attachedModel;

    private ClipRVadapter adapter;
    private View v;
    private Drawable icon;
    private RectF swipeBG;
    private Paint paint;

    private Clip clipRemoved;
    private int clipPos;

    private final float DELETE_ICON_SCALE = 1.5f;
    private int iconW;
    private int iconH;
    private int iconCenteringOffset;
    private int iconMargin;
    private int alphaBG;

    public ClipRVswipeController(ClipRVadapter adapter, HomeModel attachedModel) {
        this.attachedModel = attachedModel;
        this.adapter = adapter;

        Drawable deleteIcon = ContextCompat.getDrawable(App.getContext(), R.drawable.ic_delete_light_24dp);
        assert deleteIcon != null;

        this.iconW = Math.round(deleteIcon.getIntrinsicWidth() * DELETE_ICON_SCALE);
        this.iconH = Math.round(deleteIcon.getIntrinsicHeight() * DELETE_ICON_SCALE);

        this.icon = new ScaleDrawable(
                deleteIcon,
                0,
                iconW,
                iconH
        ).getDrawable();

        this.swipeBG = new RectF();

        this.paint = new Paint();
        this.paint.setColor(ContextCompat.getColor(App.getContext(), R.color.accentRed));
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
            @NonNull RecyclerView.ViewHolder viewHolder,
            @NonNull RecyclerView.ViewHolder target)
    {
        return false;
    }

    @Override
    public void onSwiped(
            @NonNull RecyclerView.ViewHolder viewHolder,
            int direction)
    {
        clipPos = viewHolder.getAdapterPosition();
        clipRemoved = adapter.deleteItem(clipPos);
        RealmDealer.markClipRemoved(clipRemoved.id, true);
        attachedModel.getPresenter().updateViews();

        Snackbar
            .make(
                viewHolder.itemView,
                R.string.clipDeleteSnackbarNotification,
                Snackbar.LENGTH_LONG
            )
            .setAction(
                R.string.clipDeleteSnackbarAction, v -> {
                    RealmDealer.markClipRemoved(clipRemoved.id, false);
                    adapter.restoreItem(clipPos, clipRemoved);
                    attachedModel.getPresenter().updateViews();
                }
            )
            .show();
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

        if (dX == 0) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

        this.v = viewHolder.itemView;
        this.iconMargin = (v.getHeight() - iconH) / 2;

        if (Math.abs(dX) > iconW + iconMargin * 2)
            iconCenteringOffset = Math.round(Math.abs(dX) - iconW - iconMargin * 2) / 2;
        else
            iconCenteringOffset = 0;

        if (dX > 0) {
            icon.setBounds(
                    v.getLeft() + iconMargin + iconCenteringOffset,
                    v.getTop() + iconMargin,
                    v.getLeft() + iconMargin + iconW + iconCenteringOffset,
                    v.getBottom() - iconMargin
            );
            c.clipRect(
                    v.getLeft(),
                    v.getTop(),
                    (int) dX,
                    v.getBottom()
            );

            swipeBG.set(
                    v.getLeft(),
                    v.getTop(),
                    (int) dX < v.getRight() ? (int) dX : v.getRight(),
                    v.getBottom()
            );
        }
        else {
            icon.setBounds(
                    v.getRight() - iconMargin - iconW - iconCenteringOffset,
                    v.getTop() + iconMargin,
                    v.getRight() - iconMargin - iconCenteringOffset,
                    v.getBottom() - iconMargin
            );
            c.clipRect(
                    v.getRight() + (int) dX + v.getLeft(),
                    v.getTop(),
                    v.getRight(),
                    v.getBottom()
            );

            swipeBG.set(
                    (int) -dX < v.getRight() ? v.getRight() + (int) dX + v.getLeft() : v.getLeft(),
                    v.getTop(),
                    v.getRight(),
                    v.getBottom()
            );
        }

        if (Math.abs(dX) < (float)(v.getRight() - v.getLeft()) / 2) {
            alphaBG = Math.round(Math.abs(dX) / ((v.getRight() - v.getLeft()) / 2) * 255);
        }
        else {
            alphaBG = 255;
            icon.setAlpha(255);
            paint.setAlpha(255);
        }

        if (alphaBG != 255) {
            icon.setAlpha(alphaBG);
            paint.setAlpha(alphaBG);
        }

        c.drawRect(swipeBG, paint);
        icon.draw(c);

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}