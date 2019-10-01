package com.ordolabs.clipit.generic;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ordolabs.clipit.R;

/**
 * Created by ordogod on 01.10.2019.
 **/

public interface AdvancedToolbar {

    static void prepareToolbar(AppCompatActivity act, Toolbar toolbar) {
        act.setSupportActionBar(toolbar);
        assert act.getSupportActionBar() != null;
        act.getSupportActionBar().setTitle(R.string.editToolbarTitle);
        toolbar.setNavigationIcon(act.getResources().getDrawable(R.drawable.ic_arrow_back_light_24dp));
    }
}
