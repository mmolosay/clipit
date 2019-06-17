package com.ordolabs.clipit.data;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by ordogod on 17.06.19.
 **/

public class ServiceAwakener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()) ||
                    intent.getAction().equals("android.intent.action.REAWAKE_SERVICE")) {
                context.startService(new Intent(context, ClipboardListenerService.class));
            }
        }
        catch (Exception e) {
            Log.e("WARNING", "Empty intent action body.");
            e.printStackTrace();
        }
    }
}
