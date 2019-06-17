package com.ordolabs.clipit.data;

import android.content.ClipboardManager;

import com.ordolabs.clipit.ui.home.HomeActivity;
import com.ordolabs.clipit.ui.home.HomePresenter;

/**
 * Created by ordogod on 17.06.19.
 **/

public class HomeModel {

    private HomePresenter<HomeActivity> mvpPresenter;

    public HomeModel(HomePresenter<HomeActivity> mvpPresenter) {
        this.mvpPresenter = mvpPresenter;
    }
}
