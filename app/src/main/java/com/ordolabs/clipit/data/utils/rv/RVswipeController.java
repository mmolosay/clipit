package com.ordolabs.clipit.data.utils.rv;

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
import android.util.TypedValue;
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
    private View itemView;
    private Drawable deleteIcon;
    private Paint paint;

    private ClipRaw clipRemoved;
    private int clipPosition;

    private float deleteIconScale = 1.5f;
    private int deleteIconWidth;
    private int deleteIconHeight;
    private int deleteIconCenteringOffset;
    private int deleteIconMargin;
    private float swipeBGroundness;

    public RVswipeController(RVadapter adapter) {
        this.adapter = adapter;

        Drawable deleteIcon = ContextCompat.getDrawable(ClipItApplication.getAppContext(), R.drawable.ic_delete_light_24dp);
        assert deleteIcon != null;

        this.deleteIconWidth = Math.round(deleteIcon.getIntrinsicWidth() * deleteIconScale);
        this.deleteIconHeight = Math.round(deleteIcon.getIntrinsicHeight() * deleteIconScale);

        this.deleteIcon = new ScaleDrawable(
                deleteIcon,
                0,
                deleteIconWidth,
                deleteIconHeight
        ).getDrawable();

        this.paint = new Paint();
        this.paint.setColor(ContextCompat.getColor(ClipItApplication.getAppContext(), R.color.delete_item_bg));

        this.swipeBGroundness = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                2,
                ClipItApplication.getAppContext().getResources().getDisplayMetrics()
        );
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
        if (dX == 0) super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        this.itemView = viewHolder.itemView;
        this.deleteIconMargin = (itemView.getHeight() - deleteIconHeight) / 2;
        this.deleteIconCenteringOffset =
                Math.abs(dX) > deleteIconWidth + deleteIconMargin * 2 ?
                Math.round(Math.abs(dX) - deleteIconWidth - deleteIconMargin * 2) / 2 :
                0;

        if (dX > 0) {
            deleteIcon.setBounds(
                    itemView.getLeft() + deleteIconMargin + deleteIconCenteringOffset,
                    itemView.getTop() + deleteIconMargin,
                    itemView.getLeft() + deleteIconMargin + deleteIconWidth + deleteIconCenteringOffset,
                    itemView.getBottom() - deleteIconMargin
            );
            c.clipRect(
                    itemView.getLeft(),
                    itemView.getTop(),
                    (int) dX,
                    itemView.getBottom()
            );

            c.drawRoundRect(new RectF(
                    itemView.getLeft(),
                    itemView.getTop(),
                    (int) dX < itemView.getRight() ? (int) dX : itemView.getRight(),
                    itemView.getBottom()
            ), swipeBGroundness, swipeBGroundness, paint);
        }
        else {
            deleteIcon.setBounds(
                    itemView.getRight() + (int) dX + deleteIconCenteringOffset,
                    itemView.getTop() + deleteIconMargin,
                    itemView.getLeft() + deleteIconMargin + deleteIconWidth + deleteIconCenteringOffset,
                    itemView.getBottom() - deleteIconMargin
            );
            c.clipRect(
                    itemView.getLeft(),
                    itemView.getTop(),
                    (int) dX,
                    itemView.getBottom()
            );

            c.drawRoundRect(new RectF(
                    (int) dX < itemView.getRight() ? (int) dX : itemView.getRight(),
                    itemView.getTop(),
                    itemView.getRight(),
                    itemView.getBottom()
            ), swipeBGroundness, swipeBGroundness, paint);
        }

        deleteIcon.draw(c);

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}