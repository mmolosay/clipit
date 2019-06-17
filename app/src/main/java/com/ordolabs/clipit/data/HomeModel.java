package com.ordolabs.clipit.data;

import com.ordolabs.clipit.ui.home.HomePresenter;

/**
 * Created by ordogod on 17.06.19.
 **/

public class HomeModel {

    private HomePresenter mvpPresenter;

    public HomeModel(HomePresenter mvpPresenter) {
        this.mvpPresenter = mvpPresenter;
    }
}
