package com.ordolabs.clipit.data;

import com.ordolabs.clipit.App;
import com.ordolabs.clipit.R;

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


    public static int toInt(final String s) {
        return Integer.parseInt(s);
    }

    public static String getPrettyDate() {
        return new SimpleDateFormat(
            C.DATETIME_FORMAT,
            App.getContext().getResources().getConfiguration().locale
        ).format(new Date());
    }

    public static String makePrettyDateTime(final String datetime) {
        // [ day, month, time, year ] according pattern
        String[] given = datetime.split(" ");
        String[] now = C.getPrettyDate().split(" ");

        if (given[3].equals(now[3])) { // this year
            if (given[1].equals(now[1])) { // this month
                if (given[0].equals(now[0])) { // this day
                    // minutes difference
                    int delta = minutes(given, now);

                    if (delta == 0)
                        return App.getContext().getResources()
                                .getString(R.string.prettyDayTime_JustNow);
                    if (delta == 1)
                        return App.getContext().getResources()
                                .getString(R.string.prettyDayTime_MinuteAgo);
                    if (delta > 1 && delta < 10)
                        return delta + " " + App.getContext().getResources()
                                .getString(R.string.prettyDayTime_MinutesAgo);

                    return App.getContext().getResources()
                            .getString(R.string.prettyDayTime_Today) + ", " + given[2];
                }

                // yesterday
                if (toInt(now[0]) - toInt(given[0]) == 1 || (now[0].equals("1") &&
                   (toInt(given[0]) >= 28 && toInt(given[0]) <= 31)))
                    return App.getContext().getResources()
                            .getString(R.string.prettyDayTime_Yesterday) + ", " + given[2];
            }
            return given[0] + " " + given[1] + ", " + given[2];
        }
        return given[0] + " " + given[1] + ", " + given[2] + ", " + given[3];
    }

    private static int minutes(final String[] from, final String[] to) {
        return Math.abs(
                (toInt(to[2].split(":")[0]) * 60 + toInt(to[2].split(":")[1])) -
                (toInt(from[2].split(":")[0]) * 60 + toInt(from[2].split(":")[1]))
        );
    }
}
