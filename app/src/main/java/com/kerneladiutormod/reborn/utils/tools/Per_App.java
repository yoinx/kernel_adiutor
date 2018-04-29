package com.kerneladiutormod.reborn.utils.tools;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;

import com.kerneladiutormod.reborn.utils.database.PerAppDB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by joe on 2/29/16.
 */
public class Per_App {

    public static final class App implements Comparable<App>{
        final String name;
        final String packageId;

        private App(String name, String packageId) {
            this.name = name;
            this.packageId = packageId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            App app = (App) o;
            return packageId.equals(app.packageId);
        }

        @Override
        public int hashCode() {
            return packageId.hashCode();
        }

        @Override
        public int compareTo(App another) {
            return name.compareToIgnoreCase(another.name);
        }
    }

    public static List<App> getInstalledApps (Context context) {
        // Get a list of installed apps. Currently this is only the package name
        final PackageManager pm = context.getPackageManager();
        final List<App> applist = new ArrayList<>();
        final List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {
            App app = new App (String.valueOf(packageInfo.loadLabel(pm)), packageInfo.packageName);
            applist.add(app);
        }

        Collections.sort(applist);

        return applist;
    }

    public static String[] getPackageNames (List<App> apps) {
        String[] array = new String[apps.size()+1];

        for (int i = 0; i < apps.size()+1; i++) {
            if(i == 0){
                array[i] = "Default";
            } else {
                array[i] = apps.get(i-1).packageId;
            }
        }

        return  array;
    }

    public static String[] getAppNames (List<App> apps) {
        String[] array = new String[apps.size()+1];

        for (int i = 0; i < apps.size()+1; i++) {
            if(i == 0){
                array[i] = "Default";
            } else {
                array[i] = apps.get(i-1).name;
            }
        }

        return  array;
    }

    public static void save_app (String app, String id, Context context) {
        PerAppDB perappDB = new PerAppDB(context);
        List<PerAppDB.PerAppItem> PerAppItem = perappDB.getAllApps() ;
        for (int i = 0; i < PerAppItem.size(); i++) {
            String p = PerAppItem.get(i).getApp();
            if (p != null && p.equals(app)) {
                perappDB.delApp(i);
            }
        }

        perappDB.putApp(app, id);
        perappDB.commit();
    }

    public static void remove_app (String app, String id, Context context) {
        PerAppDB perappDB = new PerAppDB(context);

        List<PerAppDB.PerAppItem> PerAppItem = perappDB.getAllApps() ;
        for (int i = 0; i < PerAppItem.size(); i++) {
            String p = PerAppItem.get(i).getApp();
            if (p != null && p.equals(app)) {
                perappDB.delApp(i);
            }
        }

        perappDB.commit();
    }

    public static boolean app_profile_exists (String app, Context context) {
        PerAppDB perappDB = new PerAppDB(context);
        boolean exists = perappDB.containsApp(app);

        return exists;
    }

    public static ArrayList<String> app_profile_info (String app, Context context) {
        PerAppDB perappDB = new PerAppDB(context);
        if (perappDB.containsApp(app)) {
            return perappDB.get_info(app);
        }

        return null;
    }

    public static boolean[] getExistingSelections (String[] apps, String profile, Context context) {
        PerAppDB perappDB = new PerAppDB(context);
        boolean exists[] = new boolean[apps.length];

        List<PerAppDB.PerAppItem> PerAppItem = perappDB.getAllApps() ;
        for (int i = 0; i < PerAppItem.size(); i++) {
            String p = PerAppItem.get(i).getApp();
            String id = PerAppItem.get(i).getID();
            if (p != null && Arrays.asList(apps).contains(p)) {
                if (id != null && id.equals(profile)) {
                    exists[Arrays.asList(apps).indexOf(p)] = true;
                }
            }
        }
        return exists;
    }

    public static boolean isAccessibilityEnabled(Context context, String id) {

        AccessibilityManager am = (AccessibilityManager) context
                .getSystemService(Context.ACCESSIBILITY_SERVICE);

        List<AccessibilityServiceInfo> runningServices = am
                .getEnabledAccessibilityServiceList(AccessibilityEvent.TYPES_ALL_MASK);
        for (AccessibilityServiceInfo service : runningServices) {
            if (id != null && id.equals(service.getId())) {
                return true;
            }
        }

        return false;
    }

}
