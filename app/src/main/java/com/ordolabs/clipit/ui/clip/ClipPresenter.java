package com.ordolabs.clipit.ui.clip;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ordolabs.clipit.R;
import com.ordolabs.clipit.data.C;
import com.ordolabs.clipit.data.realm.RealmDealer;
import com.ordolabs.clipit.data.model.ClipModel;
import com.ordolabs.clipit.common.BasePresenter;
import com.ordolabs.clipit.ui.edit.EditActivity;
import com.ordolabs.clipit.util.PrettyDate;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by ordogod on 28.06.19.
 **/

public class ClipPresenter<V extends ClipActivity>
        extends BasePresenter<V> {

    private ClipModel<ClipPresenter> mvpModel;

    private ActionBar actionBar;
    private TextView dateTimeView;
    private TextView titleView;
    private TextView bodyView;
    private ScrollView scrollView;

    ClipPresenter(V mvpView) {
        attachView(mvpView);

        initViews();
        mvpModel = new ClipModel<>(this);
        prepareViews();
    }

    @Override
    protected void initViews() {
        actionBar = mvpView.getSupportActionBar();

        dateTimeView = mvpView.findViewById(R.id.clipDatetime);
        titleView = mvpView.findViewById(R.id.clipTitle);
        bodyView = mvpView.findViewById(R.id.clipBody);

        scrollView = mvpView.findViewById(R.id.clipScrollView);
    }

    @Override
    protected void prepareViews() {
        if (actionBar != null) {
            actionBar.setTitle(R.string.clipToolbarTitile);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        dateTimeView.setText(PrettyDate.fullWithSeparators(mvpModel.getClip().getDateTime()));
    }

    @Override
    public void update() {
        mvpModel.updateData();
        updateViews();
    }

    @Override
    public void updateViews() {
        updateText();
        scrollToTop();
    }

    private void updateText() {
//        actionBar.setTitle(R.string.clipToolbarTitile);
//        titleView.setText(mvpModel.getClip().getTitle());
//        bodyView.setText(mvpModel.getClip().getText());
//        dateTimeView.setText(PrettyDate.fullWithSeparators(mvpModel.getClip().getDateTime()));
    }

    void onMenuEdit(@NonNull Context from) {
        Intent i = new Intent(from, EditActivity.class);
        i.putExtra(C.EXTRA_CLIP_ID, mvpModel.getClipID());
        mvpView.startActivity(i);
    }

    void onMenuCopy(@NonNull Context from) {
        ((ClipboardManager) from
                .getSystemService(CLIPBOARD_SERVICE))
                .setPrimaryClip(ClipData.newPlainText("", mvpModel.getClip().getText()));
        Toast.makeText(from, R.string.clipCopiedToClipBoardToast, Toast.LENGTH_SHORT).show();
    }

    void onMenuDelete(@NonNull Context from) {
        new AlertDialog.Builder(from)
            .setTitle(R.string.alertDialogDeleteTitle)
            .setMessage(R.string.alertDialogDeleteMessage)
            .setPositiveButton(R.string.alertDialogDeletePositive, (dialog, which) -> {
                RealmDealer.markClipRemoved(mvpModel.getClipID(), true);
                mvpView.finish();
            })
            .setNegativeButton(R.string.alertDialogDeleteNegative, null)
            .show();
    }

    private void scrollToTop() {
        scrollView.smoothScrollTo(0, 0);
    }
}
