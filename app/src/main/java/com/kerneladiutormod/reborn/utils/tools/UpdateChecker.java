package com.kerneladiutormod.reborn.utils.tools;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.kerneladiutormod.reborn.BuildConfig;

import java.io.File;
import java.io.FileOutputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by shmuelr on 3/10/16.
 */
public class UpdateChecker {

    private static final String TAG = UpdateChecker.class.getSimpleName();

    public static void checkForUpdate(final Callback callback, final String updateURL){

        new AsyncTask<Void, Void, AppUpdateData>(){

            @Override
            protected AppUpdateData doInBackground(Void... params) {

                AppUpdateData appUpdateData = null;

                if(TextUtils.isEmpty(updateURL)){
                    Log.w(TAG, "App update Url is empty");
                    return null;
                }

                OkHttpClient client = new OkHttpClient.Builder().build();
                Request request = new Request.Builder().url(updateURL).build();

                try {
                    Response response =  client.newCall(request).execute();
                    if(response.isSuccessful()){
                        String responseBody = response.body().string();
                        Log.d(TAG, "Response String is "+responseBody);
                        appUpdateData = new Gson().fromJson(responseBody, AppUpdateData.class);
                    } else {
                        Log.e(TAG, "Response was not successful. | "+response.code());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return appUpdateData;
            }

            @Override
            protected void onPostExecute(AppUpdateData appUpdateData) {

                if(appUpdateData == null){
                    callback.onError();
                } else {
                    callback.onSuccess(appUpdateData);
                }

            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public static boolean isOldVersion(AppUpdateData appUpdateData){
        Log.d(TAG, "Comparing app version "+ BuildConfig.VERSION_CODE+" to public version "+appUpdateData.currentBuildNumber);
        return BuildConfig.VERSION_CODE < appUpdateData.currentBuildNumber;
    }

    public static void downloadNewVersion(AppUpdateData appUpdateData,final File cacheDir, final DownloadCallback callback){

        new AsyncTask<String, Void, File>(){

            @Override
            protected File doInBackground(String... params) {
                Log.d(TAG, "Downloading new app update from "+params[0]);

                File file = null;

                try {
                    OkHttpClient client = new OkHttpClient.Builder().build();
                    Request request = new Request.Builder().url(params[0]).build();

                    Response response = client.newCall(request).execute();

                    file = new File(cacheDir.getPath()+"/app-debug.apk");
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(response.body().bytes());
                    fos.close();

                }catch (Exception e){
                    e.printStackTrace();
                }

                return file;
            }

            @Override
            protected void onPostExecute(File file) {
                Log.d(TAG, "Downloading finished");

                if (file != null) {
                    callback.onSuccess(file);
                } else {
                    callback.onError();
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, appUpdateData.fileUrl);

    }

    public interface Callback{
        void onSuccess(final AppUpdateData appUpdateData);
        void onError();
    }

    public interface DownloadCallback{
        void onSuccess(final File file);
        void onError();
    }

    public class AppUpdateData {

        @SerializedName("current_build")
        public String currentBuild;
        @SerializedName("current_build_number")
        public Integer currentBuildNumber;
        @SerializedName("download_url")
        public String downloadUrl;
        @SerializedName("file_url")
        public String fileUrl;

    }
}



