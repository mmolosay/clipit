package com.ordolabs.clipit.ui.clip;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ordolabs.clipit.R;
import com.ordolabs.clipit.data.C;
import com.ordolabs.clipit.data.db.RealmDealer;
import com.ordolabs.clipit.data.model.ClipModel;
import com.ordolabs.clipit.generic.AdvancedToolbar;
import com.ordolabs.clipit.generic.BasePresenter;
import com.ordolabs.clipit.ui.edit.EditActivity;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by ordogod on 28.06.19.
 **/

public class ClipPresenter<V extends ClipActivity>
        extends BasePresenter<V> implements AdvancedToolbar {

    private ClipModel<ClipPresenter> mvpModel;

    private Toolbar toolbar;
    private TextView titleTextView;
    private TextView bodyTextView;
    private ScrollView scrollView;

    ClipPresenter(V mvpView) {
        attachView(mvpView);

        initViews();
        mvpModel = new ClipModel<>(this);
        prepareViews();
    }

    @Override
    protected void initViews() {
        toolbar = mvpView.findViewById(R.id.clipToolbar);

        titleTextView = mvpView.findViewById(R.id.clipTitleText);
        bodyTextView = mvpView.findViewById(R.id.clipBodyText);

        scrollView = mvpView.findViewById(R.id.clipScrollView);
    }

    @Override
    protected void prepareViews() {
        AdvancedToolbar.prepareToolbar(mvpView, toolbar);
        toolbar.setNavigationOnClickListener(v -> mvpView.finish());
    }

    @Override
    public void update() {
        mvpModel.updateData();
        updateViews();
    }

    @Override
    public void updateViews() {
        toggleTitleOnEmpty();
        updateAllText();
        scrollToTop();
    }

    private void toggleTitleOnEmpty() {
        if (mvpModel.getClip().getTitle() != null)
            titleTextView.setVisibility(View.VISIBLE);
        else
            titleTextView.setVisibility(View.GONE);
    }

    private void updateAllText() {
        titleTextView.setText(mvpModel.getClip().getTitle());
        bodyTextView.setText(mvpModel.getClip().getBody());
        assert (mvpView.getSupportActionBar() != null);
        mvpView.getSupportActionBar().setTitle(mvpModel.makeActivityTitle());
    }

    void onMenuEdit(@NonNull Context from) {
        Intent i = new Intent(from, EditActivity.class);
        i.putExtra(C.EXTRA_CLIP_POSITION, mvpModel.getClipPos());
        mvpView.startActivity(i);
    }

    void onMenuCopy(@NonNull Context from) {
        ((ClipboardManager) from
                .getSystemService(CLIPBOARD_SERVICE))
                .setPrimaryClip(ClipData.newPlainText("", mvpModel.getClip().getBody()));
        Toast.makeText(from, R.string.clipCopiedToClipBoardToast, Toast.LENGTH_SHORT).show();
    }

    void onMenuDelete(@NonNull Context from) {
        new AlertDialog.Builder(from)
            .setTitle(R.string.alertDialogDeleteTitle)
            .setMessage(R.string.alertDialogDeleteMessage)
            .setPositiveButton(R.string.alertDialogDeletePositive, (dialog, which) -> {
                RealmDealer.markClipRemoved(mvpModel.getClipPos(), true);
                mvpView.finish();
            })
            .setNegativeButton(R.string.alertDialogDeleteNegative, null)
            .show();
    }

    private void scrollToTop() {
        scrollView.smoothScrollTo(0, 0);
    }
}
