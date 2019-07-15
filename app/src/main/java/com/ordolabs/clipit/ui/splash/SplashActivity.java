package com.ordolabs.clipit.ui.splash;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.ordolabs.clipit.ClipItApplication;
import com.ordolabs.clipit.R;
import com.ordolabs.clipit.data.db.RealmDealer;
import com.ordolabs.clipit.ui.base.BaseActivity;
import com.ordolabs.clipit.ui.home.HomeActivity;

import java.util.List;

import io.realm.RealmObject;

/**
 * Created by ordogod on 23.05.19.
 **/

public class SplashActivity extends BaseActivity {

    SharedPreferences prefs = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("com.ordolabs.clipit", MODE_PRIVATE);

        checkIfNeedsAutorun();
        setDefaultCategories();

        try { //TODO: remove sleep and set all kinds of time consuming preparations
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void checkIfNeedsAutorun() {
        // if it's the first launch and device requires an autorun to be turned on manually
        if (prefs.getBoolean("FIRST_RUN", true)) {

            prefs.edit().putBoolean("FIRST_RUN", false).apply();

            try {
                Intent intent = new Intent();
                String manufacturer = android.os.Build.MANUFACTURER;

                if ("xiaomi".equalsIgnoreCase(manufacturer)) {
                    intent.setComponent(new ComponentName(
                            "com.miui.securitycenter",
                            "com.miui.permcenter.autostart.AutoStartManagementActivity")
                    );
                }
                if ("oppo".equalsIgnoreCase(manufacturer)) {
                    intent.setComponent(new ComponentName(
                            "com.coloros.safecenter",
                            "com.coloros.safecenter.permission.startup.StartupAppListActivity")
                    );
                }
                if ("vivo".equalsIgnoreCase(manufacturer)) {
                    intent.setComponent(new ComponentName(
                            "com.vivo.permissionmanager",
                            "com.vivo.permissionmanager.activity.BgStartUpManagerActivity")
                    );
                }
                if ("letv".equalsIgnoreCase(manufacturer)) {
                    intent.setComponent(new ComponentName(
                            "com.letv.android.letvsafe",
                            "com.letv.android.letvsafe.AutobootManageActivity")
                    );
                }
                if ("honor".equalsIgnoreCase(manufacturer)) {
                    intent.setComponent(new ComponentName(
                            "com.huawei.systemmanager",
                            "com.huawei.systemmanager.optimize.process.ProtectActivity")
                    );
                }

                List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                if  (list.size() > 0) {
                    startActivity(intent);
                    //TODO: change toast to message at first run user guide
                    Toast.makeText(getApplicationContext(), R.string.autorunRequiredToast, Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Log.e("ERROR" , String.valueOf(e));
                e.printStackTrace();
            }
        }
        else { // start home activity if not a first launch
            startActivity(HomeActivity.getStartingIntent(this));
            finish();
        }
    }

    private void setDefaultCategories() {
        if (RealmDealer.getCategoryWithId(2) == null) {
            RealmDealer.createCategoryObject(
                    ClipItApplication.getAppContext().getResources().getString(R.string.categoryDefaultClipboardName),
                    false,
                    true
            );
            RealmDealer.createCategoryObject(
                    ClipItApplication.getAppContext().getResources().getString(R.string.categoryDefaultFavoriteName),
                    false,
                    false
            );
        }
    }
}
