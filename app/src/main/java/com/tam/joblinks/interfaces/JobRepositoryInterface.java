package com.tam.joblinks.interfaces;

import com.backendless.persistence.BackendlessDataQuery;
import com.tam.joblinks.helpers.ProgressDialogCollectionCallBack;
import com.tam.joblinks.models.Job;

/**
 * Created by toan on 4/19/2016.
 */
public interface JobRepositoryInterface extends BaseInterface<Job> {
    void pagingAsync(BackendlessDataQuery query, ProgressDialogCollectionCallBack<Job> callBack);
}
