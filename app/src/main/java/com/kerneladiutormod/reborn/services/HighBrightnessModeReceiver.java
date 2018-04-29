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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kerneladiutormod.reborn.R;
import com.kerneladiutormod.reborn.utils.Constants;
import com.kerneladiutormod.reborn.utils.Utils;
import com.kerneladiutormod.reborn.utils.kernel.Screen;
import com.kerneladiutor.library.root.RootUtils;

/**
 * Created by willi on 09.08.15.
 */
public class HighBrightnessModeReceiver extends BroadcastReceiver {

    private static final String HBM_ON = "com.kerneladiutor.mod.action.HBM_ON";
    private static final String HBM_OFF = "com.kerneladiutor.mod.action.HBM_OFF";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (HBM_ON.equals(intent.getAction()) || HBM_OFF.equals(intent.getAction())) {
            if (!RootUtils.rootAccess()) {
                Utils.toast(context.getString(R.string.no_root), context);
                return;
            }
            if (!RootUtils.hasAppletSupport()) {
                Utils.toast(context.getString(R.string.no_busybox), context);
                return;
            }

            if (HBM_ON.equals(intent.getAction()) && Screen.hasScreenHBM() && !Screen.isScreenHBMActive()) {
                Screen.activateScreenHBM(true, context, "Manual");
                if (Utils.getBoolean("Widget_Active", false, context)) {
                    HBMWidget.doupdate(context, true);
                }
                Log.i(Constants.TAG + ": " + getClass().getSimpleName(), "Activating High Brightness Mode via Intent");
            }
            if (HBM_OFF.equals(intent.getAction()) && Screen.hasScreenHBM() && Screen.isScreenHBMActive()) {
                Screen.activateScreenHBM(false, context, "Manual");
                if (Utils.getBoolean("Widget_Active", false, context)) {
                    HBMWidget.doupdate(context, false);
                }
                Log.i(Constants.TAG + ": " + getClass().getSimpleName(), "Disabling High Brightness Mode via Intent");
            }
        }
    }
}