package com.ordolabs.clipit.ui.edit;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.ordolabs.clipit.R;
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
    }

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

//        titleTextView.setText(mvpModel.getClip().getTitle());
//        bodyTextView.setText(mvpModel.getClip().getBody());
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
