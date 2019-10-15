package com.ordolabs.clipit.ui.edit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.ordolabs.clipit.R;
import com.ordolabs.clipit.data.C;
import com.ordolabs.clipit.data.model.EditModel;
import com.ordolabs.clipit.common.BasePresenter;
import com.ordolabs.clipit.ui.home.HomeActivity;

/**
 * Created by ordogod on 09.07.19.
 **/

public class EditPresenter<V extends EditActivity>
        extends BasePresenter<V> {

    private EditModel<EditPresenter> mvpModel;

    private ActionBar actionBar;

    private EditText titleEdit;
    private EditText bodyEdit;

    private TextView titleSymbCountView;
    private TextView bodySymbCountView;

    private int accentBlue;
    private int accentRed;

    private boolean wasEdited = false;

    EditPresenter(V mvpView) {
        attachView(mvpView);

        accentBlue = mvpView.getResources().getColor(R.color.accentBlue);
        accentRed = mvpView.getResources().getColor(R.color.accentRed);

        initViews();
        mvpModel = new EditModel<>(this);
        prepareViews();
    }

    @Override
    protected void initViews() {
        actionBar = mvpView.getSupportActionBar();

        titleEdit = mvpView.findViewById(R.id.editTitle);
        bodyEdit = mvpView.findViewById(R.id.editBody);

        titleSymbCountView = mvpView.findViewById(R.id.editTitleSymbolsCount);
        bodySymbCountView = mvpView.findViewById(R.id.editBodySymbolsCount);
    }

    @Override
    protected void prepareViews() {
        if (actionBar != null) {
            actionBar.setTitle(R.string.editToolbarTitle);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        prepareEditTexts();
        prepareTextViews();
    }

    @Override
    public void update() {
        //
    }

    @Override
    public void updateViews() {
        //
    }

    boolean onMenuBack() {
        if (wasEdited == false) {
            mvpView.finish();
            return true;
        }
        else
            new AlertDialog.Builder(mvpView)
                .setTitle(R.string.alertDialogEditTitle)
                .setMessage(R.string.alertDialogEditMessage)
                .setPositiveButton(R.string.alertDialogEditPositive, (dialog, which) -> {
                    Intent i = new Intent(mvpView, HomeActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mvpView.startActivity(i);
                })
                .setNegativeButton(R.string.alertDialogEditNegative, null)
                .show();

        return false;
    }

    void onMenuDone() {
        if (wasEdited == false) mvpView.finish();
        else mvpModel.rewriteClip(
                titleEdit.getText().toString(),
                bodyEdit.getText().toString()
        );
    }

    private void prepareEditTexts() {
        titleEdit.setText(mvpModel.getClip().getTitle() != null ? mvpModel.getClip().getTitle() : "");
        bodyEdit.setText(mvpModel.getClip().getBody());

        titleEdit.setFilters(new InputFilter[] { new InputFilter.LengthFilter(C.EDIT_MAX_TITLE_SYMBOLS)});
        bodyEdit.setFilters(new InputFilter[] { new InputFilter.LengthFilter(C.EDIT_MAX_BODY_SYMBOLS)});

        titleEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @SuppressLint("SetTextI18n") // fake warning, it's all OK
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (titleEdit.getText().length() == C.EDIT_MAX_TITLE_SYMBOLS)
                    titleSymbCountView.setTextColor(accentRed);
                else if (titleSymbCountView.getCurrentTextColor() != accentBlue)
                    titleSymbCountView.setTextColor(accentBlue);

                titleSymbCountView.setText(titleEdit.getText().length() + "/" + C.EDIT_MAX_TITLE_SYMBOLS);
                wasEdited = true;
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        bodyEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}


            @SuppressLint("SetTextI18n") // fake warning, it's all OK
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (bodyEdit.getText().length() == C.EDIT_MAX_BODY_SYMBOLS)
                    bodySymbCountView.setTextColor(accentRed);
                else if (bodySymbCountView.getCurrentTextColor() != accentBlue)
                    bodySymbCountView.setTextColor(accentBlue);

                bodySymbCountView.setText(bodyEdit.getText().length() + "/" + C.EDIT_MAX_BODY_SYMBOLS);
                wasEdited = true;
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @SuppressLint("SetTextI18n") // fake warning, it's all OK
    private void prepareTextViews() {
        titleSymbCountView.setText(titleEdit.getText().length() + "/" + C.EDIT_MAX_TITLE_SYMBOLS);
        bodySymbCountView.setText(bodyEdit.getText().length() + "/" + C.EDIT_MAX_BODY_SYMBOLS);
    }
}
