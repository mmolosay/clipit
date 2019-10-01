package com.ordolabs.clipit.ui.edit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.ordolabs.clipit.R;
import com.ordolabs.clipit.data.C;
import com.ordolabs.clipit.data.model.EditModel;
import com.ordolabs.clipit.generic.AdvancedToolbar;
import com.ordolabs.clipit.generic.BasePresenter;
import com.ordolabs.clipit.ui.home.HomeActivity;

/**
 * Created by ordogod on 09.07.19.
 **/

public class EditPresenter<V extends EditActivity>
        extends BasePresenter<V> implements AdvancedToolbar {

    private EditModel<EditPresenter> mvpModel;

    private Toolbar toolbar;

    private EditText titleEdit;
    private EditText bodyEdit;

    private TextView titleSymbolsCount;
    private TextView bodySymbolsCount;

    private int accentBlue;
    private int accentRed;

    private boolean wasEdited = false;

    EditPresenter(V mvpView) {
        attachView(mvpView);

        accentBlue = mvpView.getResources().getColor(R.color.accent_blue);
        accentRed = mvpView.getResources().getColor(R.color.accent_red);

        initViews();
        mvpModel = new EditModel<>(this);
        prepareViews();
    }

    @Override
    protected void initViews() {
        toolbar = mvpView.findViewById(R.id.editToolbar);

        titleEdit = mvpView.findViewById(R.id.editTitle);
        bodyEdit = mvpView.findViewById(R.id.editBody);

        titleSymbolsCount = mvpView.findViewById(R.id.editTitleSymbolsCount);
        bodySymbolsCount = mvpView.findViewById(R.id.editBodySymbolsCount);
    }

    @Override
    protected void prepareViews() {
        AdvancedToolbar.prepareToolbar(mvpView, toolbar);
        toolbar.setNavigationOnClickListener(v -> onMenuBack());

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

    private void onMenuBack() {
        if (wasEdited == false) mvpView.finish();
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
                    titleSymbolsCount.setTextColor(accentRed);
                else if (titleSymbolsCount.getCurrentTextColor() != accentBlue)
                    titleSymbolsCount.setTextColor(accentBlue);

                titleSymbolsCount.setText(titleEdit.getText().length() + "/" + C.EDIT_MAX_TITLE_SYMBOLS);
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
                    bodySymbolsCount.setTextColor(accentRed);
                else if (bodySymbolsCount.getCurrentTextColor() != accentBlue)
                    bodySymbolsCount.setTextColor(accentBlue);

                bodySymbolsCount.setText(bodyEdit.getText().length() + "/" + C.EDIT_MAX_BODY_SYMBOLS);
                wasEdited = true;
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @SuppressLint("SetTextI18n") // fake warning, it's all OK
    private void prepareTextViews() {
        titleSymbolsCount.setText(titleEdit.getText().length() + "/" + C.EDIT_MAX_TITLE_SYMBOLS);
        bodySymbolsCount.setText(bodyEdit.getText().length() + "/" + C.EDIT_MAX_BODY_SYMBOLS);
    }
}
