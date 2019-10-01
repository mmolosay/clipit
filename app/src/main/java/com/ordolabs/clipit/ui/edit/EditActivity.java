package com.ordolabs.clipit.ui.edit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;

import com.ordolabs.clipit.R;

/**
 * Created by ordogod on 09.07.19.
 **/

public class EditActivity extends AppCompatActivity {

    private EditPresenter<EditActivity> mvpPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        mvpPresenter = new EditPresenter<>(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuDone) {
            mvpPresenter.onMenuDone();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mvpPresenter.updateView();
    }
}
