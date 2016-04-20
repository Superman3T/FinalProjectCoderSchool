package com.tam.joblinks.repositories;

import android.content.Context;

import com.backendless.Backendless;
import com.tam.joblinks.helpers.ProgressDialogCallBack;
import com.tam.joblinks.helpers.ProgressDialogCollectionCallBack;
import com.tam.joblinks.interfaces.BaseInterface;

/**
 * Created by toan on 4/19/2016.
 */
public abstract class BaseRepository<T> implements BaseInterface<T> {
    // http://stackoverflow.com/questions/16799129/java-generics-get-classt-of-generic-parameter
    protected Class<T> curClass;
    protected Context context;
    public BaseRepository(Class<T> entityClass) {
        this.curClass = entityClass;
    }

    @Override
    public void getAllAsync(ProgressDialogCollectionCallBack<T> callBack) {
        Backendless.Persistence.of(this.curClass).find(callBack);
    }

    @Override
    public void getByIdAsync(String objectId, ProgressDialogCallBack<T> callBack) {
        Backendless.Persistence.of(this.curClass).findById(objectId, callBack);
    }

    @Override
    public void deleteAsync(T objectToDelete, ProgressDialogCallBack<Long> callBack) {
        Backendless.Persistence.of(this.curClass).remove(objectToDelete, callBack);
    }

    @Override
    public void upsertAsync(T objectToSave, ProgressDialogCallBack<T> callBack) {
        Backendless.Persistence.of(this.curClass).save(objectToSave, callBack);
    }
}
