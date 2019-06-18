package com.ordolabs.clipit.data.service;

import android.app.Service;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.ordolabs.clipit.data.utils.ServiceAwakenerBR;

import java.util.Objects;

/**
 * Created by ordogod on 17.06.19.
 **/

public class ClipboardListenerService extends Service {

    private ClipboardManager clipboardManager;
    private ClipboardManager.OnPrimaryClipChangedListener onPrimaryClipChangedListener =
            new ClipboardManager.OnPrimaryClipChangedListener() {
                @Override
                public void onPrimaryClipChanged() {
                    String clipText = Objects.requireNonNull(clipboardManager.getPrimaryClip())
                            .getItemAt(0)
                            .getText().toString();
                    if (clipText.length() > 25) clipText = clipText.substring(0, 20) + "…";
                    Toast.makeText(getApplicationContext(), "\"" + clipText + "\" is clipped!" , Toast.LENGTH_SHORT).show();
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
