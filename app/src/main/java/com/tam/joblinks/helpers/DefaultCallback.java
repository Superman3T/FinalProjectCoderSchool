package com.tam.joblinks.helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessFault;

/**
 * Created by toan on 4/28/2016.
 */
public class DefaultCallback<T> extends BackendlessCallback<T> {

    private Context context;
    public DefaultCallback(Context context) {
        this.context = context;
    }

    @Override
    public void handleResponse(T response) {

    }

    @Override
    public void handleFault(BackendlessFault fault) {
        Toast.makeText(context, fault.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
