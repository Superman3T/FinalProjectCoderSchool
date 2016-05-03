package com.tam.joblinks.helpers;

import android.content.Context;

import com.backendless.BackendlessCollection;

/**
 * Created by toan on 5/3/2016.
 */
public class DefaultCollectionCallback<T> extends DefaultCallback<BackendlessCollection<T>> {
    public DefaultCollectionCallback(Context context) {
        super(context);
    }
}
