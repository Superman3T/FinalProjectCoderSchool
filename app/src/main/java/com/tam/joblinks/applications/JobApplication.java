package com.tam.joblinks.applications;

import android.app.Application;

import com.backendless.Backendless;
import com.tam.joblinks.helpers.StringHelper;

/**
 * Created by toan on 4/14/2016.
 */
public class JobApplication extends Application {
    public static String currentMail = "";
    public static String previousPage = "";
    public static final int PAGESIZE = 24;
    public static final int APPLY_JOB = 0;
    public static final int PUBLISH_JOB = 1;
    public static final int SAVE_JOB = 2;
    public static final int UNKNOWN = -1;
    public static final String USER_ACTION_JOB = "USER_ACTION_JOB";

    public static final String SERVER_URL = "https://api.backendless.com";
    public static final int TAP_JOB = 1;
    private static final String APP_VERSION = "v1";
    private static final String APP_ID = "B12EE488-15DE-8B01-FF81-39F47C8C6800";
    private static final String SECRET_KEY = "5AA333A3-327C-F2F9-FFA5-D4C917333500";

    @Override
    public void onCreate() {
        super.onCreate();
        Backendless.setUrl(SERVER_URL);
        Backendless.initApp(this, APP_ID, SECRET_KEY, APP_VERSION);
    }

    public static boolean isLogined() {
        if (StringHelper.isNullOrEmpty(JobApplication.currentMail)) {
            return false;
        }
        return  true;
    }
}
