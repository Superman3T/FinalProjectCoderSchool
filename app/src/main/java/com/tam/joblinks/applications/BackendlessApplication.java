package com.tam.joblinks.applications;

import android.app.Application;

import com.backendless.Backendless;

/**
 * Created by toan on 4/14/2016.
 */
public class BackendlessApplication extends Application {

    public static final String SERVER_URL = "https://api.backendless.com";
    private static final String APP_VERSION = "v1";
    private static final String APP_ID = "B12EE488-15DE-8B01-FF81-39F47C8C6800";
    private static final String SECRET_KEY = "5AA333A3-327C-F2F9-FFA5-D4C917333500";
    @Override
    public void onCreate() {
        super.onCreate();
        Backendless.setUrl(SERVER_URL);
        Backendless.initApp(this, APP_ID, SECRET_KEY, APP_VERSION);
    }
}
