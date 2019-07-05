package com.ordolabs.clipit.data.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.IBinder;

import com.ordolabs.clipit.data.C;
import com.ordolabs.clipit.data.db.RealmDealer;
import com.ordolabs.clipit.data.utils.ServiceAwakenerBR;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Created by ordogod on 17.06.19.
 **/

public class ClipboardListenerService extends Service {

    private ClipboardManager clipboardManager;
    private ClipboardManager.OnPrimaryClipChangedListener onPrimaryClipChangedListener =
            new ClipboardManager.OnPrimaryClipChangedListener() {
                @SuppressLint("SimpleDateFormat")
                @Override
                public void onPrimaryClipChanged() {
                    String clipText = Objects.requireNonNull(clipboardManager.getPrimaryClip())
                            .getItemAt(0)
                            .getText().toString();

                    RealmDealer.createClipObject(
                            null,
                            clipText,
                            new SimpleDateFormat(C.DATETIME_FORMAT).format(new Date())
                    );
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
        Intent intent = new Intent().setAction("android.intent.action.REAWAKE_SERVICE").setClass(this, ServiceAwakenerBR.class);
        this.sendBroadcast(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        if (clipboardManager != null) {
            clipboardManager.removePrimaryClipChangedListener(onPrimaryClipChangedListener);
        }
        super.onDestroy();
    }
}
