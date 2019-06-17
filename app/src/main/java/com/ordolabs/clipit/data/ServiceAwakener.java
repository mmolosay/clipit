package com.ordolabs.clipit.data;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Objects;

/**
 * Created by ordogod on 17.06.19.
 **/

public class ServiceAwakener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()) ||
            Objects.requireNonNull(intent.getAction()).equals("android.intent.action.REAWAKE_SERVICE")) {

            context.startService(new Intent(context, ClipboardListenerService.class));
        }
    }
}
