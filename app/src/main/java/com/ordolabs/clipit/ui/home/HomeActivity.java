package com.ordolabs.clipit.ui.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ordolabs.clipit.App;
import com.ordolabs.clipit.R;

public class HomeActivity extends AppCompatActivity {

    private HomePresenter<HomeActivity> mvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mvpPresenter = new HomePresenter<>(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);

        TextView title = getSupportActionBar().getCustomView().findViewById(R.id.actionBarTitle);

        float menuButtonWidth = App.DIPtoPixels(54);
        float actionBarOffset = App.DIPtoPixels(16);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) title.getLayoutParams();
        params.setMargins((int)menuButtonWidth, 0, (int)actionBarOffset, 0);
        title.setLayoutParams(params);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menuHomeSettings: {
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mvpPresenter.update();
    }
}
