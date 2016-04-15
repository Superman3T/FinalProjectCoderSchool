package com.tam.joblinks.helpers;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by toan on 4/11/2016.
 * https://www.codeofaninja.com/2011/07/android-alertdialog-example.html
 * http://www.cs.dartmouth.edu/~campbell/cs65/lecture13/lecture13.html
 */
public class DialogHelper {

    public static void confirmDialog(Context context, String message,
                                     DialogInterface.OnClickListener yesAction,
                                     DialogInterface.OnClickListener noAction) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (StringHelper.isNullOrEmpty(message)) {
            message = "Are you sure ?";
        }
        builder.setMessage(message)
                .setPositiveButton("Yes", yesAction)
                .setNegativeButton("No", noAction)
                .show();
    }

    public static void showErrorMessage(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Server reported an error: " + message)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}
