package com.ordolabs.clipit.ui.edit;

import android.annotation.SuppressLint;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ordolabs.clipit.R;
import com.ordolabs.clipit.data.C;
import com.ordolabs.clipit.data.models.edit.EditModel;
import com.ordolabs.clipit.ui.base.BasePresenter;

/**
 * Created by ordogod on 09.07.19.
 **/

public class EditPresenter<V extends EditActivity> extends BasePresenter<V> implements EditMvpContract.Presenter<V> {

    private EditModel<EditPresenter> mvpModel;

    private Toolbar toolbar;

    private EditText titleEdit;
    private EditText bodyEdit;

    private TextView titleSymbolsCount;
    private TextView bodySymbolsCount;

    private int accentBlue;
    private int accentRed;

    EditPresenter(V mvpView, int clipPos) {
        attachView(mvpView);

        initViews();
        mvpModel = new EditModel<EditPresenter>(this, clipPos);
        prepareViews();
    }

    @Override
    protected void initViews() {
        toolbar = mvpView.findViewById(R.id.editToolbar);

        titleEdit = mvpView.findViewById(R.id.editTitle);
        bodyEdit = mvpView.findViewById(R.id.editBody);

        titleSymbolsCount = mvpView.findViewById(R.id.editTitleSymbolsCount);
        bodySymbolsCount = mvpView.findViewById(R.id.editBodySymbolsCount);

        accentBlue = mvpView.getResources().getColor(R.color.accent_blue);
        accentRed = mvpView.getResources().getColor(R.color.accent_red);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void prepareViews() {
        mvpView.setSupportActionBar(toolbar);
        mvpView.getSupportActionBar().setTitle(R.string.editToolbarTitle);
        toolbar.setNavigationIcon(mvpView.getResources().getDrawable(R.drawable.ic_arrow_back_light_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add "exit without save" dialog
                mvpView.finish();
            }
        });

        titleEdit.setText(mvpModel.getClip().getTitle() != null ? mvpModel.getClip().getTitle() : "");
        bodyEdit.setText(mvpModel.getClip().getBody());

        titleEdit.setFilters(new InputFilter[] { new InputFilter.LengthFilter(C.EDIT_MAX_TITLE_SYMBOLS)});
        bodyEdit.setFilters(new InputFilter[] { new InputFilter.LengthFilter(C.EDIT_MAX_BODY_SYMBOLS)});

        titleSymbolsCount.setText(titleEdit.getText().length() + "/" + C.EDIT_MAX_TITLE_SYMBOLS);
        bodySymbolsCount.setText(bodyEdit.getText().length() + "/" + C.EDIT_MAX_BODY_SYMBOLS);

        titleEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (titleEdit.getText().length() == C.EDIT_MAX_TITLE_SYMBOLS) {
                    titleSymbolsCount.setTextColor(accentRed);
                }
                else {
                    if (titleSymbolsCount.getCurrentTextColor() != accentBlue) {
                        titleSymbolsCount.setTextColor(accentBlue);
                    }
                }

                titleSymbolsCount.setText(titleEdit.getText().length() + "/" + C.EDIT_MAX_TITLE_SYMBOLS);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        bodyEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (bodyEdit.getText().length() == C.EDIT_MAX_BODY_SYMBOLS) {
                    bodySymbolsCount.setTextColor(accentRed);
                }
                else {
                    if (bodySymbolsCount.getCurrentTextColor() != accentBlue) {
                        bodySymbolsCount.setTextColor(accentBlue);
                    }
                }

                bodySymbolsCount.setText(bodyEdit.getText().length() + "/" + C.EDIT_MAX_BODY_SYMBOLS);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    protected void updateStates() {

    }

    @Override
    protected void animateActivityHiding() {

    }

    @Override
    protected void animateActivityShowing() {

    }
}
