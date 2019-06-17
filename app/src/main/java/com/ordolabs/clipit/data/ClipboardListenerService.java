package com.ordolabs.clipit.data;

import android.app.Service;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Objects;

/**
 * Created by ordogod on 17.06.19.
 **/

public class ClipboardListenerService extends Service {

    private ClipboardManager clipboardManager;
    private Context context = this;
    private ClipboardManager.OnPrimaryClipChangedListener onPrimaryClipChangedListener =
            new ClipboardManager.OnPrimaryClipChangedListener() {
                @Override
                public void onPrimaryClipChanged() {
                    String clipText = Objects.requireNonNull(clipboardManager.getPrimaryClip())
                            .getItemAt(0)
                            .getText().toString();
                    if (clipText.length() > 20) clipText = clipText.substring(0, 20) + "…";
                    Toast.makeText(context, "\"" + clipText + "\" copied!" , Toast.LENGTH_SHORT).show();
                }
            };

    @Override
    public void onCreate() {
        super.onCreate();

        Toast.makeText(this, "Service started.", Toast.LENGTH_SHORT).show();
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipboardManager.addPrimaryClipChangedListener(onPrimaryClipChangedListener);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (clipboardManager != null) {
            clipboardManager.removePrimaryClipChangedListener(onPrimaryClipChangedListener);
        }
    }
}
