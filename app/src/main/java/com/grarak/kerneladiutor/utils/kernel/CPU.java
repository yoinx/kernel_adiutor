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

package com.grarak.kerneladiutor.utils.kernel;

import android.content.Context;
import android.util.Log;

import com.grarak.kerneladiutor.R;
import com.grarak.kerneladiutor.utils.Constants;
import com.grarak.kerneladiutor.utils.Utils;
import com.grarak.kerneladiutor.utils.root.Control;
import com.kerneladiutor.library.root.RootUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by willi on 02.12.14.
 */
public class CPU implements Constants {

    private static int cores;
    // <core_number, type> Where types are:  0 = legacy, 1 = little, 2 = big
    public static HashMap<Integer, Integer> coretypes = new HashMap<>();
    public static int bigCore = -1;
    public static int LITTLEcore = -1;
    private static Integer[][] mFreqs;
    private static String[][] mAvailableGovernors;
    private static String[] mMcPowerSavingItems;
    private static String[] mAvailableCFSSchedulers;

    private static String TEMP_FILE;

    private static String[] mCpuQuietAvailableGovernors;

    private static String CPU_BOOST_ENABLE_FILE;

    private static String mMSMLimiterEnable;

    private static String getMSMLimiterTokenValueByCore (int core, String newvalue, String existing) {
        String newstring = "";
        if (core == -1) {
            for (int i = 0; i < CPU.cores;i++) {
                if (i == 0) {
                    newstring = i + ":" + newvalue + " ";
                }
                else {
                    newstring = newstring + i + ":" + newvalue + " ";
                }
            }
        } else if (core >= 0) {
            String[] parts = existing.split(" ");
            parts[core] = core + ":" + newvalue;
            for (int i = 0; i < parts.length; i++) {
                newstring = newstring + parts[i] + " ";
            }
        }
        if (newstring.endsWith(" ")) {
            newstring = newstring.substring(0, newstring.length() -1);
        }
        return newstring;
    }

    private static String getMSMLimiterToken (int core, String value) {
        String tokens[] = value.split(" ");
        if (core == -1) {
            return tokens[0].replace(0 + ":","");
        } else if (core >= 0) {
            return tokens[core].replace(core + ":","");
        }
        return "";
    }

    public static boolean hasAlu_T_Boost() {
        return Utils.existFile(ALU_T_BOOST);
    }
    public static boolean hasAlu_T_Boostms() {return Utils.existFile(ALU_T_BOOST_MS);}
    public static boolean hasAlu_T_Boostmii() {return Utils.existFile(ALU_T_BOOST_INTERVAL);}
    public static boolean hasAlu_T_Boostcpus() {return Utils.existFile(ALU_T_BOOST_CPUS);}
    public static boolean hasAlu_T_Boostfreq() {return Utils.existFile(ALU_T_BOOST_FREQ);}

    public static int getAlutBoostMs() {
        return Utils.stringToInt(Utils.readFile(ALU_T_BOOST_MS));
    }
    public static int getAlutBoostFreq() {
        String value = Utils.readFile(ALU_T_BOOST_FREQ);
        if (value.equals("0")) return 0;
        return CPU.getFreqs().indexOf(Utils.stringToInt(value)) + 1;
    }
    public static int getAlutBoostMii() {
        return Utils.stringToInt(Utils.readFile(ALU_T_BOOST_INTERVAL));
    }
    public static int getAlutBoostCpus() {
        return Utils.stringToInt(Utils.readFile(ALU_T_BOOST_CPUS));
    }
    public static void setAlutBoostFreq(int freq, Context context) {
        if (ALU_T_BOOST_FREQ != null)
            Control.runCommand(String.valueOf(freq), ALU_T_BOOST_FREQ, Control.CommandType.GENERIC, context);
    }
    public static void setAlutBoostMs(int value, Context context) {
        Control.runCommand(String.valueOf(value), ALU_T_BOOST_MS, Control.CommandType.GENERIC, context);
    }
    public static void setAlutBoostMii(int value, Context context) {
        Control.runCommand(String.valueOf(value), ALU_T_BOOST_INTERVAL, Control.CommandType.GENERIC, context);
    }
    public static void setAlutBoostCpus(int value, Context context) {
        Control.runCommand(String.valueOf(value), ALU_T_BOOST_CPUS, Control.CommandType.GENERIC, context);
    }

    public static void activateCpuTouchBoost(boolean active, Context context) {
        Control.runCommand(active ? "1" : "0", CPU_TOUCH_BOOST, Control.CommandType.GENERIC, context);
    }

    public static boolean isCpuTouchBoostEnabled() {
        return Utils.readFile(CPU_TOUCH_BOOST).equals("1");
    }

    public static boolean hasCpuTouchBoost() {
        return Utils.existFile(CPU_TOUCH_BOOST);
    }

    public static boolean hasCpuInputBoostEnable () {
        return Utils.existFile(INPUT_BOOST_ENABLE);
    }

    public static void activateCpuBoostWakeup(boolean active, Context context) {
        Control.runCommand(active ? "Y" : "N", CPU_BOOST_WAKEUP, Control.CommandType.GENERIC, context);
    }

    public static boolean isCpuBoostWakeupActive() {
        return Utils.readFile(CPU_BOOST_WAKEUP).equals("Y");
    }

    public static boolean isInputBoostActive() {
        return Utils.readFile(INPUT_BOOST_ENABLE).equals("1");
    }

    public static boolean hasCpuBoostWakeup() {
        return Utils.existFile(CPU_BOOST_WAKEUP);
    }

    public static void activateCpuInputBoost(boolean active, Context context) {
        Control.runCommand(active ? "1" : "0", INPUT_BOOST_ENABLE, Control.CommandType.GENERIC, context);
    }

    public static void activateCpuBoostHotplug(boolean active, Context context) {
        Control.runCommand(active ? "Y" : "N", CPU_BOOST_HOTPLUG, Control.CommandType.GENERIC, context);
    }

    public static boolean isCpuBoostHotplugActive() {
        return Utils.readFile(CPU_BOOST_HOTPLUG).equals("Y");
    }

    public static boolean hasCpuBoostHotplug() {
        return Utils.existFile(CPU_BOOST_HOTPLUG);
    }

    public static void setCpuBoostInputMs(int value, Context context) {
        Control.runCommand(String.valueOf(value), CPU_BOOST_INPUT_MS, Control.CommandType.GENERIC, context);
    }

    public static int getCpuBootInputMs() {
        return Utils.stringToInt(Utils.readFile(CPU_BOOST_INPUT_MS));
    }

    public static boolean hasCpuBoostInputMs() {
        return Utils.existFile(CPU_BOOST_INPUT_MS);
    }

    public static void setCpuBoostInputFreq(int value, int core, Context context) {
        if (Utils.readFile(CPU_BOOST_INPUT_BOOST_FREQ).contains(":")) {
            String existing = Utils.readFile(CPU_BOOST_INPUT_BOOST_FREQ), newvalues = "";
            String[] parts = existing.split(" ");
            parts[core] = core + ":" + value;
            for (int i = 0; i < parts.length; i++) {
                if (i < parts.length - 1) newvalues = newvalues + parts[i] + " ";
                else newvalues = newvalues + parts[i];

            }
            Control.runCommand(newvalues, CPU_BOOST_INPUT_BOOST_FREQ, Control.CommandType.GENERIC, context);
        }
        else
            Control.runCommand(String.valueOf(value), CPU_BOOST_INPUT_BOOST_FREQ, Control.CommandType.GENERIC, context);
    }

    public static List<Integer> getCpuBootInputFreq() {
        List<Integer> list = new ArrayList<>();
        String value = Utils.readFile(CPU_BOOST_INPUT_BOOST_FREQ);
        for (String core : value.split(" ")) {
            if (core.contains(":")) core = core.split(":")[1];
            if (core.equals("0")) list.add(0);
            else list.add(CPU.getFreqs().indexOf(Utils.stringToInt(core)) + 1);
        }
        return list;
    }

    public static boolean hasCpuBoostInputFreq() {
        return Utils.existFile(CPU_BOOST_INPUT_BOOST_FREQ);
    }

    public static void setCpuBoostSyncThreshold(int value, Context context) {
        Control.runCommand(String.valueOf(value), CPU_BOOST_SYNC_THRESHOLD, Control.CommandType.GENERIC, context);
    }

    public static int getCpuBootSyncThreshold() {
        String value = Utils.readFile(CPU_BOOST_SYNC_THRESHOLD);
        if (value.equals("0")) return 0;
        return CPU.getFreqs().indexOf(Utils.stringToInt(value)) + 1;
    }

    public static boolean hasCpuBoostSyncThreshold() {
        return Utils.existFile(CPU_BOOST_SYNC_THRESHOLD);
    }

    public static void setCpuBoostMs(int value, Context context) {
        Control.runCommand(String.valueOf(value), CPU_BOOST_MS, Control.CommandType.GENERIC, context);
    }

    public static int getCpuBootMs() {
        return Utils.stringToInt(Utils.readFile(CPU_BOOST_MS));
    }

    public static boolean hasCpuBoostMs() {
        return Utils.existFile(CPU_BOOST_MS);
    }

    public static void activateCpuBoostDebugMask(boolean active, Context context) {
        Control.runCommand(active ? "1" : "0", CPU_BOOST_DEBUG_MASK, Control.CommandType.GENERIC, context);
    }

    public static boolean isCpuBoostDebugMaskActive() {
        return Utils.readFile(CPU_BOOST_DEBUG_MASK).equals("1");
    }

    public static boolean hasCpuBoostDebugMask() {
        return Utils.existFile(CPU_BOOST_DEBUG_MASK);
    }

    public static void activateCpuBoost(boolean active, Context context) {
        String command = active ? "1" : "0";
        if (CPU_BOOST_ENABLE_FILE.equals(CPU_BOOST_ENABLE_2)) command = active ? "Y" : "N";
        Control.runCommand(command, CPU_BOOST_ENABLE_FILE, Control.CommandType.GENERIC, context);
    }

    public static boolean isCpuBoostActive() {
        String value = Utils.readFile(CPU_BOOST_ENABLE_FILE);
        return value.equals("1") || value.equals("Y");
    }

    public static boolean hasCpuBoostEnable() {
        if (Utils.existFile(CPU_BOOST_ENABLE)) CPU_BOOST_ENABLE_FILE = CPU_BOOST_ENABLE;
        else if (Utils.existFile(CPU_BOOST_ENABLE_2)) CPU_BOOST_ENABLE_FILE = CPU_BOOST_ENABLE_2;
        return CPU_BOOST_ENABLE_FILE != null;
    }

    public static boolean hasCpuBoost() {
        return Utils.existFile(CPU_BOOST);
    }

    public static boolean hasMSM_Limiter() {
        if (Utils.existFile(CPU_MSM_LIMITER_ENABLE)) {
            mMSMLimiterEnable = CPU_MSM_LIMITER_ENABLE;
            return true;
        }
        else if (Utils.existFile(CPU_MSM_LIMITER_ENABLE_NEW)) {
            mMSMLimiterEnable = CPU_MSM_LIMITER_ENABLE_NEW;
            return true;
        }
        return false;
    }

    public static void activateMSM_Limiter(boolean active, Context context) {
        if (active) {
            for (int i = 0; i < CPU.getCoreCount(); i++) {
                Control.deletespecificcommand(context, String.format(CPU_SCALING_GOVERNOR, i), null);
                Control.deletespecificcommand(context, String.format(CPU_SCALING_GOVERNOR, i) + "permission644", null);
                Control.deletespecificcommand(context, String.format(CPU_SCALING_GOVERNOR, i) + "permission444", null);
            }
            CPU.setMSMLimiterGovernor(CPU.getCurGovernor(true), context, -1);
        }
        else {
            Control.deletespecificcommand(context, CPU_MSM_LIMITER_SCALING_GOVERNOR, null);
            CPU.setGovernor(CPU.getMSMLimiterGovernor(-1), context);
        }
        Control.runCommand(active ? "1" : "0", mMSMLimiterEnable, Control.CommandType.GENERIC, context);
    }

    public static boolean isMSM_LimiterActive() {
        if (Utils.existFile(mMSMLimiterEnable)) {
            return Utils.readFile(mMSMLimiterEnable).equals("1");
        }
        else {
            return false;
        }
    }

    public static boolean hasMSM_Limiter_Version () {
        return Utils.existFile(CPU_MSM_LIMITER_VERSION);
    }

    public static Double getMSM_Limiter_Version () {
        return Utils.stringtodouble(Utils.readFile(CPU_MSM_LIMITER_VERSION).replace("version: ",""));
    }

    public static void setCpuQuietGovernor(String value, Context context) {
        Control.runCommand(value, CPU_QUIET_CURRENT_GOVERNOR, Control.CommandType.GENERIC, context);
    }

    public static String getCpuQuietCurGovernor() {
        return Utils.readFile(CPU_QUIET_CURRENT_GOVERNOR);
    }

    public static List<String> getCpuQuietAvailableGovernors() {
        if (mCpuQuietAvailableGovernors == null) {
            String[] governors = Utils.readFile(CPU_QUIET_AVAILABLE_GOVERNORS).split(" ");
            if (governors.length > 0) {
                mCpuQuietAvailableGovernors = new String[governors.length];
                System.arraycopy(governors, 0, mCpuQuietAvailableGovernors, 0, mCpuQuietAvailableGovernors.length);
            }
        }
        if (mCpuQuietAvailableGovernors == null) return null;
        return new ArrayList<>(Arrays.asList(mCpuQuietAvailableGovernors));
    }

    public static boolean hasCpuQuietGovernors() {
        return Utils.existFile(CPU_QUIET_AVAILABLE_GOVERNORS) && Utils.existFile(CPU_QUIET_CURRENT_GOVERNOR)
                && !Utils.readFile(CPU_QUIET_AVAILABLE_GOVERNORS).equals("none");
    }

    public static void activateCpuQuiet(boolean active, Context context) {
        Control.runCommand(active ? "1" : "0", CPU_QUIET_ENABLE, Control.CommandType.GENERIC, context);
    }

    public static boolean isCpuQuietActive() {
        return Utils.readFile(CPU_QUIET_ENABLE).equals("1");
    }

    public static boolean hasCpuQuietEnable() {
        return Utils.existFile(CPU_QUIET_ENABLE);
    }

    public static boolean hasCpuQuiet() {
        return Utils.existFile(CPU_QUIET);
    }

    public static boolean hasMSM_LimiterResumeMaxFreq() {
        return Utils.existFile(CPU_MSM_LIMITER_RESUME_MAX);
    }

    public static int getMSM_LimiterResumeMaxFreq (int core) {
        if (getMSM_Limiter_Version() < 5.2 ) {
            if (core == -1) {
                if (Utils.existFile(CPU_MSM_LIMITER_RESUME_MAX)) {
                    String value = Utils.readFile(CPU_MSM_LIMITER_RESUME_MAX);
                    if (value != null) return Utils.stringToInt(value);
                }
            } else if (core >= 0) {
                if (Utils.existFile(String.format(CPU_MAX_FREQ_PER_CORE, core))) {
                    String value = Utils.readFile(String.format(CPU_MAX_FREQ_PER_CORE, core));
                    if (value != null) return Utils.stringToInt(value);
                }
            }
        }
        else if (getMSM_Limiter_Version() > 5.1 ) {
            if (core == -1) {
                return Utils.stringToInt(getMSMLimiterToken(0, Utils.readFile(CPU_MSM_LIMITER_RESUME_MAX)));
            }
            if (core >= 0) {
                return Utils.stringToInt(getMSMLimiterToken(core, Utils.readFile(CPU_MSM_LIMITER_RESUME_MAX)));
            }
        }
        return 0;
    }

    public static void setMSM_LimiterResumeMaxFreq(int freq, Context context, int core) {
        if (getMSM_Limiter_Version() < 5.2) {
            if (core == -1) {
                Control.runCommand(String.valueOf(freq), CPU_MSM_LIMITER_RESUME_MAX, Control.CommandType.GENERIC, context);
            } else if (core >= 0) {
                String path = String.format(CPU_MAX_FREQ_PER_CORE, core);
                if (Utils.existFile(path))
                    Control.runCommand(Integer.toString(freq), path, Control.CommandType.GENERIC, context);
            }
        }
        else if (getMSM_Limiter_Version() > 5.1) {
            if (core == -1) {
                Control.runCommand(getMSMLimiterTokenValueByCore(-1,String.valueOf(freq),""), CPU_MSM_LIMITER_RESUME_MAX, Control.CommandType.GENERIC, context);
            }
            else if (core >= 0) {
                Control.runCommand(getMSMLimiterTokenValueByCore(core,String.valueOf(freq),Utils.readFile(CPU_MSM_LIMITER_RESUME_MAX)), CPU_MSM_LIMITER_RESUME_MAX, Control.CommandType.GENERIC, context);
            }
        }
    }

    public static boolean hasMSM_LimiterSuspendMaxFreq() {
            return Utils.existFile(CPU_MSM_LIMITER_SUSPEND_MAX);
    }

    public static int getMSM_LimiterSuspendMaxFreq (int core) {
        if (getMSM_Limiter_Version() < 5.2 ) {
            if (core == -1) {
                if (Utils.existFile(CPU_MSM_LIMITER_SUSPEND_MAX)) {
                    String value = Utils.readFile(CPU_MSM_LIMITER_SUSPEND_MAX);
                    if (value != null) return Utils.stringToInt(value);
                }
            }
            else if (core >= 0) {
                if (Utils.existFile(String.format(CPU_MIN_FREQ_PER_CORE, core))) {
                    String value = Utils.readFile(String.format(CPU_MIN_FREQ_PER_CORE, core));
                    if (value != null) return Utils.stringToInt(value);
                }
            }
        }
        else if (getMSM_Limiter_Version() > 5.1 ) {
            if (core == -1) {
                return Utils.stringToInt(getMSMLimiterToken(0, Utils.readFile(CPU_MSM_LIMITER_SUSPEND_MAX)));
            }
            if (core >= 0) {
                return Utils.stringToInt(getMSMLimiterToken(core, Utils.readFile(CPU_MSM_LIMITER_SUSPEND_MAX)));
            }
        }
        return 0;
    }

    public static void setMSM_LimiterSuspendMaxFreq(int freq, int core, Context context) {
        if (getMSM_Limiter_Version() < 5.2) {
            if (core == -1) {
                Control.runCommand(String.valueOf(freq), CPU_MSM_LIMITER_SUSPEND_MAX, Control.CommandType.GENERIC, context);
            }
        }
        else if (getMSM_Limiter_Version() > 5.1) {
            if (core == -1) {
                Control.runCommand(getMSMLimiterTokenValueByCore(-1,String.valueOf(freq),""), CPU_MSM_LIMITER_SUSPEND_MAX, Control.CommandType.GENERIC, context);
            }
            else if (core >= 0) {
                Control.runCommand(getMSMLimiterTokenValueByCore(core,String.valueOf(freq),Utils.readFile(CPU_MSM_LIMITER_SUSPEND_MAX)), CPU_MSM_LIMITER_SUSPEND_MAX, Control.CommandType.GENERIC, context);
            }
        }
    }

    public static boolean hasMSM_LimiterSuspendMinFreq() {
        return Utils.existFile(CPU_MSM_LIMITER_SUSPEND_MIN);
    }

    public static int getMSM_LimiterSuspendMinFreq (int core) {
        if (getMSM_Limiter_Version() < 5.2 ) {
            if (core == -1) {
                if (Utils.existFile(CPU_MSM_LIMITER_SUSPEND_MIN)) {
                    String value = Utils.readFile(CPU_MSM_LIMITER_SUSPEND_MIN);
                    if (value != null) return Utils.stringToInt(value);
                }
            }
            else if (core >= 0) {
                if (Utils.existFile(String.format(CPU_MIN_FREQ_PER_CORE, core))) {
                    String value = Utils.readFile(String.format(CPU_MIN_FREQ_PER_CORE, core));
                    if (value != null) return Utils.stringToInt(value);
                }
            }
        }
        else if (getMSM_Limiter_Version() > 5.1 ) {
            if (core == -1) {
                return Utils.stringToInt(getMSMLimiterToken(0, Utils.readFile(CPU_MSM_LIMITER_SUSPEND_MIN)));
            }
            if (core >= 0) {
                return Utils.stringToInt(getMSMLimiterToken(core, Utils.readFile(CPU_MSM_LIMITER_SUSPEND_MIN)));
            }
        }
        return 0;
    }

    public static void setMSM_LimiterSuspendMinFreq(int freq, int core, Context context) {
        if (getMSM_Limiter_Version() < 5.2 ) {
            if (core == -1) {
                Control.runCommand(String.valueOf(freq), CPU_MSM_LIMITER_SUSPEND_MIN, Control.CommandType.GENERIC, context);
            }
            else if (core > 0 ) {
                String path = String.format(CPU_MIN_FREQ_PER_CORE, core);
                if (Utils.existFile(path)) Control.runCommand(Integer.toString(freq), path, Control.CommandType.GENERIC, context);
            }
        }
        else if (getMSM_Limiter_Version() > 5.1 ) {
            if (core == -1) {
                Control.runCommand(getMSMLimiterTokenValueByCore(-1,String.valueOf(freq),""), CPU_MSM_LIMITER_SUSPEND_MIN, Control.CommandType.GENERIC, context);
            }
            else if (core >= 0) {
                Control.runCommand(getMSMLimiterTokenValueByCore(core,String.valueOf(freq),Utils.readFile(CPU_MSM_LIMITER_SUSPEND_MIN)), CPU_MSM_LIMITER_SUSPEND_MIN, Control.CommandType.GENERIC, context);
            }
        }
    }

    public static void setCFSScheduler(String value, Context context) {
        Control.runCommand(value, CPU_CURRENT_CFS_SCHEDULER, Control.CommandType.GENERIC, context);
    }

    public static String getCurrentCFSScheduler() {
        return Utils.readFile(CPU_CURRENT_CFS_SCHEDULER);
    }

    public static List<String> getAvailableCFSSchedulers() {
        if (mAvailableCFSSchedulers == null)
            mAvailableCFSSchedulers = Utils.readFile(CPU_AVAILABLE_CFS_SCHEDULERS).split(" ");
        return new ArrayList<>(Arrays.asList(mAvailableCFSSchedulers));
    }

    public static boolean hasCFSScheduler() {
        return Utils.existFile(CPU_AVAILABLE_CFS_SCHEDULERS) && Utils.existFile(CPU_CURRENT_CFS_SCHEDULER);
    }

    public static String[] getMcPowerSavingItems(Context context) {
        if (mMcPowerSavingItems == null && context != null)
            mMcPowerSavingItems = context.getResources().getStringArray(R.array.mc_power_saving_items);
        return mMcPowerSavingItems;
    }

    public static void setMcPowerSaving(int value, Context context) {
        Control.runCommand(String.valueOf(value), CPU_MC_POWER_SAVING, Control.CommandType.GENERIC, context);
    }

    public static int getCurMcPowerSaving() {
        return Utils.stringToInt(Utils.readFile(CPU_MC_POWER_SAVING));
    }

    public static boolean hasMcPowerSaving() {
        return Utils.existFile(CPU_MC_POWER_SAVING);
    }

    public static void activatePowerSavingWq(boolean active, Context context) {
        String command = active ? "Y" : "N";
        Control.runCommand(command, CPU_WQ_POWER_SAVING, Control.CommandType.GENERIC, context);
    }

    public static boolean isPowerSavingWqActive() {
        String value = Utils.readFile(CPU_WQ_POWER_SAVING);
        return value.equals("Y");
    }

    public static boolean hasPowerSavingWq() {
        return Utils.existFile(CPU_WQ_POWER_SAVING);
    }

    public static List<String> getAvailableGovernors() {
        return getAvailableGovernors(getBigCore());
    }

    public static List<String> getAvailableGovernors(int core) {
        if (mAvailableGovernors == null) mAvailableGovernors = new String[getCoreCount()][];
        if (mAvailableGovernors[core] == null) {
            String value = Utils.readFile(CPU_AVAILABLE_GOVERNORS);
            if (value != null) {
                mAvailableGovernors[core] = value.split(" ");
                Collections.sort(Arrays.asList(mAvailableGovernors[core]), String.CASE_INSENSITIVE_ORDER);
            }
        }
        if (mAvailableGovernors[core] == null) return null;
        return new ArrayList<>(Arrays.asList(mAvailableGovernors[core]));
    }

    public static boolean isPerCoreControlActive (Context context) {
        return Utils.getBoolean("MSM_Limiter_Per_Core_Control", false, context);
    }

    public static boolean hasPerCoreControl () {
        return hasMSM_Limiter();
    }


    public static void activatePerCoreControl(boolean active, Context context) {
        Utils.saveBoolean("MSM_Limiter_Per_Core_Control", active, context);
        if (active) {
            Control.deletespecificcommand(context, CPU_MSM_LIMITER_SCALING_GOVERNOR, null);
            for (int i = 0; i < CPU.getCoreCount(); i++) {
                CPU.setMSMLimiterGovernor(CPU.getMSMLimiterGovernor(-1), context, i);
            }
        }
        else {
            for (int i = 0; i < CPU.getCoreCount(); i++) {
                Control.deletespecificcommand(context, String.format(CPU_MSM_LIMITER_SCALING_GOVERNOR_PER_CORE, i), null);
            }
            CPU.setMSMLimiterGovernor(CPU.getMSMLimiterGovernor(0), context, -1);
        }
    }

    public static void setGovernor(String governor, Context context) {
        setGovernor(Control.CommandType.CPU, governor, context);
    }

    public static void setGovernor(Control.CommandType command, String governor, Context context) {
        Control.runCommand(governor, CPU_SCALING_GOVERNOR, command, context);
    }

    public static void setMSMLimiterGovernor(String governor, Context context, int core) {
        if (getMSM_Limiter_Version() < 5.2) {
            if ( core == -1) {
                Control.runCommand(governor, CPU_MSM_LIMITER_SCALING_GOVERNOR, Control.CommandType.GENERIC, context);
            } else if ( core >= 0) {
                Control.runCommand(governor, String.format(CPU_MSM_LIMITER_SCALING_GOVERNOR_PER_CORE, core), Control.CommandType.GENERIC, context);
            }
        }
        else if (getMSM_Limiter_Version() > 5.1) {
            String newgov = "";
            if (core == -1) {
               newgov = CPU.getMSMLimiterTokenValueByCore(-1, governor, "");
            } else if (core >= 0) {
                newgov = CPU.getMSMLimiterTokenValueByCore(core, governor, Utils.readFile(CPU_MSM_LIMITER_SCALING_GOVERNOR));
            }
            Control.runCommand(newgov, CPU_MSM_LIMITER_SCALING_GOVERNOR, Control.CommandType.GENERIC, context);
        }
    }

    public static String getCurGovernor(boolean forceRead) {
        return getCurGovernor(getBigCore(), forceRead);
    }

    public static String getCurGovernor(int core, boolean forceRead) {
        if (forceRead && core > 0)
            while (!Utils.existFile(String.format(CPU_SCALING_GOVERNOR, core)))
                activateCore(core, true, null);
        if (Utils.existFile(String.format(CPU_SCALING_GOVERNOR, core))) {
            String value = Utils.readFile(String.format(CPU_SCALING_GOVERNOR, core));
            if (value != null) return value;
        }
        return "";
    }

    public static String getMSMLimiterGovernor (int core) {
        if (Utils.existFile(CPU_MSM_LIMITER_SCALING_GOVERNOR)) {
            String value = Utils.readFile(CPU_MSM_LIMITER_SCALING_GOVERNOR);
            if (getMSM_Limiter_Version() < 5.2) {
                if (core == -1){
                    if (value != null) {
                        return value;
                    }
                } else if ( core >= 0) {
                    if (Utils.existFile(String.format(CPU_MSM_LIMITER_SCALING_GOVERNOR_PER_CORE, core))) {
                        value = Utils.readFile(String.format(CPU_MSM_LIMITER_SCALING_GOVERNOR_PER_CORE, core));
                        if (value != null) return value;
                    }
                }
            } else if (getMSM_Limiter_Version() > 5.1) {
                if (core == -1) {
                    return getMSMLimiterToken(0, value);
                } else if (core >= 0) {
                    return getMSMLimiterToken(core, value);
                }
            }
        }
        return "";
    }

    public static List<Integer> getFreqs() {
        return getFreqs(getBigCore());
    }

    public static List<Integer> getFreqs(int core) {
        if (mFreqs == null) mFreqs = new Integer[getCoreCount()][];
        if (mFreqs[core] == null) {
            if (Utils.existFile(Utils.getsysfspath(CPU_TIME_IN_STATE_ARRAY, core)) || Utils.existFile(Utils.getsysfspath(CPU_TIME_IN_STATE_ARRAY, 0))) {
                if (core > 0) {
                    activateCore(core, true, null);
                }
                String file;
                if (Utils.existFile(Utils.getsysfspath(CPU_TIME_IN_STATE_ARRAY, core))) {
                    file = Utils.getsysfspath(CPU_TIME_IN_STATE_ARRAY, core);
                } else {
                    file = Utils.getsysfspath(CPU_TIME_IN_STATE_ARRAY, 0);
                }
                String values;
                if ((values = Utils.readFile(file)) != null) {
                    String[] valueArray = values.split("\\r?\\n");
                    mFreqs[core] = new Integer[valueArray.length];
                    for (int i = 0; i < mFreqs[core].length; i++)
                        mFreqs[core][i] = Utils.stringToInt(valueArray[i].split(" ")[0]);
                }
            } else if (Utils.existFile(String.format(CPU_AVAILABLE_FREQS, 0))) {
                if (core > 0) {
                    while (!Utils.existFile(String.format(CPU_AVAILABLE_FREQS, core)))
                        activateCore(core, true, null);
                }
                String values;
                if ((values = Utils.readFile(String.format(CPU_AVAILABLE_FREQS, core))) != null) {
                    String[] valueArray = values.split(" ");
                    mFreqs[core] = new Integer[valueArray.length];
                    for (int i = 0; i < mFreqs[core].length; i++)
                        mFreqs[core][i] = Utils.stringToInt(valueArray[i]);
                }
            }
        }
        if (mFreqs[core] == null) {
            return Collections.emptyList();
        }
        List<Integer> freqs = Arrays.asList(mFreqs[core]);
        Collections.sort(freqs);
        return freqs;
    }

    public static void setMaxScreenOffFreq(int freq, Context context) {
        setMaxScreenOffFreq(Control.CommandType.CPU, freq, context);
    }

    public static void setMaxScreenOffFreq(Control.CommandType command, int freq, Context context) {
        Control.runCommand(String.valueOf(freq), CPU_MAX_SCREEN_OFF_FREQ, command, context);
    }

    public static int getMaxScreenOffFreq(boolean forceRead) {
        return getMaxScreenOffFreq(getBigCore(), forceRead);
    }

    public static int getMaxScreenOffFreq(int core, boolean forceRead) {
        if (forceRead && core > 0)
            while (!Utils.existFile(String.format(CPU_MAX_SCREEN_OFF_FREQ, core)))
                activateCore(core, true, null);
        if (Utils.existFile(String.format(CPU_MAX_SCREEN_OFF_FREQ, core))) {
            String value = Utils.readFile(String.format(CPU_MAX_SCREEN_OFF_FREQ, core));
            if (value != null) return Utils.stringToInt(value);
        }
        return 0;
    }

    public static boolean hasMaxScreenOffFreq() {
        return Utils.existFile(String.format(CPU_MAX_SCREEN_OFF_FREQ, 0));
    }

    public static void setMinFreq(int freq, Context context) {
        setMinFreq(Control.CommandType.CPU, freq, context);
    }

    public static void setMinFreq(Control.CommandType command, int freq, Context context) {
        if (getMaxFreq(command == Control.CommandType.CPU ? getBigCore() : getLITTLEcore(), true) < freq)
            setMaxFreq(command, freq, context);
        Control.runCommand(String.valueOf(freq), CPU_MIN_FREQ, command, context);
    }

     public static int getMinFreq(boolean forceRead) {
        return getMinFreq(getBigCore(), forceRead);
    }

    public static int getMinFreq(int core, boolean forceRead) {
        if (forceRead && core > 0) while (!Utils.existFile(String.format(CPU_MIN_FREQ, core)))
            activateCore(core, true, null);
        if (Utils.existFile(String.format(CPU_MIN_FREQ, core))) {
            String value = Utils.readFile(String.format(CPU_MIN_FREQ, core));
            if (value != null) return Utils.stringToInt(value);
        }
        return 0;
    }

    public static void setMaxFreq(int freq, Context context) {
        setMaxFreq(Control.CommandType.CPU, freq, context);
    }

    public static void setMaxFreq(Control.CommandType command, int freq, Context context) {
        if (command == Control.CommandType.CPU && Utils.existFile(CPU_MSM_CPUFREQ_LIMIT)
                && freq > Utils.stringToInt(Utils.readFile(CPU_MSM_CPUFREQ_LIMIT)))
            Control.runCommand(String.valueOf(freq), CPU_MSM_CPUFREQ_LIMIT, Control.CommandType.GENERIC, context);
        if (Utils.existFile(String.format(CPU_ENABLE_OC, 0)))
            Control.runCommand("1", CPU_ENABLE_OC, Control.CommandType.CPU, context);
        if (getMinFreq(command == Control.CommandType.CPU ? getBigCore() : getLITTLEcore(), true) > freq)
            setMinFreq(command, freq, context);
        if (Utils.existFile(String.format(CPU_MAX_FREQ_KT, 0)))
            Control.runCommand(String.valueOf(freq), CPU_MAX_FREQ_KT, command, context);
        else Control.runCommand(String.valueOf(freq), CPU_MAX_FREQ, command, context);
    }

    public static int getMaxFreq(boolean forceRead) {
        return getMaxFreq(getBigCore(), forceRead);
    }

    public static int getMaxFreq(int core, boolean forceRead) {
        if (forceRead && core > 0) while (!Utils.existFile(String.format(CPU_MAX_FREQ, core)))
            activateCore(core, true, null);
        if (forceRead && core > 0 && Utils.existFile(String.format(CPU_MAX_FREQ_KT, 0)))
            while (!Utils.existFile(String.format(CPU_MAX_FREQ_KT, core)))
                activateCore(core, true, null);

        if (Utils.existFile(String.format(CPU_MAX_FREQ_KT, core))) {
            String value = Utils.readFile(String.format(CPU_MAX_FREQ_KT, core));
            if (value != null) return Utils.stringToInt(value);
        }
        if (Utils.existFile(String.format(CPU_MAX_FREQ, core))) {
            String value = Utils.readFile(String.format(CPU_MAX_FREQ, core));
            if (value != null) return Utils.stringToInt(value);
        }
        return 0;
    }

    public static int getCurFreq(int core) {
        if (Utils.existFile(String.format(CPU_CUR_FREQ, core))) {
            String value = Utils.readFile(String.format(CPU_CUR_FREQ, core));
            if (value != null) return Utils.stringToInt(value);
        }
        return 0;
    }

    public static void onlineAllCores(Context context) {
        for (int i = 1; i < getCoreCount(); i++) activateCore(i, true, context);
    }

    public static void activateCore(int core, boolean active, Context context) {
        if (context != null)
            Control.runCommand(active ? "1" : "0", String.format(CPU_CORE_ONLINE, core), Control.CommandType.GENERIC, context);
        else
            RootUtils.runCommand(String.format("echo %s > " + String.format(CPU_CORE_ONLINE, core), active ? "1" : "0"));
    }

    public static List<Integer> getLITTLECoreRange() {
        List<Integer> list = new ArrayList<>();
        if (!isBigLITTLE()) for (int i = 0; i < getCoreCount(); i++) list.add(i);
        else if (getLITTLEcore() == 0) for (int i = 0; i < bigCore; i++) list.add(i);
        else for (int i = getLITTLEcore(); i < getCoreCount(); i++) list.add(i);
        return list;
    }

    public static List<Integer> getBigCoreRange() {
        List<Integer> list = new ArrayList<>();
        if (!isBigLITTLE()) for (int i = 0; i < getCoreCount(); i++) list.add(i);
        else if (getBigCore() == 0) for (int i = 0; i < LITTLEcore; i++) list.add(i);
        else for (int i = getBigCore(); i < getCoreCount(); i++) list.add(i);
        return list;
    }

    public static int getLITTLEcore() {
        isBigLITTLE();
        return LITTLEcore == -1 ? 0 : LITTLEcore;
    }

    public static int getBigCore() {
        isBigLITTLE();
        return bigCore == -1 ? 0 : bigCore;
    }

    public static boolean isBigLITTLE() {
        if (bigCore == -1 || LITTLEcore == -1) {
            int cores = getCoreCount();

            List<Integer> cpu0Freqs = getFreqs(0);
            List<Integer> cpu2Freqs;
            List<Integer> cpu4Freqs;

            if(cores > 4) {
                cpu4Freqs = getFreqs(4);
                if (cpu0Freqs.size() > cpu4Freqs.size()) {
                    bigCore = 0;
                    LITTLEcore = 4;
                } else {
                    bigCore = 4;
                    LITTLEcore = 0;
                }
            } else if(cores > 3) {
                // If system has 4 cores and is armV7 assume it is not big.little
                if (System.getProperty("os.arch").toLowerCase().contains("armv7")) {
                    return false;
                }
                cpu2Freqs = getFreqs(2);
                if (cpu0Freqs.size() > cpu2Freqs.size()) {
                    bigCore = 0;
                    LITTLEcore = 2;
                } else {
                    bigCore = 2;
                    LITTLEcore = 0;
                }
            } else if(cores <= 2) {
                return false;
            }
        }
        return bigCore != -1 && LITTLEcore != -1;
    }

    public static int getCoreCount() {
        return cores == 0 ? cores = Runtime.getRuntime().availableProcessors() : cores;
    }

    public static String getTemp() {
        double temp = Utils.stringToLong(Utils.readFile(TEMP_FILE));
        if (temp > 1000) temp /= 1000;
        else if (temp > 200) temp /= 10;
        return Utils.formatCelsius(temp) + " " + Utils.celsiusToFahrenheit(temp);
    }

    public static boolean hasTemp() {
        if (Utils.existFile(CPU_TEMP_ZONE1)) {
            int temp = Utils.stringToInt(Utils.readFile(CPU_TEMP_ZONE1));
            if (temp > -1 && temp < 1000000) {
                TEMP_FILE = CPU_TEMP_ZONE1;
                return true;
            }
        }
        if (Utils.existFile(CPU_TEMP_ZONE0)) TEMP_FILE = CPU_TEMP_ZONE0;
        return TEMP_FILE != null;
    }

    public static float[] getCpuUsage() {
        try {
            Usage[] usage1 = getUsages();
            Thread.sleep(1000);
            Usage[] usage2 = getUsages();

            if (usage1 != null && usage2 != null) {
                float[] pers = new float[usage1.length];
                for (int i = 0; i < usage1.length; i++) {
                    long idle1 = usage1[i].getIdle();
                    long up1 = usage1[i].getUptime();

                    long idle2 = usage2[i].getIdle();
                    long up2 = usage2[i].getUptime();

                    float cpu = -1f;
                    if (idle1 >= 0 && up1 >= 0 && idle2 >= 0 && up2 >= 0) {
                        if ((up2 + idle2) > (up1 + idle1) && up2 >= up1) {
                            cpu = (up2 - up1) / (float) ((up2 + idle2) - (up1 + idle1));
                            cpu *= 100.0f;
                        }
                    }

                    pers[i] = cpu > -1 ? cpu : 0;
                }
                return pers;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Usage[] getUsages() {
        try {
            RandomAccessFile reader = new RandomAccessFile("/proc/stat", "r");
            Usage[] usage = new Usage[getCoreCount() + 1];
            for (int i = 0; i < usage.length; i++)
                usage[i] = new Usage(reader.readLine());
            reader.close();
            return usage;
        } catch (FileNotFoundException e) {
            Log.i(TAG, "/proc/stat does not exist");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class Usage {

        private long[] stats;

        public Usage(String stats) {
            if (stats == null) return;

            String[] values = stats.replace("  ", " ").split(" ");
            this.stats = new long[values.length - 1];
            for (int i = 0; i < this.stats.length; i++)
                this.stats[i] = Utils.stringToLong(values[i + 1]);
        }

        public long getUptime() {
            if (stats == null) return -1L;
            long l = 0L;
            for (int i = 0; i < stats.length; i++)
                if (i != 3) l += stats[i];
            return l;
        }

        public long getIdle() {
            try {
                return stats == null ? -1L : stats[3];
            } catch (ArrayIndexOutOfBoundsException e) {
                return -1L;
            }
        }
    }

    public static boolean isCoreOnline (int core) {
        return Utils.readFile(String.format(CPU_CORE_ONLINE, core)).equals("1");
    }

    /* Function to parse /proc/cpuinfo to get CPU Part Nums.
    I intend to use this to compare to a pre-set list of PartNumbers
    in order to determine whether a core is big/little/legacy
    */
    public static void checkCPUPartnum() {
        try {
            FileReader filereader = new FileReader("/proc/cpuinfo");
            BufferedReader buffreader = new BufferedReader(filereader);
            if (buffreader != null) {
                String line;
                String[] linePieces;
                int core = 0;
                while ((line = buffreader.readLine()) != null) {
                    line = line.replace("\t", "").replace(":", "");
                    linePieces = line.split(" ");
                    if (linePieces.length == 3 && linePieces[1].equals("part")) {
                        if (Arrays.asList(CPU_BIG_PARTS).contains(linePieces[2])) {
                            coretypes.put(core, 2);
                        } else if (Arrays.asList(CPU_LITTLE_PARTS).contains(linePieces[2])) {
                            coretypes.put(core, 1);
                        } else {
                            coretypes.put(core, 0);
                        }
                        core++;
                    }
                }
                filereader.close();
                buffreader.close();
            }
        } catch (Exception ex) {
            Log.w(TAG, "Error Parsing: /proc/cpuinfo");
            ex.printStackTrace();
        }
    }

}
