/*
 * Copyright (C) 2015 Willi Ye
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kerneladiutormod.reborn.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import com.kerneladiutormod.reborn.utils.kernel.Screen;

import static android.os.SystemClock.elapsedRealtime;

/**
 * Created by willi on 08.03.15.
 */
public class AutoHighBrightnessModeService extends Service {
    public static float lux = 0, avglux = 0;
    public static int LuxThresh = 3000;
    public static boolean HBM_Manually_Toggled = false;
    public static float[] luxvalues = new float [3];

    private static long prevuptime = 0;
    private SensorManager sMgr;
    Sensor light;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LuxThresh = Screen.getAutoHBMThresh(getApplicationContext());
        luxvalues = new float [Screen.getAutoHBMSmoothingSamples(getApplicationContext())];
        init();
    }

    @Override
    public void onDestroy() {
        LuxThresh = Screen.getAutoHBMThresh(getApplicationContext());
        luxvalues = new float [Screen.getAutoHBMSmoothingSamples(getApplicationContext())];
        super.onDestroy();
        unregisterAutoHBMReceiver(getApplicationContext());
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (Build.VERSION.SDK_INT > 19 && pm.isInteractive()) {
            deactivateLightSensorRead();
        } else if (Build.VERSION.SDK_INT < 20 ) {
            deactivateLightSensorRead();
        }
    }

    private void init() {
        if (Screen.isScreenAutoHBMActive(getApplicationContext())) {
            registerAutoHBMReceiver(getApplicationContext());
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            if (Build.VERSION.SDK_INT > 19 && pm.isInteractive()) {
                activateLightSensorRead();
            } else if (Build.VERSION.SDK_INT < 20) {
                activateLightSensorRead();
            }
        }
    }

    public void activateLightSensorRead() {
        sMgr = (SensorManager) getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        light = sMgr.getDefaultSensor(Sensor.TYPE_LIGHT);
        sMgr.registerListener(_SensorEventListener, light, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void deactivateLightSensorRead() {
        sMgr.unregisterListener(_SensorEventListener);
    }

    SensorEventListener _SensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (elapsedRealtime() - prevuptime >= 100) {
                prevuptime = elapsedRealtime();
                // Call Screen.hasScreenHBM() here in the if block to ensure that the appropriate variables are set when calling Screen.activateScreenHBM
                if (Screen.isScreenAutoHBMActive(getApplicationContext()) && Screen.isScreenAutoHBMSmoothingActive(getApplicationContext()) && Screen.hasScreenHBM()) {
                    // This should average the last X lux values. X being user set or defaulting to 3.
                    // This will cause some delay in autohbm actually working as the values initialize at 0
                    avglux = 0;
                    for (int i = luxvalues.length - 1; i > 0; i--) {
                        luxvalues[i] = luxvalues[i - 1];
                        avglux += luxvalues[i];
                    }
                    luxvalues[0] = event.values[0];
                    avglux += luxvalues[0];
                    lux = Math.round(avglux / luxvalues.length);
                } else {
                    lux = Math.round(event.values[0]);
                }
                if (Screen.isScreenAutoHBMActive(getApplicationContext()) && !HBM_Manually_Toggled) {
                    if (lux >= LuxThresh && !Screen.isScreenHBMActive()) {
                        Log.i("Kernel Adiutor: ", "AutoHBMService Activating HBM: received LUX value: " + lux + " Threshold: " + LuxThresh);
                        Screen.activateScreenHBM(true, getApplicationContext(), "Auto");
                    }
                    if (lux < LuxThresh && Screen.isScreenHBMActive()) {
                        Log.i("Kernel Adiutor: ", "De-Activation: AutoHBMService: received LUX value: " + lux + " Threshold: " + LuxThresh);
                        Screen.activateScreenHBM(false, getApplicationContext(), "Auto");
                    }
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    private BroadcastReceiver AutoHBMreceiver = null;

    private void registerAutoHBMReceiver(Context context) {
        if (Screen.isScreenAutoHBMActive(getApplicationContext())) {
            final IntentFilter autohbmfilter = new IntentFilter();
            /** System Defined Broadcast */
            autohbmfilter.addAction(android.content.Intent.ACTION_SCREEN_ON);
            autohbmfilter.addAction(android.content.Intent.ACTION_SCREEN_OFF);

            AutoHBMreceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, android.content.Intent intent) {
                    String strAction = intent.getAction();
                    if (strAction.equals(android.content.Intent.ACTION_SCREEN_OFF)) {
                        if (!Screen.isScreenHBMLockActive(context)) {
                            HBM_Manually_Toggled = false;
                        }
                        if (Screen.isScreenAutoHBMActive(getApplicationContext())) {
                            LuxThresh = Screen.getAutoHBMThresh(getApplicationContext());
                            deactivateLightSensorRead();
                        }
                    }

                    if (strAction.equals(android.content.Intent.ACTION_SCREEN_ON)) {
                        if (!Screen.isScreenHBMLockActive(context)) {
                            HBM_Manually_Toggled = false;
                        }
                        if (Screen.isScreenAutoHBMActive(getApplicationContext())) {
                            LuxThresh = Screen.getAutoHBMThresh(getApplicationContext());
                            // Delay 250ms to allow sensor to reactivate after doze.
                            try {
                                Thread.sleep(250);
                            } catch (InterruptedException ex) {
                                Thread.currentThread().interrupt();
                            }
                            activateLightSensorRead();
                        }
                    }
                }
            };

            context.registerReceiver(AutoHBMreceiver, autohbmfilter);
        }
    }

    private void unregisterAutoHBMReceiver(Context context) {
        int apiLevel = Build.VERSION.SDK_INT;

        if (apiLevel >= 7) {
            try {
                context.unregisterReceiver(AutoHBMreceiver);
            } catch (IllegalArgumentException e) {
                AutoHBMreceiver = null;
            }
        } else {
            AutoHBMreceiver = null;
        }
    }
}

