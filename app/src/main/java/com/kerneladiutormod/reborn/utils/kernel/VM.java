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
 * Created by willi on 27.12.14.
 */
public class VM implements Constants {

    public static void setZRAMDisksize(final int value, final Context context) {
        int size = value * 1024 * 1024;
        Control.runCommand("swapoff " + ZRAM_BLOCK + " > /dev/null 2>&1", ZRAM_BLOCK, Control.CommandType.CUSTOM, "swapoff", context);
        Control.runCommand("1", ZRAM_RESET, Control.CommandType.GENERIC, context);
        if (size != 0) {
            Control.runCommand(String.valueOf(size), ZRAM_DISKSIZE, Control.CommandType.GENERIC, context);
            Control.runCommand("mkswap " + ZRAM_BLOCK + " > /dev/null 2>&1", ZRAM_BLOCK,
                    Control.CommandType.CUSTOM, "mkswap", context);
            Control.runCommand("swapon " + ZRAM_BLOCK + " > /dev/null 2>&1", ZRAM_BLOCK,
                    Control.CommandType.CUSTOM, "swapon", context);
        }
    }

    public static int getZRAMDisksize() {
        return Utils.stringToInt(Utils.readFile(ZRAM_DISKSIZE)) / 1024 / 1024;
    }

    public static boolean hasZRAM() {
        return Utils.existFile(ZRAM);
    }

    public static boolean hasDirtyRatio() {
        if (Utils.existFile(VM_DIRTY_RATIO)) return true;
        return false;
    }

    public static void setDirtyRatio(int value, Context context) {
        Control.runCommand(String.valueOf(value), VM_DIRTY_RATIO, Control.CommandType.GENERIC, context);
    }

    public static int getDirtyRatio() {
        return Utils.stringToInt(Utils.readFile(VM_DIRTY_RATIO));
    }

    public static boolean hasDirtyBackgroundRatio() {
        if (Utils.existFile(VM_DIRTY_BACKGROUND_RATIO)) return true;
        return false;
    }

    public static void setDirtyBackgroundRatio(int value, Context context) {
        Control.runCommand(String.valueOf(value), VM_DIRTY_BACKGROUND_RATIO, Control.CommandType.GENERIC, context);
    }

    public static int getDirtyBackgroundRatio() {
        return Utils.stringToInt(Utils.readFile(VM_DIRTY_BACKGROUND_RATIO));
    }

    public static boolean hasDirtyExpire() {
        if (Utils.existFile(VM_DIRTY_EXPIRE_CENTISECS)) return true;
        return false;
    }

    public static void setDirtyExpire(int value, Context context) {
        Control.runCommand(String.valueOf(value), VM_DIRTY_EXPIRE_CENTISECS, Control.CommandType.GENERIC, context);
    }

    public static int getDirtyExpire() {
        return Utils.stringToInt(Utils.readFile(VM_DIRTY_EXPIRE_CENTISECS));
    }

    public static boolean hasDirtyWriteback() {
        if (Utils.existFile(VM_DIRTY_WRITEBACK_CENTISECS)) return true;
        return false;
    }

    public static void setDirtyWriteback(int value, Context context) {
        Control.runCommand(String.valueOf(value), VM_DIRTY_WRITEBACK_CENTISECS, Control.CommandType.GENERIC, context);
    }

    public static int getDirtyWriteback() {
        return Utils.stringToInt(Utils.readFile(VM_DIRTY_WRITEBACK_CENTISECS));
    }

    public static boolean hasOverCommitRatio() {
        if (Utils.existFile(VM_DIRTY_RATIO)) return true;
        return false;
    }

    public static void setOverCommitRatio(int value, Context context) {
        Control.runCommand(String.valueOf(value), VM_OVERCOMMIT_RATIO, Control.CommandType.GENERIC, context);
    }

    public static int getOverCommitRatio() {
        return Utils.stringToInt(Utils.readFile(VM_OVERCOMMIT_RATIO));
    }

    public static boolean hasSwappiness() {
        if (Utils.existFile(VM_SWAPPINESS)) return true;
        return false;
    }

    public static void setSwappiness(int value, Context context) {
        Control.runCommand(String.valueOf(value), VM_SWAPPINESS, Control.CommandType.GENERIC, context);
    }

    public static int getSwappiness() {
        return Utils.stringToInt(Utils.readFile(VM_SWAPPINESS));
    }

    public static boolean hasVFSCachePressure() {
        if (Utils.existFile(VM_VFS_CACHE_PRESSURE)) return true;
        return false;
    }

    public static void setVFSCachePressure(int value, Context context) {
        Control.runCommand(String.valueOf(value), VM_VFS_CACHE_PRESSURE, Control.CommandType.GENERIC, context);
    }

    public static int getVFSCachePressure() {
        return Utils.stringToInt(Utils.readFile(VM_VFS_CACHE_PRESSURE));
    }

    public static boolean hasLaptopMode() {
        if (Utils.existFile(VM_LAPTOP_MODE)) return true;
        return false;
    }

    public static void activateLaptopMode(boolean active, Context context) {
        Control.runCommand(active ? "1" : "0", VM_LAPTOP_MODE, Control.CommandType.GENERIC, context);
    }

    public static boolean isLaptopModeActive() {
        return Utils.readFile(VM_LAPTOP_MODE).equals("1");
    }

    public static boolean hasMinFreeKbytes() {
        if (Utils.existFile(VM_MIN_FREE_KBYTES)) return true;
        return false;
    }

    public static void setMinFreeKbytes(String value, Context context) {
        Control.runCommand(value, VM_MIN_FREE_KBYTES, Control.CommandType.GENERIC, context);
    }

    public static String getMinFreeKbytes() {
        String value = Utils.readFile(VM_MIN_FREE_KBYTES);
        if (value != null) return value;
        return null;
    }

    public static boolean hasExtraFreeKbytes() {
        if (Utils.existFile(VM_EXTRA_FREE_KBYTES)) return true;
        return false;
    }

    public static void setExtraFreeKbytes(String value, Context context) {
        Control.runCommand(value, VM_EXTRA_FREE_KBYTES, Control.CommandType.GENERIC, context);
    }

    public static String getExtraFreeKbytes() {
        String value = Utils.readFile(VM_EXTRA_FREE_KBYTES);
        if (value != null) return value;
        return null;
    }

    public static boolean hasDynamicDirtyWriteback() {
        if (Utils.existFile(VM_DYNAMIC_DIRTY_WRITEBACK)) return true;
        return false;
    }

    public static void activateDynamicDirtyWriteback(boolean active, Context context) {
        Control.runCommand(active ? "1" : "0", VM_DYNAMIC_DIRTY_WRITEBACK, Control.CommandType.GENERIC, context);
    }

    public static boolean isDynamicDirtyWritebackActive() {
        return Utils.readFile(VM_DYNAMIC_DIRTY_WRITEBACK).equals("1");
    }

    public static boolean hasDirtySuspendWriteback() {
        if (Utils.existFile(VM_DIRTY_WRITEBACK_SUSPEND_CENTISECS)) return true;
        return false;
    }

    public static void setDirtySuspendWriteback(int value, Context context) {
        Control.runCommand(String.valueOf(value), VM_DIRTY_WRITEBACK_SUSPEND_CENTISECS, Control.CommandType.GENERIC, context);
    }

    public static int getDirtySuspendWriteback() {
        return Utils.stringToInt(Utils.readFile(VM_DIRTY_WRITEBACK_SUSPEND_CENTISECS));
    }

    public static boolean hasDirtyActiveWriteback() {
        if (Utils.existFile(VM_DIRTY_WRITEBACK_ACTIVE_CENTISECS)) return true;
        return false;
    }

    public static void setDirtyActiveWriteback(int value, Context context) {
        Control.runCommand(String.valueOf(value), VM_DIRTY_WRITEBACK_ACTIVE_CENTISECS, Control.CommandType.GENERIC, context);
    }

    public static int getDirtyActiveWriteback() {
        return Utils.stringToInt(Utils.readFile(VM_DIRTY_WRITEBACK_ACTIVE_CENTISECS));
    }

}
