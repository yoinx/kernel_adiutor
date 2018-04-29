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

package com.kerneladiutormod.reborn.utils.kernel;

import android.content.Context;

import com.kerneladiutormod.reborn.utils.Constants;
import com.kerneladiutormod.reborn.utils.Utils;
import com.kerneladiutormod.reborn.utils.root.Control;

/**
 * Created by willi on 03.01.15.
 */
public class Battery implements Constants {

    private static String CURRENT_PATH;

    public static boolean hasChargingCurrent() {
        for(String path : BATTERY_CHARGING_CURRENT) {
            if (Utils.existFile(path))
                CURRENT_PATH = path;
        }
        return CURRENT_PATH != null;
    }
	
	public static int getChargingCurrent() {
        return Utils.stringToInt(Utils.readFile(CURRENT_PATH));
    }

    public static void setChargingRate(int value, Context context) {
        Control.runCommand(String.valueOf(value), CUSTOM_CHARGING_RATE, Control.CommandType.GENERIC, context);
    }

    public static int getChargingRate() {
        return Utils.stringToInt(Utils.readFile(CUSTOM_CHARGING_RATE));
    }

    public static boolean hasChargingRate() {
        return Utils.existFile(CUSTOM_CHARGING_RATE);
    }

    public static void setlowpowervalue(int value, Context context) {
        Control.runCommand(String.valueOf(value), LOW_POWER_VALUE, Control.CommandType.GENERIC, context);
    }

    public static int getlowpowervalue() {
        return Utils.stringToInt(Utils.readFile(LOW_POWER_VALUE));
    }

    public static boolean haslowpowervalue() {
        return Utils.existFile(LOW_POWER_VALUE);
    }

    public static void activateCustomChargeRate(boolean active, Context context) {
        Control.runCommand(active ? "1" : "0", CHARGE_RATE_ENABLE, Control.CommandType.GENERIC, context);
    }

    public static boolean isCustomChargeRateActive() {
        return Utils.readFile(CHARGE_RATE_ENABLE).equals("1");
    }

    public static boolean hasCustomChargeRateEnable() {
        return Utils.existFile(CHARGE_RATE_ENABLE);
    }

    public static boolean hasChargeRate() {
        return Utils.existFile(CHARGE_RATE);
    }

    public static void setBlx(int value, Context context) {
        Control.runCommand(String.valueOf(value), BLX, Control.CommandType.GENERIC, context);
    }

    public static int getCurBlx() {
        return Utils.stringToInt(Utils.readFile(BLX));
    }

    public static boolean hasBlx() {
        return Utils.existFile(BLX);
    }

    public static void activateForceFastCharge(boolean active, Context context) {
        Control.runCommand(active ? "1" : "0", FORCE_FAST_CHARGE, Control.CommandType.GENERIC, context);
    }

    public static void setFastChargeCurrent(int value, Context context) {
        Control.runCommand(String.valueOf(value), FORCE_FAST_CHARGE_CURRENT, Control.CommandType.GENERIC, context);
    }

    public static int getFastChargeCurrent() {
        return Utils.stringToInt(Utils.readFile(FORCE_FAST_CHARGE_CURRENT));
    }

    public static boolean isForceFastChargeActive() {
        return Utils.readFile(FORCE_FAST_CHARGE).equals("1");
    }

    public static boolean hasForceFastCharge() {
        return Utils.existFile(FORCE_FAST_CHARGE);
    }

    public static boolean hasForceFastChargeCurrent() {
        return Utils.existFile(FORCE_FAST_CHARGE_CURRENT);
    }

    public static boolean hasChargeLevelControl() {
        return Utils.existFile(CHARGE_LEVEL);
    }

    public static boolean hasChargeLevelControlAC() {
        return Utils.existFile(AC_CHARGE_LEVEL);
    }

    public static int getChargeLevelControlAC() {
        return Utils.stringToInt(Utils.readFile(AC_CHARGE_LEVEL));
    }

    public static void setChargeLevelControlAC (int value, Context context) {
        Control.runCommand(String.valueOf(value), AC_CHARGE_LEVEL, Control.CommandType.GENERIC, context);
    }

    public static boolean hasChargeLevelControlUSB() {
        return Utils.existFile(USB_CHARGE_LEVEL);
    }

    public static int getChargeLevelControlUSB() {
        return Utils.stringToInt(Utils.readFile(USB_CHARGE_LEVEL));
    }

    public static void setChargeLevelControlUSB (int value, Context context) {
        Control.runCommand(String.valueOf(value), USB_CHARGE_LEVEL, Control.CommandType.GENERIC, context);
    }


    public static void activateArchPower(boolean active, Context context) {
        Control.runCommand(active ? "1" : "0", ARCH_POWER, Control.CommandType.GENERIC, context);
    }

    public static boolean isArchPowerActive() {
        return Utils.readFile(ARCH_POWER).equals("1");
    }

    public static boolean hasArchPower() {
        return Utils.existFile(ARCH_POWER);
    }

    public static void setNewPowerSuspend(int value, Context context) {
        Control.runCommand(String.valueOf(value), POWER_SUSPEND_STATE, Control.CommandType.GENERIC, context);
    }

    public static int getNewPowerSuspendState() {
        return Utils.stringToInt(Utils.readFile(POWER_SUSPEND_STATE));
    }

    public static boolean hasNewPowerSuspendState() {
        if (Utils.existFile(POWER_SUSPEND_STATE) && Utils.existFile(POWER_SUSPEND_VERSION)) {
            String version = Utils.readFile(POWER_SUSPEND_VERSION);
            if (version.contains("1.3") || version.contains("1.5")) return true;
        }
        return false;
    }

    public static void activateOldPowerSuspend(boolean active, Context context) {
        Control.runCommand(active ? "1" : "0", POWER_SUSPEND_STATE, Control.CommandType.GENERIC, context);
    }

    public static boolean isOldPowerSuspendStateActive() {
        return Utils.readFile(POWER_SUSPEND_STATE).equals("1");
    }

    public static boolean hasOldPowerSuspendState() {
        if (Utils.existFile(POWER_SUSPEND_STATE) && Utils.existFile(POWER_SUSPEND_VERSION))
            if (Utils.readFile(POWER_SUSPEND_VERSION).contains("1.2")) return true;
        return false;
    }

    public static void setPowerSuspendMode(int value, Context context) {
        Control.runCommand(String.valueOf(value), POWER_SUSPEND_MODE, Control.CommandType.GENERIC, context);
    }

    public static int getPowerSuspendMode() {
        return Utils.stringToInt(Utils.readFile(POWER_SUSPEND_MODE));
    }

    public static boolean hasPowerSuspendMode() {
        return Utils.existFile(POWER_SUSPEND_MODE);
    }

    public static boolean hasPowerSuspend() {
        return Utils.existFile(POWER_SUSPEND);
    }


    public static void activateStateNotifier(boolean active, Context context) {
        Control.runCommand(active ? "Y" : "N", STATE_NOTIFIER_ENABLED, Control.CommandType.GENERIC, context);
    }

    public static boolean isStateNotifierStateActive() {
        return Utils.readFile(STATE_NOTIFIER_ENABLED).equals("Y");
    }

    public static boolean hasStateNotifier()  {
        return Utils.existFile(STATE_NOTIFIER_ENABLED);
    }

    public static void activateC0State (boolean active, Context context) {
        String path = C0STATE;
        for (int i = 0; i < CPU.getCoreCount(); i++ ) {
            Control.runCommand(active ? "1" : "0", path.replace("0", Integer.toString(i)), Control.CommandType.GENERIC, context);
        }
    }


    public static boolean isC0StateActive() {
        return Utils.readFile(C0STATE).equals("1");
    }

    public static boolean hasC0State () {
        return Utils.existFile(C0STATE);
    }

    public static void activateC1State (boolean active, Context context) {
        String path = C1STATE;
        for (int i = 0; i < CPU.getCoreCount(); i++ ) {
            Control.runCommand(active ? "1" : "0", path.replace("0", Integer.toString(i)), Control.CommandType.GENERIC, context);
        }
    }


    public static boolean isC1StateActive() {
        return Utils.readFile(C1STATE).equals("1");
    }

    public static boolean hasC1State () {
        return Utils.existFile(C1STATE);
    }

    public static void activateC2State (boolean active, Context context) {
        String path = C2STATE;
        for (int i = 0; i < CPU.getCoreCount(); i++ ) {
            Control.runCommand(active ? "1" : "0", path.replace("0", Integer.toString(i)), Control.CommandType.GENERIC, context);
        }
    }


    public static boolean isC2StateActive() {
        return Utils.readFile(C2STATE).equals("1");
    }

    public static boolean hasC2State () {
        return Utils.existFile(C2STATE);
    }

    public static void activateC3State (boolean active, Context context) {
        String path = C3STATE;
        for (int i = 0; i < CPU.getCoreCount(); i++ ) {
            Control.runCommand(active ? "1" : "0", path.replace("0", Integer.toString(i)), Control.CommandType.GENERIC, context);
        }
    }

    public static boolean isC3StateActive() {
        return Utils.readFile(C3STATE).equals("1");
    }

    public static boolean hasC3State () {
        return Utils.existFile(C3STATE);
    }

}
