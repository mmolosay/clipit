package com.ordolabs.clipit.data.util.clipRV;

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
import com.ordolabs.clipit.data.model.home.HomeModel;
import com.ordolabs.clipit.ui.home.HomePresenter;

import static android.support.v7.widget.helper.ItemTouchHelper.*;

/**
 * Created by ordogod on 06.07.19.
 **/

public class ClipRVswipeController extends Callback {

    private HomeModel attachedModel;

    private ClipRVadapter adapter;
    private View itemView;
    private Drawable deleteIcon;
    private RectF swipeBG;
    private Paint paint;

    private ClipRaw clipRemoved;
    private int clipPosition;

    private final float DELETE_ICON_SCALE = 1.75f;
    private int deleteIconWidth;
    private int deleteIconHeight;
    private int deleteIconCenteringOffset;
    private int deleteIconMargin;
    private float swipeBGradius;
    private int alphaBG;

    public ClipRVswipeController(ClipRVadapter adapter, HomeModel attachedModel) {
        this.attachedModel = attachedModel;
        this.adapter = adapter;

        Drawable deleteIcon = ContextCompat.getDrawable(ClipItApplication.getAppContext(), R.drawable.ic_delete_light_24dp);
        assert deleteIcon != null;

        this.deleteIconWidth = Math.round(deleteIcon.getIntrinsicWidth() * DELETE_ICON_SCALE);
        this.deleteIconHeight = Math.round(deleteIcon.getIntrinsicHeight() * DELETE_ICON_SCALE);

        this.deleteIcon = new ScaleDrawable(
                deleteIcon,
                0,
                deleteIconWidth,
                deleteIconHeight
        ).getDrawable();

        this.swipeBG = new RectF();

        this.paint = new Paint();
        this.paint.setColor(ContextCompat.getColor(ClipItApplication.getAppContext(), R.color.accent_red));

        this.swipeBGradius = TypedValue.applyDimension(
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

        attachedModel.increaseClipsVisibleBy(-1);

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
                        else {
                            attachedModel.increaseClipsVisibleBy(1);
                            ((HomePresenter) attachedModel.getAttachedPresenter()).toggleNoClipsContainer();
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

        if (dX == 0) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

        this.itemView = viewHolder.itemView;
        this.deleteIconMargin = (itemView.getHeight() - deleteIconHeight) / 2;

        if (Math.abs(dX) > deleteIconWidth + deleteIconMargin * 2) {
            this.deleteIconCenteringOffset = Math.round(Math.abs(dX) - deleteIconWidth - deleteIconMargin * 2) / 2;
        }
        else {
            this.deleteIconCenteringOffset = 0;
        }

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

            swipeBG.set(
                    itemView.getLeft(),
                    itemView.getTop(),
                    (int) dX < itemView.getRight() ? (int) dX : itemView.getRight(),
                    itemView.getBottom()
            );
        }
        else {
            deleteIcon.setBounds(
                    itemView.getRight() - deleteIconMargin - deleteIconWidth - deleteIconCenteringOffset,
                    itemView.getTop() + deleteIconMargin,
                    itemView.getRight() - deleteIconMargin - deleteIconCenteringOffset,
                    itemView.getBottom() - deleteIconMargin
            );
            c.clipRect(
                    itemView.getRight() + (int) dX + itemView.getLeft(),
                    itemView.getTop(),
                    itemView.getRight(),
                    itemView.getBottom()
            );

            swipeBG.set(
                    (int) -dX < itemView.getRight() ? itemView.getRight() + (int) dX + itemView.getLeft() : itemView.getLeft(),
                    itemView.getTop(),
                    itemView.getRight(),
                    itemView.getBottom()
            );
        }

        if (Math.abs(dX) < (itemView.getRight() - itemView.getLeft()) / 2) {
            alphaBG = Math.round(Math.abs(dX) / ((itemView.getRight() - itemView.getLeft()) / 2) * 255);
        }
        else {
            alphaBG = 255;
            deleteIcon.setAlpha(255);
            paint.setAlpha(255);
        }

        if (alphaBG != 255) {
            deleteIcon.setAlpha(alphaBG);
            paint.setAlpha(alphaBG);
        }

        c.drawRoundRect(swipeBG, swipeBGradius, swipeBGradius, paint);
        deleteIcon.draw(c);

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}