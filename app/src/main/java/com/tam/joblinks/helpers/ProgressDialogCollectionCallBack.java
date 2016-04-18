package com.tam.joblinks.helpers;

import android.content.Context;

import com.backendless.BackendlessCollection;

/**
 * Created by toan on 4/19/2016.
 */
public class ProgressDialogCollectionCallBack<T> extends ProgressDialogCallBack<BackendlessCollection<T>> {
    public ProgressDialogCollectionCallBack(Context context) {
        super(context);
    }
}
