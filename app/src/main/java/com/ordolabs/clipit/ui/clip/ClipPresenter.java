package com.ordolabs.clipit.ui.clip;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ordolabs.clipit.R;
import com.ordolabs.clipit.data.C;
import com.ordolabs.clipit.data.db.RealmDealer;
import com.ordolabs.clipit.data.models.clip.ClipModel;
import com.ordolabs.clipit.ui.base.BasePresenter;
import com.ordolabs.clipit.ui.edit.EditActivity;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by ordogod on 28.06.19.
 **/

public class ClipPresenter<V extends ClipActivity> extends BasePresenter<V> implements ClipMvpContract.Presenter<V> {

    private ClipModel<ClipPresenter> mvpModel;

    private Toolbar toolbar;
    private TextView titleTextView;
    private TextView bodyTextView;

    ClipPresenter(V mvpView, int clipPos) {
        attachView(mvpView);

        initViews();
        mvpModel = new ClipModel<ClipPresenter>(this, clipPos);
        prepareViews();
    }

    @Override
    protected void initViews() {
        toolbar = mvpView.findViewById(R.id.clipToolbar);
        titleTextView = mvpView.findViewById(R.id.clipTitleText);
        bodyTextView = mvpView.findViewById(R.id.clipBodyText);
    }

    @Override
    protected void prepareViews() {
        mvpView.setSupportActionBar(toolbar);
        mvpView.getSupportActionBar().setTitle(mvpModel.makeActivityTitle());
        toolbar.setNavigationIcon(mvpView.getResources().getDrawable(R.drawable.ic_arrow_back_light_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpView.finish();
            }
        });
    }

    @Override
    protected void updateStates() {
        mvpModel.updateData();
        toggleTitleOnEmpty();
        updateAllText();
    }

    @Override
    protected void animateActivityHiding() {

    }

    @Override
    protected void animateActivityShowing() {

    }

    private void updateAllText() {
        titleTextView.setText(mvpModel.getClip().getTitle());
        bodyTextView.setText(mvpModel.getClip().getBody());
    }

    private void toggleTitleOnEmpty() {
        if (mvpModel.getClip().getTitle() != null) {
            titleTextView.setVisibility(View.VISIBLE);
        }
        else {
            titleTextView.setVisibility(View.GONE);
        }
    }

    void menuOnEdit(Context callingContext) {
        Intent i = EditActivity
                .getStartingIntent(callingContext)
                .putExtra(C.EXTRA_CLIP_POSITION, mvpModel.getClipPos());
        mvpView.startActivity(i);
    }

    void menuOnCopy(Context callingContext) {
        ((ClipboardManager) callingContext.getSystemService(CLIPBOARD_SERVICE))
                .setPrimaryClip(ClipData.newPlainText("", mvpModel.getClip().getBody()));
        Toast.makeText(callingContext, R.string.clipCopiedToClipBoardToast, Toast.LENGTH_SHORT).show();
    }

    void menuOnDelete(Context callingContext) {
        new AlertDialog.Builder(callingContext)
                .setTitle(R.string.alertDialogDeleteTitle)
                .setMessage(R.string.alertDialogDeleteMessage)
                .setPositiveButton(R.string.alertDialogDeletePositive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RealmDealer.deleteClipAtPosition(mvpModel.getClipPos());
                        mvpView.finish();
                    }
                })
                .setNegativeButton(R.string.alertDialogDeleteNegative, null)
                .show();
    }
}
