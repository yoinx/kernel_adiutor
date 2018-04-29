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


public class CoreControl implements Constants {

    public static void setMinLittle(int value, Context context) {
        Control.runCommand(String.valueOf(value), MINLITTLE, Control.CommandType.GENERIC, context);
    }
    public static int getMinLittle() {
        return Utils.stringToInt(Utils.readFile(MINLITTLE));
    }

    public static boolean hasMinLittle() {
        return Utils.existFile(MINLITTLE);
    }

    public static void setMaxLittle(int value, Context context) {
        Control.runCommand(String.valueOf(value), MAXLITTLE, Control.CommandType.GENERIC, context);
    }
    public static int getMaxLittle() {
        return Utils.stringToInt(Utils.readFile(MAXLITTLE));
    }

    public static boolean hasMaxLittle() {
        return Utils.existFile(MAXLITTLE);
    }

    public static void setMinBig(int value, Context context) {
        Control.runCommand(String.valueOf(value), MINBIG, Control.CommandType.GENERIC, context);
    }
    public static int getMinBig() {
        return Utils.stringToInt(Utils.readFile(MINBIG));
    }

    public static boolean hasMinBig() {
        return Utils.existFile(MINBIG);
    }


    public static void setMaxBig(int value, Context context) {
        Control.runCommand(String.valueOf(value), MAXBIG, Control.CommandType.GENERIC, context);
    }
    public static int getMaxBig() {
        return Utils.stringToInt(Utils.readFile(MAXBIG));
    }

    public static boolean hasMaxBig() {
        return Utils.existFile(MAXBIG);
    }

}
