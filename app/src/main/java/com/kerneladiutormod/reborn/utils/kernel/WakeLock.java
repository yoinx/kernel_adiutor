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
import android.util.Log;

import com.kerneladiutormod.reborn.utils.Constants;
import com.kerneladiutormod.reborn.utils.Utils;
import com.kerneladiutormod.reborn.utils.root.Control;

/**
 * Created by willi on 27.12.14.
 */
public class WakeLock implements Constants {

    private static String SMB135X_WAKELOCK_FILE;
    private static String WLAN_RX_WAKELOCK_FILE;
    private static String WLAN_CTRL_WAKELOCK_FILE;
    private static String WLAN_WAKELOCK_FILE;

    public static boolean hasAnyWakelocks () {
        for (int i = 0; i < WAKELOCK_ARRAY.length; i++) {
            for (int x = 0; x  < WAKELOCK_ARRAY[i].length; x++) {
                if (Utils.existFile(WAKELOCK_ARRAY[i][x])) {
                    return true;
                }
            }
        }
        return false;
    }


    public static void setMsmHsicWakelockDivider(int value, Context context) {
        String command = String.valueOf(value);
        Control.runCommand(command, MSM_HSIC_WAKELOCK_DIVIDER, Control.CommandType.GENERIC, context);
        // Delay 100ms to allow Control.runCommand chain to run
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        if (value != (getMsmHsicWakelockDivider()) ) {
            Log.i(TAG, "Divider: " + (getMsmHsicWakelockDivider()));
            Utils.toast("Sorry, your kernel does not support this value. Please choose another.", context);
        }
    }

    public static int getMsmHsicWakelockDivider() {
        int value = Utils.stringToInt(Utils.readFile(MSM_HSIC_WAKELOCK_DIVIDER));
        return value;
    }

    public static boolean hasMsmHsicWakelockDivider() {
        return Utils.existFile(MSM_HSIC_WAKELOCK_DIVIDER);
    }

    public static void setWlanrxWakelockDivider(int value, Context context) {
        String command = String.valueOf(value + 1);
        if (value == 15) command = "0";
        Control.runCommand(command, WLAN_RX_WAKELOCK_DIVIDER, Control.CommandType.GENERIC, context);
    }

    public static int getWlanrxWakelockDivider() {
        int value = Utils.stringToInt(Utils.readFile(WLAN_RX_WAKELOCK_DIVIDER));
        if (value == 0) value = 16;
        return value - 1;
    }

    public static boolean hasWlanrxWakelockDivider() {
        return Utils.existFile(WLAN_RX_WAKELOCK_DIVIDER);
    }

    public static void setBCMDHDWakelockDivider(int value, Context context) {
        String command = String.valueOf(value + 1);
        Control.runCommand(command, BCMDHD_WAKELOCK_DIVIDER, Control.CommandType.GENERIC, context);
    }

    public static int getBCMDHDWakelockDivider() {
        int value = Utils.stringToInt(Utils.readFile(BCMDHD_WAKELOCK_DIVIDER));
        return value - 1;
    }

    public static boolean hasBCMDHDWakelockDivider() {
        return Utils.existFile(BCMDHD_WAKELOCK_DIVIDER);
    }


    public static void activateWlanWakeLock(boolean active, Context context) {
        Control.runCommand(active ? "Y" : "N", WLAN_WAKELOCK_FILE, Control.CommandType.GENERIC, context);
    }

    public static boolean isWlanWakeLockActive() {
        return Utils.readFile(WLAN_WAKELOCK_FILE).equals("Y");
    }

    public static boolean hasWlanWakeLock() {
        for (String file : WLAN_WAKELOCKS)
            if (Utils.existFile(file)) {
                WLAN_WAKELOCK_FILE = file;
                return true;
            }
        return false;
    }

    public static void activateWlanctrlWakeLock(boolean active, Context context) {
        Control.runCommand(active ? "Y" : "N", WLAN_CTRL_WAKELOCK_FILE, Control.CommandType.GENERIC, context);
    }

    public static boolean isWlanctrlWakeLockActive() {
        return Utils.readFile(WLAN_CTRL_WAKELOCK_FILE).equals("Y");
    }

    public static boolean hasWlanctrlWakeLock() {
        for (String file : WLAN_CTRL_WAKELOCKS)
            if (Utils.existFile(file)) {
                WLAN_CTRL_WAKELOCK_FILE = file;
                return true;
            }
        return false;
    }

    public static void activateWlanrxWakeLock(boolean active, Context context) {
        Control.runCommand(active ? "Y" : "N", WLAN_RX_WAKELOCK_FILE, Control.CommandType.GENERIC, context);
    }

    public static boolean isWlanrxWakeLockActive() {
        return Utils.readFile(WLAN_RX_WAKELOCK_FILE).equals("Y");
    }

    public static boolean hasWlanrxWakeLock() {
        for (String file : WLAN_RX_WAKELOCKS)
            if (Utils.existFile(file)) {
                WLAN_RX_WAKELOCK_FILE = file;
                return true;
            }
        return false;
    }

    public static void activateMsmHsicHostWakeLock(boolean active, Context context) {
        Control.runCommand(active ? "Y" : "N", MSM_HSIC_HOST_WAKELOCK, Control.CommandType.GENERIC, context);
    }

    public static boolean isMsmHsicHostWakeLockActive() {
        return Utils.readFile(MSM_HSIC_HOST_WAKELOCK).equals("Y");
    }

    public static boolean hasMsmHsicHostWakeLock() {
        return Utils.existFile(MSM_HSIC_HOST_WAKELOCK);
    }

    public static void activateBlueSleepWakeLock(boolean active, Context context) {
        Control.runCommand(active ? "Y" : "N", BLUESLEEP_WAKELOCK, Control.CommandType.GENERIC, context);
    }

    public static boolean isBlueSleepWakeLockActive() {
        return Utils.readFile(BLUESLEEP_WAKELOCK).equals("Y");
    }

    public static boolean hasBlueSleepWakeLock() {
        return Utils.existFile(BLUESLEEP_WAKELOCK);
    }



    public static void activateSensorIndWakeLock(boolean active, Context context) {
        Control.runCommand(active ? "Y" : "N", SENSOR_IND_WAKELOCK, Control.CommandType.GENERIC, context);
    }

    public static boolean isSensorIndWakeLockActive() {
        return Utils.readFile(SENSOR_IND_WAKELOCK).equals("Y");
    }

    public static boolean hasSensorIndWakeLock() {
        return Utils.existFile(SENSOR_IND_WAKELOCK);
    }

    public static void activateSmb135xWakeLock(boolean active, Context context) {
        Control.runCommand(active ? "Y" : "N", SMB135X_WAKELOCK_FILE, Control.CommandType.GENERIC, context);
    }

    public static boolean isSmb135xWakeLockActive() {
        return Utils.readFile(SMB135X_WAKELOCK_FILE).equals("Y");
    }

    public static boolean hasSmb135xWakeLock() {
        for (String file : SMB135X_WAKELOCKS)
            if (Utils.existFile(file)) {
                SMB135X_WAKELOCK_FILE = file;
                return true;
            }
        return false;
    }

}
