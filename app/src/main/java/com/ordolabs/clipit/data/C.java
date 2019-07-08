package com.ordolabs.clipit.data;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ordogod on 05.07.19.
 **/

public final class C {
    public static final String DATETIME_FORMAT = "d MMM HH:mm yyyy";
    public static final String EXTRA_CLICKED_CLIP_POSITION = "EXTRA_CLICKED_CLIP_POSITION";

    public static String current_datetime = "";

    @SuppressLint("SimpleDateFormat")
    public static void updateData() {
        current_datetime = new SimpleDateFormat(C.DATETIME_FORMAT).format(new Date());
    }
}
