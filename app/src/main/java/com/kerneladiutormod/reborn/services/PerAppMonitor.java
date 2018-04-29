package com.kerneladiutormod.reborn.services;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.kerneladiutormod.reborn.utils.Utils;
import com.kerneladiutormod.reborn.utils.database.ProfileDB;
import com.kerneladiutormod.reborn.utils.tools.Per_App;
import com.kerneladiutor.library.root.RootUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joe on 3/2/16.
 */
public class PerAppMonitor extends AccessibilityService {
    private static final String TAG = PerAppMonitor.class.getSimpleName();
    public static String sPackageName;
    public static String accessibilityId;
    String last_package = "";
    String last_profile = "";
    long time= System.currentTimeMillis();

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        AccessibilityServiceInfo serviceInfo = this.getServiceInfo();
        accessibilityId = serviceInfo.getId();

        PackageManager localPackageManager = getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        String launcher = localPackageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY).activityInfo.packageName;

        if(event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && event.getPackageName() != null) {
            sPackageName = event.getPackageName().toString();
            Log.d(TAG, "Package Name is "+sPackageName);
            if ((System.currentTimeMillis() - time) < 1000) {
                if (!sPackageName.equals(launcher) || !sPackageName.equals("com.android.systemui")) {
                    process_window_change(sPackageName);
                }
            }
            else if ((System.currentTimeMillis() - time) >= 1000) {
                process_window_change(sPackageName);
            }
        }
    }

    @Override
    public void onInterrupt() {

    }

    private void process_window_change (String packageName) {
        if (!Per_App.app_profile_exists(packageName, getApplicationContext())) {
            packageName = "Default";
            Log.d(TAG, "Profile does not exist. Using Default");
        }
        if (Per_App.app_profile_exists(packageName, getApplicationContext())) {
            ArrayList<String> info = new ArrayList<String>();
            // Item 0 is package name Item 1 is the profile ID
            info = Per_App.app_profile_info(packageName, getApplicationContext());

            if (!packageName.equals(last_package) && !info.get(1).equals(last_profile)) {
                last_package = packageName;
                last_profile = info.get(1);
                time = System.currentTimeMillis();
                ProfileDB profileDB = new ProfileDB(getApplicationContext());
                final List<ProfileDB.ProfileItem> profileItems = profileDB.getAllProfiles();

                for (int i = 0; i < profileItems.size(); i++) {
                    if (profileItems.get(i).getID().equals(info.get(1))) {
                        if (Utils.getBoolean("Per_App_Toast", false, this)) {
                            Utils.toast("Applying Profile: " + profileItems.get(i).getName(), this);
                        }
                        Log.i(TAG, "Applying Profile:  " + profileItems.get(i).getName() + " for package " + packageName);
                        ProfileDB.ProfileItem profileItem = profileItems.get(i);
                        List<String> paths = profileItem.getPath();
                        for (int x = 0; x < paths.size(); x++) {
                            RootUtils.runCommand(profileItem.getCommands().get(x));
                        }
                    }
                }
            }
        }
    }

}
