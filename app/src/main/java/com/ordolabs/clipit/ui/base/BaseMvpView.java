package com.ordolabs.clipit.ui.base;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * Created by ordogod on 23.05.19.
 **/

public interface BaseMvpView {
    Intent getStartingIntent(@NonNull Context callingContext);
}
