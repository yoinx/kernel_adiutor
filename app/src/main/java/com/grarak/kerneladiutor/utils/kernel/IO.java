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

import com.grarak.kerneladiutor.utils.Constants;
import com.grarak.kerneladiutor.utils.Utils;
import com.grarak.kerneladiutor.utils.root.Control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by willi on 26.12.14.
 */
public class IO implements Constants {

    public enum StorageType {
        INTERNAL, INTERNAL_SDA, INTERNAL_DM0, EXTERNAL
    }

    public static void setReadahead(StorageType type, int readahead, Context context) {
        String file;

        if (type == StorageType.INTERNAL)
            file = IO_INTERNAL_READ_AHEAD;
        else if (type == StorageType.INTERNAL_DM0)
            file = IO_INTERNAL_READ_AHEAD_DM0;
        else if (type == StorageType.INTERNAL_SDA)
            file = IO_INTERNAL_READ_AHEAD_SDA;
        else
            file = IO_EXTERNAL_READ_AHEAD;

        if (file != null)
            Control.runCommand(String.valueOf(readahead), file, Control.CommandType.GENERIC, context);
    }

    public static int getReadahead(StorageType type) {
        String file;

        if (type == StorageType.INTERNAL)
            file = IO_INTERNAL_READ_AHEAD;
        else if (type == StorageType.INTERNAL_DM0)
            file = IO_INTERNAL_READ_AHEAD_DM0;
        else if (type == StorageType.INTERNAL_SDA)
            file = IO_INTERNAL_READ_AHEAD_SDA;
        else
            file = IO_EXTERNAL_READ_AHEAD;

        if (Utils.existFile(file)) {
            String values = Utils.readFile(file);
            if (values != null) return Utils.stringToInt(values);
        }
        return 0;
    }

    public static void setScheduler(StorageType type, String scheduler, Context context) {
        String file;

        if (type == StorageType.INTERNAL)
            file = IO_INTERNAL_SCHEDULER;
        else if (type == StorageType.INTERNAL_DM0)
            file = IO_INTERNAL_SCHEDULER_DM0;
        else if (type == StorageType.INTERNAL_SDA)
            file = IO_INTERNAL_SCHEDULER_SDA;
        else
            file = IO_EXTERNAL_SCHEDULER;

        if (file != null)
            Control.runCommand(scheduler, file, Control.CommandType.GENERIC, context);
    }

    public static List<String> getSchedulers(StorageType type) {
        String file;

        if (type == StorageType.INTERNAL)
            file = IO_INTERNAL_SCHEDULER;
        else if (type == StorageType.INTERNAL_DM0)
            file = IO_INTERNAL_SCHEDULER_DM0;
        else if (type == StorageType.INTERNAL_SDA)
            file = IO_INTERNAL_SCHEDULER_SDA;
        else
            file = IO_EXTERNAL_SCHEDULER;

        if (Utils.existFile(file)) {
            String values = Utils.readFile(file);
            if (values != null) {
                String[] valueArray = values.split(" ");
                String[] out = new String[valueArray.length];

                for (int i = 0; i < valueArray.length; i++)
                    out[i] = valueArray[i].replace("[", "").replace("]", "");
                Collections.sort(Arrays.asList(out), String.CASE_INSENSITIVE_ORDER);
                return new ArrayList<>(Arrays.asList(out));
            }
        }
        return null;
    }

    public static String getScheduler(StorageType type) {
        String file;

        if (type == StorageType.INTERNAL)
            file = IO_INTERNAL_SCHEDULER;
        else if (type == StorageType.INTERNAL_DM0)
            file = IO_INTERNAL_SCHEDULER_DM0;
        else if (type == StorageType.INTERNAL_SDA)
            file = IO_INTERNAL_SCHEDULER_SDA;
        else
            file = IO_EXTERNAL_SCHEDULER;

        if (Utils.existFile(file)) {
            String values = Utils.readFile(file);
            if (values != null) {
                String[] valueArray = values.split(" ");

                for (String value : valueArray)
                    if (value.contains("["))
                        return value.replace("[", "").replace("]", "");
            }
        }
        return "";
    }

    public static boolean hasExternalStorage() {
        return Utils.existFile(IO_EXTERNAL_READ_AHEAD)
                || Utils.existFile(IO_EXTERNAL_SCHEDULER);
    }

     public static void activaterotational (boolean active, Context context) {
         Control.runCommand(active ? "1" : "0", IO_ROTATIONAL, Control.CommandType.GENERIC, context);
     }

     public static boolean isRotationalActive() {
        return Utils.readFile(IO_ROTATIONAL).equals("1");
     }

     public static boolean hasRotational () {
        return Utils.existFile(IO_ROTATIONAL);
     }

    public static void activateIORandom (boolean active, Context context) {
        Control.runCommand(active ? "1" : "0", IO_RANDOM, Control.CommandType.GENERIC, context);
    }

    public static boolean isIORandomActive() {
        return Utils.readFile(IO_RANDOM).equals("1");
    }

    public static boolean hasIORandom () {
        return Utils.existFile(IO_RANDOM);
    }

    public static void activateIOstats (boolean active, Context context) {
        Control.runCommand(active ? "1" : "0", IO_STATS, Control.CommandType.GENERIC, context);
    }

    public static boolean isIOStatsActive() {
        return Utils.readFile(IO_STATS).equals("1");
    }

    public static boolean hasIOStats () {
        return Utils.existFile(IO_STATS);
    }

    public static boolean hasIOAffinity() {
        return Utils.existFile(IO_AFFINITY);
    }

    public static int getIOAffinity () {
        String value = Utils.readFile(IO_AFFINITY);
        return Utils.stringToInt(value);
    }

    public static void setIOAffinity(int value, Context context) {
        Control.runCommand(String.valueOf(value), IO_AFFINITY, Control.CommandType.GENERIC, context);
    }

}
