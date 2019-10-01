package com.ordolabs.clipit.data;

import com.ordolabs.clipit.App;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ordogod on 05.07.19.
 **/

public final class C {

    private static final String DATETIME_FORMAT = "d MMM HH:mm yyyy";

    public static final String EXTRA_CLIP_POSITION = "EXTRA_CLIP_POSITION";
    public static final int EDIT_MAX_TITLE_SYMBOLS = 40;
    public static final int EDIT_MAX_BODY_SYMBOLS = 3000;


    public static String getPrettyDate() {
        return new SimpleDateFormat(
            C.DATETIME_FORMAT,
            App.getContext().getResources().getConfiguration().locale
        ).format(new Date());
    }
}
