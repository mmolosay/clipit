package com.ordolabs.clipit.data.service;

import android.app.Service;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;

import com.ordolabs.clipit.data.C;
import com.ordolabs.clipit.data.db.RealmDealer;
import com.ordolabs.clipit.util.ServiceAwakenerBR;

/**
 * Created by ordogod on 17.06.19.
 **/

public final class ClipboardListenerService extends Service {

    private ClipboardManager clipboardManager;
    private final ClipboardManager.OnPrimaryClipChangedListener onPrimaryClipChangedListener =
        new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                String clipText = clipboardManager
                    .getPrimaryClip().getItemAt(0)
                    .getText().toString();

                if (RealmDealer.isSameBodyClipExist(clipText) == false)
                    RealmDealer.createClipObject(null, clipText, C.getPrettyDate());
            }
        };

    @Override
    public void onCreate() {
        super.onCreate();

        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipboardManager.addPrimaryClipChangedListener(onPrimaryClipChangedListener);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent i = new Intent()
            .setAction("android.intent.action.REAWAKE_SERVICE")
            .setClass(this, ServiceAwakenerBR.class);
        this.sendBroadcast(i);
    }

    @Override
    public IBinder onBind(Intent intent) { return null; }

    @Override
    public void onDestroy() {
        if (clipboardManager != null) {
            clipboardManager.removePrimaryClipChangedListener(onPrimaryClipChangedListener);
        }
        super.onDestroy();
    }

    public static void start(@NonNull Context caller) {
        caller.startService(new Intent(caller, ClipboardListenerService.class));
    }
}
