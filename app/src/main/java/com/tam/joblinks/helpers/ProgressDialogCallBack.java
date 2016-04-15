package com.tam.joblinks.helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessFault;

/**
 * Created by toan on 4/16/2016.
 */
public class ProgressDialogCallBack<T> extends BackendlessCallback<T> {

    private Context context;
    private ProgressDialog progressDialog;

    public ProgressDialogCallBack(Context context) {
        this(context, "");
    }

    public ProgressDialogCallBack(Context context, String title) {
        this(context, title, "Loading...");
    }

    public ProgressDialogCallBack(Context context, String title, String message) {
        this.context = context;
        progressDialog = ProgressDialog.show(context, title, message, true);
    }

    @Override
    public void handleResponse(T response) {
        progressDialog.cancel();
    }

    @Override
    public void handleFault(BackendlessFault fault) {
        progressDialog.cancel();
        Toast.makeText(context, fault.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
