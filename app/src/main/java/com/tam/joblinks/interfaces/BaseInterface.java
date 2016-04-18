package com.tam.joblinks.interfaces;

import com.tam.joblinks.helpers.ProgressDialogCallBack;
import com.tam.joblinks.helpers.ProgressDialogCollectionCallBack;

/**
 * Created by toan on 4/19/2016.
 */
public interface BaseInterface<T> {
    void getAllAsync(ProgressDialogCollectionCallBack<T> callBack);
    void getByIdAsync(String objectId, ProgressDialogCallBack<T> callBack);
    void deleteAsync(T objectToDelete, ProgressDialogCallBack<Long> callBack);
    void upsertAsync(T objectToSave, ProgressDialogCallBack<T> callBack);
}
