package com.tam.joblinks.helpers;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by toan on 4/17/2016.
 */
public class MessageHelper {

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
