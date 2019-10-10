package com.ordolabs.clipit.ui.splash;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ordolabs.clipit.App;
import com.ordolabs.clipit.R;
import com.ordolabs.clipit.data.realm.RealmDealer;
import com.ordolabs.clipit.ui.home.HomeActivity;
import com.ordolabs.clipit.util.PrettyDate;

import static com.ordolabs.clipit.data.realm.RealmDealer.deleteMarkedClips;

/**
 * Created by ordogod on 23.05.19.
 **/

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private static final String FIRST_RUN = "FIRST_RUN";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences("com.ordolabs.clipit", MODE_PRIVATE);

        if (prefs.getBoolean(FIRST_RUN, true)) {
            checkAutorun();
            addDefaultCategories();

            prefs.edit().putBoolean(FIRST_RUN, false).apply();
        }
        else {
            deleteMarkedClips();
        }

        startActivity(new Intent(this, HomeActivity.class));
        finish(); // remove from activities stack
    }

    private void checkAutorun() {
        // if it is a very first launch and device
        // requires an autorun to be turned on manually

        try {
            Intent i = new Intent();
            String manufacturer = android.os.Build.MANUFACTURER;

            if ("xiaomi".equalsIgnoreCase(manufacturer))
                i.setComponent(new ComponentName(
                        "com.miui.securitycenter",
                        "com.miui.permcenter.autostart.AutoStartManagementActivity"
                ));
            if ("oppo".equalsIgnoreCase(manufacturer))
                i.setComponent(new ComponentName(
                        "com.coloros.safecenter",
                        "com.coloros.safecenter.permission.startup.StartupAppListActivity"
                ));
            if ("vivo".equalsIgnoreCase(manufacturer))
                i.setComponent(new ComponentName(
                        "com.vivo.permissionmanager",
                        "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"
                ));
            if ("letv".equalsIgnoreCase(manufacturer))
                i.setComponent(new ComponentName(
                        "com.letv.android.letvsafe",
                        "com.letv.android.letvsafe.AutobootManageActivity"
                ));
            if ("honor".equalsIgnoreCase(manufacturer))
                i.setComponent(new ComponentName(
                        "com.huawei.systemmanager",
                        "com.huawei.systemmanager.optimize.process.ProtectActivity"
                ));

            if (getPackageManager().queryIntentActivities(i, PackageManager.MATCH_DEFAULT_ONLY).size() > 0) {
                startActivity(i);
                // TODO: change toast to guide at first run
                Toast.makeText(getApplicationContext(), R.string.autorunRequiredToast, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void addDefaultCategories() {
        RealmDealer.createCategoryObject(
                App.getContext().getResources().getString(R.string.categoryDefaultClipboardName),
                true
        );
        RealmDealer.createCategoryObject(
                App.getContext().getResources().getString(R.string.categoryDefaultFavoriteName),
                true
        );
    }
}
