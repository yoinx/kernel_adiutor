package com.grarak.kerneladiutor.services;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.grarak.kerneladiutor.R;
import com.grarak.kerneladiutor.utils.Constants;
import com.grarak.kerneladiutor.utils.Utils;
import com.grarak.kerneladiutor.utils.kernel.Screen;

/**
 * Created by joe on 5/10/16.
 */
public class HBMWidget extends AppWidgetProvider {

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        if (Screen.hasScreenHBM()) {
            setWidgetActive(true, context.getApplicationContext());
        } else {
            Utils.toast("Your device does not have HBM/SRE, this widget will not work. Please remove it.", context);
        }
    }

    @Override
    public void onDisabled(Context context) {
        setWidgetActive(false, context.getApplicationContext());
        super.onDisabled(context);
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];
            // Make sure that the widget is in the correct state when widgets are updating.
            doupdate(context, Screen.isScreenHBMActive());

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.hbm_widget_layout);
            Intent intent = new Intent(context, HBMWidget.class);
            intent.setAction("com.kerneladiutor.mod.action.TOGGLE_HBM");
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            int flag = PendingIntent.FLAG_UPDATE_CURRENT;
            PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, flag);
            views.setOnClickPendingIntent(R.id.imageView, pi);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (Utils.getBoolean("Widget_Active", false, context) && Screen.hasScreenHBM()) {
            if (intent.getAction().equals("com.kerneladiutor.mod.action.TOGGLE_HBM")) {
                if (Screen.hasScreenHBM()) {
                    Log.i(Constants.TAG + ": " + getClass().getSimpleName(), "Toggling High Brightness Mode");
                    if (Screen.isScreenHBMActive()) {
                        Screen.activateScreenHBM(false, context, "Manual");
                        doupdate(context, false);
                    } else {
                        Screen.activateScreenHBM(true, context, "Manual");
                        doupdate(context, true);
                    }
                }
            }
            // Make sure that the widget is in the correct state when the phone is unlocked.
            if (intent.getAction().equals("android.intent.action.USER_PRESENT") && Screen.hasScreenHBM()) {
                doupdate(context, Screen.isScreenHBMActive());
            }
        }
    }

    private void setWidgetActive(boolean active, Context context){
        Utils.saveBoolean("Widget_Active", active, context);
    }

    public static void doupdate (Context context, boolean active) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.hbm_widget_layout);
        ComponentName thisWidget = new ComponentName(context, HBMWidget.class);
        if (active) {
            remoteViews.setImageViewResource(R.id.imageView, R.drawable.ic_high_brightness_on);
            remoteViews.setInt(R.id.imageView, "setColorFilter", ContextCompat.getColor(context, R.color.hbm_widget_enabled));
        } else {
            remoteViews.setImageViewResource(R.id.imageView, R.drawable.ic_high_brightness_off);
            remoteViews.setInt(R.id.imageView, "setColorFilter", ContextCompat.getColor(context, R.color.hbm_widget_disabled));
        }
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
    }

}
