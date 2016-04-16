package com.tam.joblinks.helpers;

import android.util.Patterns;

/**
 * Created by toan on 4/17/2016.
 */
public class Validator {

    public static boolean isValidEmail(String email) {
        if (StringHelper.isNullOrEmpty(email)) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
