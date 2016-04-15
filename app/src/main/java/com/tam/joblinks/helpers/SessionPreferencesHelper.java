package com.tam.joblinks.helpers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.tam.joblinks.activities.LoginActivity;
import com.tam.joblinks.models.ViewUserLogin;

/**
 * Created by toan on 4/12/2016.
 * Ref: http://www.androidhive.info/2012/08/android-session-management-using-shared-preferences/
 */
public class SessionPreferencesHelper {
    SharedPreferences sharedRef;

    Editor editor;

    Context context;

    // Sharedpref file name
    private static final String PREF_NAME = "JobLinksPreference";

    // All Shared Preferences Keys
    private static final String IS_LOGGED_IN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    // Shared pref mode
    int PRIVATE_MODE = 0;

    public SessionPreferencesHelper(Context context) {
        this.context = context;
        this.sharedRef = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        this.editor = this.sharedRef.edit();
    }

    /**
     * Create loginAsync session
     * @param fullName
     * @param email
     */
    public void createLoginSession(String fullName, String email) {
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(KEY_NAME, fullName);
        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }

    public void checkLogin() {
        if (!this.isLoggedIn()) {
            Intent intent = new Intent(this.context, LoginActivity.class);
            // Closing all the Activities
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            this.context.startActivity(intent);
        }
    }

    /**
     * Get stored session data
     * @return ViewUserLogin object
     */
    public ViewUserLogin getUserDetails() {
        ViewUserLogin user = new ViewUserLogin();
        user.email = this.sharedRef.getString(KEY_EMAIL, null);
        //user.name = this.sharedRef.getString(KEY_NAME, null);
        return user;
    }

    public void logoutUser() {
        // clear all data from Shared Preferences
        this.editor.clear();
        this.editor.commit();

        Intent intent = new Intent(this.context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.context.startActivity(intent);
    }

    private boolean isLoggedIn() {
        return this.sharedRef.getBoolean(IS_LOGGED_IN, false);
    }
}
