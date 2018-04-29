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

package com.kerneladiutormod.reborn.utils.database;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by willi on 15.04.15.
 */
public class PerAppDB extends JsonDB {

    public PerAppDB(Context context) {
        super(context.getFilesDir() + "/per_app.json", 1);
    }

    @Override
    public DBJsonItem getItem(JSONObject item) {
        return new PerAppItem(item);
    }

    public boolean containsApp(String app) {
        List<PerAppItem> profiles = getAllApps();

        for (PerAppItem profile : profiles) {
            if (profile.getApp().equals(app)) {
                return true;
            }
        }

        return false;
    }

    public ArrayList<String> get_info(String app) {
        List<PerAppItem> profiles = getAllApps();
        ArrayList<String> list = new ArrayList<String>();

        for (PerAppItem profile : profiles) {
            if (profile.getApp().equals(app)) {
                list.add(0, profile.getApp());
                list.add(1, profile.getID());
                return(list);
            }
        }

        return null;
    }


    public void putApp(String app, String id) {
        try {
            JSONObject items = new JSONObject();
            items.put("name", app);
            items.put("id", id);

            putItem(items);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void delApp(int index) {
            delete(index);
    }

    public List<PerAppItem> getAllApps() {
        List<PerAppItem> items = new ArrayList<>();
        for (DBJsonItem jsonItem : getAllItems())
            items.add((PerAppItem) jsonItem);
        return items;
    }

    public static class PerAppItem extends DBJsonItem {

        public PerAppItem(JSONObject object) {
            item = object;
        }

        public String getApp() {
            return getString("name");
        }

        public String getID() {
            return getString("id");
        }

        private String getString(String name) {
            try {
                return getItem().getString(name);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

    }

}
